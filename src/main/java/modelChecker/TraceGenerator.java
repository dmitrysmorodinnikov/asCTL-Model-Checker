/**
 * 
 */
package modelChecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import formula.pathFormula.Always;
import formula.pathFormula.Next;
import formula.pathFormula.Until;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.ForAll;
import formula.stateFormula.Not;
import formula.stateFormula.Or;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.State;
import model.Transition;


public class TraceGenerator {
	
	private SubsetCalculator satCalculator;
	private ENFGenerator enfGenerator;

	public TraceGenerator(SubsetCalculator satCalculator, ENFGenerator enfGenerator) {
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
	public String[] getCounterExample(Model model, StateFormula originalQuery, StateFormula enfQuery, Set<State> sat) {
		
		ArrayList<String> result = new ArrayList<String>();
		//printSet(model.getStatesSet(), "Model States");
		printSet(model.getInitialStates(), "Initial States");
		printSet(sat, "Sat");
		
		//Get the initial states that don't satisfy the formula
		Set<State> initialNotSat = new HashSet<State>();
		initialNotSat.addAll(model.getInitialStates());
		initialNotSat.removeAll(sat);
		
		printSet(initialNotSat, "Initial not Sat");
		
		//If it is ForAll Until, return a counterexample for it (instead of the ENF formula)
		if (originalQuery instanceof ForAll){
			ForAll forAll = (ForAll) originalQuery;
			if (forAll.pathFormula instanceof Until){
				
				Until until = (Until)((ForAll) originalQuery).pathFormula;
				StateFormula right = enfGenerator.getENF(until.right);
				StateFormula satisfabilityFormula = new And(enfGenerator.getENF(until.left), new Not(right));
				//Get the states that don't satisfy the second part of the formula 
				StateFormula invalidationFormula = new Not(right);
				ArrayList<String> counterex = getUntilCounterexample(satisfabilityFormula, invalidationFormula, model);
				result.addAll(counterex);
			}
			else if (forAll.pathFormula instanceof Next){
				StateFormula stateFormulaNext = new Not(((Next) forAll.pathFormula).stateFormula);
				ArrayList<String> counterExample = this.getNextCounterExample(model, initialNotSat, stateFormulaNext);
				result.addAll(counterExample);
			}
			
		}
		//If it is a state formula, return a sample initial state where it does not hold
		else if (enfQuery instanceof And || 
				enfQuery instanceof AtomicProp || 
				enfQuery instanceof BoolProp || 
				enfQuery instanceof Not || 
				enfQuery instanceof Or){
			result.add(initialNotSat.iterator().next().getName());
		}
		//There Exists Case
		else if (enfQuery instanceof ThereExists){
			
			ThereExists exists = (ThereExists) enfQuery;
			
			if (exists.pathFormula instanceof Next){
				StateFormula stateFormulaNext = ((Next) exists.pathFormula).stateFormula;
				ArrayList<String> counterExample = this.getNextCounterExample(model, initialNotSat, stateFormulaNext);
				result.addAll(counterExample);
			}
			else if (exists.pathFormula instanceof Until 
					|| exists.pathFormula instanceof Always){
				
				//States that satisfy the formula (left part of Until, formula in always)
				StateFormula satisfabilityFormula = null;
				//States that make it invalid (not(right) in case of Until, not(formula) in always)
				StateFormula invalidationFormula = null;
				
				
				if (exists.pathFormula instanceof Until){
					Until until = (Until) exists.pathFormula;
					satisfabilityFormula = new And(until.left, new Not(until.right));
					//Get the states that don't satisfy the second part of the formula 
					invalidationFormula = new Not(until.right);
				}
				else {
					Always always = (Always) exists.pathFormula;
					satisfabilityFormula = always.stateFormula;
					invalidationFormula = new Not(always.stateFormula);
				}

				ArrayList<String> counterex = getUntilCounterexample(satisfabilityFormula, invalidationFormula, model);
				result.addAll(counterex);
			}
			
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	private ArrayList<String> getNextCounterExample(Model model, Set<State> initialNotSat, StateFormula stateFormulaNext){
		ArrayList<String> counterExample = new ArrayList<String>();
		//Take one of the initial states that does not satisfy the formula
		State initialState = initialNotSat.iterator().next();
		
		//Calculate Post(initialState)
		Set<State> post = model.getPostCollection(initialState);
		
		Set<State> nextSatisfy = satCalculator.getSat(stateFormulaNext, post);
		//Remove from post those that satisfy the result
		post.removeAll(nextSatisfy);
		
		counterExample.add(initialState.getName());
		//There should be at least one in post
		//TODO: Should add the label of the transition here as well
		counterExample.add(post.iterator().next().getName());
		return counterExample;
	}
	
	private ArrayList<String> getUntilCounterexample(StateFormula satisfabilityFormula, StateFormula invalidationFormula, Model model){
		//Create a new Graph, with only those transitions that originate 
		//in states that satisfy left and not(right)
		
		Set<State> satisfabilityStates = this.satCalculator.getSat(satisfabilityFormula, model.getStatesSet());
		printSet(satisfabilityStates, "satisfabilityStates");
		
		//Create an array of the name of those states
		ArrayList<String> satisfabilityStatesNames = new ArrayList<String>();
		Iterator<State> it = satisfabilityStates.iterator();
		while(it.hasNext()){
			satisfabilityStatesNames.add(it.next().getName());
		}
		
		//New Graph (Model)
		Model graph = new Model();
		graph.setStates(model.getStates().clone());
		
		//Add transitions that originate ONLY from leftAndNotRightStates
		ArrayList<Transition> newTransitions = new ArrayList<Transition>();
		for (Transition t : model.getTransitions()){
			if (satisfabilityStatesNames.contains(t.getSource())){
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
		
		
		Set<State> invalidationFormulaStates = this.satCalculator.getSat(invalidationFormula, model.getStatesSet());
		
		printSet(invalidationFormulaStates, "invalidationFormulaStates");
		
		ArrayList<String> counterex = newGraph.getUntilCounterexample(invalidationFormulaStates);
		return counterex;
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
