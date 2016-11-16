/**
 * 
 */
package modelChecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import formula.pathFormula.Next;
import formula.pathFormula.Until;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.Or;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.State;
import model.Transition;


public class TraceGenerator {
	
	private SubsetCalculator satCalculator;

	public TraceGenerator(SubsetCalculator satCalculator) {
		this.satCalculator = satCalculator;
	}
	
	/**
	 * Gets a counterexample for the query
	 * @param model
	 * @param constraint
	 * @param query Assumed to be ENF
	 * @param sat
	 * @return
	 */
	public String[] getCounterExample(Model model, StateFormula constraint, StateFormula query, Set<State> sat) {
		
		ArrayList<String> result = new ArrayList<String>();
		//printSet(model.getStatesSet(), "Model States");
		printSet(model.getInitialStates(), "Initial States");
		printSet(sat, "Sat");
		
		//Get the initial states that don't satisfy the formula
		Set<State> initialNotSat = new HashSet<State>();
		initialNotSat.addAll(model.getInitialStates());
		initialNotSat.removeAll(sat);
		
		printSet(initialNotSat, "Initial not Sat");
		
		
		//Counterexamples without fairnes
		if (constraint == null){
			
			//If it is a state formula, return a sample initial state where it does not hold
			if (query instanceof And || 
					query instanceof AtomicProp || 
					query instanceof BoolProp || 
					query instanceof Not || 
					query instanceof Or){
				result.add(initialNotSat.iterator().next().getName());
			}
			//There Exists Case
			else if (query instanceof ThereExists){
				
				ThereExists exists = (ThereExists) query;
				
				if (exists.pathFormula instanceof Next){
					
					//Take one of the initial states that doesnot satisfy the formula
					State initialState = initialNotSat.iterator().next();
					
					//Calculate Post(initialState)
					Set<State> post = model.getPostCollection(initialState);
					
					Set<State> nextSatisfy = satCalculator.getSat(((Next) exists.pathFormula).stateFormula, post);
					//Remove from post those that satisfy the result
					post.removeAll(nextSatisfy);
					
					result.add(initialState.getName());
					//There should be at least one in post
					//TODO: Should add the label of the transition here as well
					result.add(post.iterator().next().getName());
				}
				else if (exists.pathFormula instanceof Until){
					
					Until until = (Until) exists.pathFormula;
					
					//Create a new Graph, with only those transitions that originate 
					//in states that satisfy left and not(right)
					StateFormula leftAndNotRightFormula = new And(until.left, new Not(until.right));
					Set<State> leftAndNotRightStates = this.satCalculator.getSat(leftAndNotRightFormula, model.getStatesSet());
					printSet(leftAndNotRightStates, "Left and Not Right");
					
					//Create an array of the name of those states
					ArrayList<String> leftAndNotRightStatesNames = new ArrayList<String>();
					Iterator<State> it = leftAndNotRightStates.iterator();
					while(it.hasNext()){
						leftAndNotRightStatesNames.add(it.next().getName());
					}
					
					//New Graph (Model)
					Model graph = new Model();
					graph.setStates(model.getStates().clone());
					
					//Add transitions that originate ONLY from leftAndNotRightStates
					ArrayList<Transition> newTransitions = new ArrayList<Transition>();
					for (Transition t : model.getTransitions()){
						if (leftAndNotRightStatesNames.contains(t.getSource())){
							newTransitions.add(t);
						}
					}
					graph.setTransitions(newTransitions.toArray(new Transition[newTransitions.size()]));
					
					//Print new transitions
					System.out.println("*********New Transitions");
					for(Transition t : newTransitions){
						System.out.println(t);
					}
					System.out.println("*********");
					
					Graph newGraph = new Graph(model.getStatesSet(), newTransitions);
					
					//Get the states that don't satisfy the second part of the formula 
					StateFormula notRightFormula = new Not(until.right);
					Set<State> notRightFormulaStates = this.satCalculator.getSat(notRightFormula, model.getStatesSet());
					
					printSet(notRightFormulaStates, "Not Right");
					
					newGraph.getUntilCounterexample(notRightFormulaStates);
					
					//Get the SCC of the new graph
					//newGraph.getCounterExample();
					
				}
				
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	//Testing
    private void printSet(Set<State> set, String name){
    	System.out.println("-------SET "+name+"-------");
    	Iterator<State> it = set.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("-------End SET-------");
    }

}
