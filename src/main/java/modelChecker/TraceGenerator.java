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
import helpers.CollectionHelper;
import model.Model;
import model.State;
import model.Transition;

/**
 * Generates a counterexample trace
 */
public class TraceGenerator {
	
	private SubsetCalculator satCalculator;
	private ENFGenerator enfGenerator;
	private CollectionHelper helper;

	/**
	 * Class constructor
	 * @param satCalculator Calculator of the sat sets for asCTL formulae
	 * @param enfGenerator Generates the ENF equivalent of asCTL
	 */
	public TraceGenerator(SubsetCalculator satCalculator, ENFGenerator enfGenerator) {
		this.satCalculator = satCalculator;
		this.enfGenerator = enfGenerator;
		this.helper = new CollectionHelper();
	}
	
	/**
	 * Gets a counterexample for a query known to not hold in a model
	 * @param model Model to use
	 * @param constraint Fairness constraint
	 * @param query Assumed to be ENF
	 * @param sat Set of states that satisfy the query
	 * @return
	 */
	public String[] getCounterExample(Model model, StateFormula originalQuery, StateFormula enfQuery, Set<State> sat) {
		
		ArrayList<String> result = new ArrayList<String>();
		
		//Get the initial states that don't satisfy the formula
		Set<State> initialNotSat = new HashSet<State>();
		initialNotSat.addAll(model.getInitialStates());
		initialNotSat.removeAll(sat);
		
		//If it is ForAll Until, return a counterexample for it (instead of the ENF formula)
		if (originalQuery instanceof ForAll){
			ForAll forAll = (ForAll) originalQuery;
			if (forAll.pathFormula instanceof Until){
				
				Until until = (Until)((ForAll) originalQuery).pathFormula;
				StateFormula right = enfGenerator.getENF(until.right);
				StateFormula satisfabilityFormula = new And(enfGenerator.getENF(until.left), new Not(right));
				//Get the states that don't satisfy the second part of the formula 
				StateFormula invalidationFormula = right;
				ArrayList<String> counterex = getUntilCounterexample(satisfabilityFormula, invalidationFormula, model,until.getLeftActions(), until.getRightActions(), initialNotSat);
				result.addAll(counterex);
			}
			else if (forAll.pathFormula instanceof Next){
				StateFormula stateFormulaNext = new Not(((Next) forAll.pathFormula).stateFormula);
				ArrayList<String> counterExample = this.getNextCounterExample(model, initialNotSat, stateFormulaNext, ((Next) forAll.pathFormula).getActions());
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
				ArrayList<String> counterExample = this.getNextCounterExample(model, initialNotSat, stateFormulaNext, ((Next) exists.pathFormula).getActions());
				result.addAll(counterExample);
			}
			else if (exists.pathFormula instanceof Until 
					|| exists.pathFormula instanceof Always){
				
				//States that satisfy the formula (left part of Until, formula in always)
				StateFormula satisfabilityFormula = null;
				//States that make it invalid (not(right) in case of Until, not(formula) in always)
				StateFormula invalidationFormula = null;
				
				Set<String> rightActions = null;
				Set<String> leftActions = null;
				
				if (exists.pathFormula instanceof Until){
					Until until = (Until) exists.pathFormula;
					satisfabilityFormula = new And(until.left, new Not(until.right));
					//Get the states that don't satisfy the second part of the formula 
					invalidationFormula = until.right;
					rightActions = until.getRightActions();
					leftActions = until.getLeftActions();
				}
				else {
					Always always = (Always) exists.pathFormula;
					satisfabilityFormula = always.stateFormula;
					invalidationFormula = always.stateFormula;
					rightActions = always.getActions();
				}

				ArrayList<String> counterex = getUntilCounterexample(satisfabilityFormula, invalidationFormula, model, leftActions, rightActions, initialNotSat);
				result.addAll(counterex);
			}
			
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	/**
	 * Gets a counterexample for the Next operator
	 * @param model
	 * @param initialNotSat
	 * @param stateFormulaNext
	 * @param actions
	 * @return
	 */
	private ArrayList<String> getNextCounterExample(Model model, Set<State> initialNotSat, StateFormula stateFormulaNext, Set<String> actions){
		ArrayList<String> counterExample = new ArrayList<String>();
		//Take one of the initial states that does not satisfy the formula
		State initialState = initialNotSat.iterator().next();
		
		//Calculate Post(initialState)
		Set<State> post = model.getPostCollection(initialState);
		
		Set<State> nextSatisfy = satCalculator.getSat(stateFormulaNext, post);
		
		//If there are actions, keep only those that satisfy the action property 
		if (actions != null){
			Set<State> nextSatisfyWithActions = satCalculator.getSatPrefix(helper.cloneSet(post), actions);
			nextSatisfy.retainAll(nextSatisfyWithActions);
		}
		
		//Remove from post those that satisfy the result
		post.removeAll(nextSatisfy);
		
		counterExample.add(initialState.getName());
		//There should be at least one in post
		String nextState = post.iterator().next().getName();
		
		//Add the transition from A to B, which action not in actions
		for(Transition t : model.getTransitions()){
			if (t.getSource().equals(initialState.getName()) && 
					t.getTarget().equals(nextState)){
				//Check if the actions of the transition are not in the formula
				if (!helper.contains(t.getActions(), actions)){
					 counterExample.add(t.toString());break;
				}
			}
		}
		
		counterExample.add(nextState);
		return counterExample;
	}
	
	/**
	 * Gets a counterexample for the Until operator
	 * @param satisfabilityFormula Formula for the states that satisfy the until 
	 * @param invalidationFormula  Formula for the states that invalidate the until
	 * @param model Model to use
	 * @param leftActions Left actions of the Until operator
	 * @param rightActions Right actions of the Until operator
	 * @param initialNotSat Set of initial states that don't satisfy the formula
	 * @return
	 */
	private ArrayList<String> getUntilCounterexample(StateFormula satisfabilityFormula, StateFormula invalidationFormula, Model model, Set<String> leftActions, Set<String> rightActions, Set<State> initialNotSat){
		
		Set<State> invalidationFormulaStates = this.satCalculator.getSat(new Not(invalidationFormula), model.getStatesSet());
		Set<State> rightStates = this.satCalculator.getSat(invalidationFormula, model.getStatesSet());
		
		//If there are actions, the invalidation set need to be extended to add those that satisfy the formula, but don't come from one of the actions
		if (rightActions != null && rightActions.size() != 0){
			Set<State> satStates = rightStates;
			Set<State> satAndActionsStates = this.satCalculator.getSatPrefix(helper.cloneSet(satStates), rightActions);
			rightStates = satAndActionsStates;
			satStates.removeAll(satAndActionsStates);
			invalidationFormulaStates.addAll(satStates);
		}
		
		//Create a new Graph, with only those transitions that originate 
		//in states that satisfy left and not(right)
		Set<State> satisfabilityStates = this.satCalculator.getSat(satisfabilityFormula, model.getStatesSet());
		
		//If there are left actions in the formula, then we should only take into consideration those that
		//satisfy the formula and have a valid suffix transition
		if (leftActions != null && leftActions.size() != 0){
			Set<State> suffixStates = this.satCalculator.getSatSuffix(satisfabilityStates, helper.cloneSet(rightStates), leftActions);
			satisfabilityStates.retainAll(suffixStates);
		}
		
		//Create an array of the name of those states
		ArrayList<String> satisfabilityStatesNames = new ArrayList<String>();
		Iterator<State> it = satisfabilityStates.iterator();
		while(it.hasNext()){
			satisfabilityStatesNames.add(it.next().getName());
		}
		
		//Add transitions that originate ONLY from leftAndNotRightStates
		ArrayList<Transition> newTransitions = new ArrayList<Transition>();
		for (Transition t : model.getTransitions()){
			if (satisfabilityStatesNames.contains(t.getSource())){
				newTransitions.add(t);
			}
		}
		
		Graph newGraph = new Graph(model.getStatesSet(), newTransitions, initialNotSat);
		
		ArrayList<String> counterex = newGraph.getUntilCounterexample(invalidationFormulaStates, initialNotSat);
		
		if (leftActions != null && leftActions.size() != 0){
			//Get the last node
			String last = counterex.get(counterex.size() - 1);
			Node node = newGraph.getNodeByName(last);
			if (node != null){
				Set<State> post = model.getPostCollection(node.getState());
				for (State p : post){
					for (Transition t : model.getTransitions()){
						if (t.getSource().equals(last) && t.getTarget().equals(p.getName())){
							if (!helper.contains(t.getActions(), leftActions)){
								counterex.add(counterex.size(), t.toString());
							}
						}
					}
				}
			}
		}
		
		return counterex;
	}

}
