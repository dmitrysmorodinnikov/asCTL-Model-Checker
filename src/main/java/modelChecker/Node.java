package modelChecker;

import java.util.ArrayList;

import model.State;

/**
 * Node class for the counterexamples graph
 */
public class Node {
	
	private State state;
	private ArrayList<Edge> edgesToThis;
	private ArrayList<Edge> edgesFromThis;
	private int depth;
	private boolean visited;
	
	/**
	 * Class constructor
	 * @param state
	 */
	public Node(State state){
		this.state = state;
		this.edgesToThis = new ArrayList<Edge>();
		this.edgesFromThis = new ArrayList<Edge>();
		this.setVisited(false);
	}
	
	public void addChildren(Edge node){
		edgesFromThis.add(node);
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public ArrayList<Edge> getParents() {
		return edgesToThis;
	}
	public void addParents(Edge parent) {
		this.edgesToThis.add(parent);
	}
	public ArrayList<Edge> getChildren() {
		return edgesFromThis;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String toString(){
		return state.toString();
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
