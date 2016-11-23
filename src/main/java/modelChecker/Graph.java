package modelChecker;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import model.State;
import model.Transition;

/**
 * Graph used to find counterexamples for Until operators
 */
public class Graph {
	
	/**
	 * Initial nodes of the graph
	 */
	private ArrayList<Node> initialNodes;
	
	/**
	 * Hashtable to keep all the nodes in the system by name
	 */
	private Hashtable<String, Node> hashNodes;
	
	/**
	 * Creates a graph from a set of states and valid transitions
	 * @param states
	 */
	public Graph(Set<State> states, ArrayList<Transition> transitions, Set<State> initialNotSat){
		
		this.initialNodes = new ArrayList<Node>();
		this.hashNodes = new Hashtable<String, Node>();
		
		//Create the nodes and add them to the hash and the initial states list
		Iterator<State> it = states.iterator();
		State state;
		while(it.hasNext()){
			state = it.next();
			//Create the node 
			Node newNode = new Node(state);
			if (state.isInit() && initialNotSat.contains(state)){
				initialNodes.add(newNode);
			}
			hashNodes.put(state.getName(), newNode);
		}
		
		//Create the edges
		for(Transition t : transitions){
			Node from = hashNodes.get(t.getSource());
			Node to = hashNodes.get(t.getTarget());
			Edge edge = new Edge(from, to);
			edge.setActions(t.getActions());
			from.addChildren(edge);
			to.addParents(edge);
		}
	}
	
	/**
	 * Gets a counterexample for the Until operator
	 * @param terminalCounterexStates States that invalidate the formula
	 * @param initialNotSat States that don't satisfy the Until in the initial state
	 * @return
	 */
	public ArrayList<String> getUntilCounterexample(Set<State> terminalCounterexStates, Set<State> initialNotSat) {
		
		ArrayList<String> result = new ArrayList<String>();
		//For each of the initial states
		for(Node initialNode : initialNodes){
			
			//Create stacks, mark all as not visited
			Stack<Object> stackPath = new Stack<Object>();
			Stack<Edge> stackExpand = new Stack<Edge>();
			Iterator<String> keys = hashNodes.keySet().iterator();
			while(keys.hasNext()){
				hashNodes.get(keys.next()).setVisited(false);
			}
			
			//If there are not children from the initial state, check if it belongs to terminalCounterexStates and return it if it is
			if (initialNode.getChildren().size() == 0 && terminalCounterexStates.contains(initialNode.getState())){
				result.add(initialNode.getState().getName());
				return result;
			}
			
			//Add the initial nodes to expand
			for (Edge initialEdge : initialNode.getChildren())
				stackExpand.add(initialEdge);
			
			//Add the initial state to the stackPath
			stackPath.add(initialNode);
			
			do {
				
				//Get an element from the stack 
				Edge expandedEdge = stackExpand.pop();
				//Set origin as visited
				expandedEdge.getFrom().setVisited(true);
				//If the top of the stack path is different than my current origin
				while (!(stackPath.peek() instanceof Node) || 
						!((Node) stackPath.peek()).getState().equals(expandedEdge.getFrom().getState())){
					//Remove elements from the path until the origin is on top. Mark as not visited those that are taken out
					Object pop = stackPath.pop();
					if (pop instanceof Node){
						((Node) pop).setVisited(false);
					}
				}
				
				//Add my edge and the destination to the stack path
				stackPath.add(expandedEdge);
				stackPath.add(expandedEdge.getTo());
				
				boolean counterexFound = false;
				boolean loopFound = false;
				
				//If my destination is terminal and is in terminalCounterexStates, then we have a counter example. (return the path stack)
				if (expandedEdge.getTo().getChildren().size() == 0 && terminalCounterexStates.contains(expandedEdge.getTo().getState())){
					counterexFound = true;
				}
				
				//If my destination is already visited, we have a loop, and a counter example. (return the path stack)
				if (expandedEdge.getTo().isVisited()){
					loopFound = true;
					counterexFound = true;
				}
				//If a counterexample is found, return the path and finish the algorithm
				if (counterexFound){
					while(!stackPath.isEmpty()){
						Object pop = stackPath.pop();
						if (pop instanceof Node){
							result.add(0, ((Node) pop).getState().getName());
						}
						else {
							result.add(0, ((Edge) pop).getActionsString());
						}
					}
					if (loopFound){
						result.add("...");
					}
					return result;
				}
				else {
					//If not, add my destination's children transitions to the stackExpand
					for (Edge newEdge : expandedEdge.getTo().getChildren()){
						stackExpand.add(newEdge);
					}
					
				}
				
				
			} while (!stackExpand.isEmpty());
			
		}
		return null;
	}
	
	public Node getNodeByName(String name){
		return this.hashNodes.get(name);
	}
	
}
