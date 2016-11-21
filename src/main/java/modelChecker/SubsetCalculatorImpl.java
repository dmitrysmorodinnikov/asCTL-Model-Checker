package modelChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import formula.pathFormula.Always;
import formula.pathFormula.Next;
import formula.pathFormula.PathFormula;
import formula.pathFormula.Until;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import helpers.CollectionHelper;
import model.Model;
import model.State;
import model.Transition;
/**
 * 
 * Computation of the satisfaction set
 *
 */
public class SubsetCalculatorImpl implements SubsetCalculator {

	private Model model;
	private CollectionHelper collectionHelper;
	
	public SubsetCalculatorImpl(Model model) {
		collectionHelper = new CollectionHelper();
		this.model = model;
	}
	
	/**
	 * sat computation of the StateFormula
	 */
	@Override
	public Set<State> getSat(StateFormula formula, Set<State> states) {		
		Set<State> resultStates;
		if(formula instanceof BoolProp)
			resultStates = getSatBool((BoolProp) formula, states);
		else if(formula instanceof AtomicProp)
			resultStates = getSatAtomicProp((AtomicProp) formula, states);
		else if(formula instanceof And)
			resultStates = getSatAnd((And) formula, states);
		else if(formula instanceof Not)
			resultStates = getSatNot((Not) formula, states);
		else if(formula instanceof ThereExists){
			PathFormula pathFormula = ((ThereExists) formula).pathFormula;
			if(pathFormula instanceof Next)
				resultStates = getSatExistsNext((ThereExists)formula, states);
			else if(pathFormula instanceof Until)
				resultStates = getSatExistsUntil((ThereExists) formula, states);
			else if(pathFormula instanceof Always)
				resultStates = getSatExistsAlways((ThereExists) formula, states);
			else
				resultStates = null;
		}			
		else if(formula instanceof AtomicProp)
			resultStates = getSatAtomicProp((AtomicProp) formula, states);
		else if(formula instanceof AtomicProp)
			resultStates = getSatAtomicProp((AtomicProp) formula, states);
		else
			resultStates = null;
		return resultStates;
	}
	
	/**
	 * sat computation for boolean propositions
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatBool(BoolProp formula, Set<State> states){
		return states;
	}
	
	/**
	 * sat computation for Atomic propositions
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatAtomicProp(AtomicProp formula, Set<State> states){		
		Set<State>reducedStates = states.stream()
				.filter(x->Arrays.asList(x.getLabel()).contains(formula.label)).collect(Collectors.toSet());		
		return reducedStates;
	}
	
	/**
	 * sat computation for AND formula
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatAnd(And formula, Set<State> states){
		Set<State>leftStates = getSat(formula.left, states);
		Set<State>rightStates = getSat(formula.right, states);
		Set<State>reducedStates = leftStates.stream()
									.filter(rightStates::contains)
									.collect(Collectors.toSet());
		return reducedStates;
	}	
	
	/**
	 * sat computation for NOT formula
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatNot(Not formula, Set<State> states){
		Set<State>fStates = getSat(formula.stateFormula, states);
		Set<State>copy = new HashSet<>(states);
		copy.removeAll(fStates);
		return copy;
	}
	
	/**
	 * sat computation for Exists(Next) formula
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatExistsNext(ThereExists formula, Set<State> states){
		Set<State>reducedStates = new HashSet<>();
		PathFormula pathFormula = formula.pathFormula;
		if(!(pathFormula instanceof Next))
			return null;
		StateFormula f = ((Next)pathFormula).stateFormula;
		Set<State>fStates = getSat(f, states);
		if(!((Next)pathFormula).getActions().isEmpty()){
			fStates = getSatPrefix(fStates, ((Next)pathFormula).getActions());
		}
		for(State state:states){
			Set<State>posts = getPostCollection(state, states);
			posts.retainAll(fStates);
			if(!posts.isEmpty())
				reducedStates.add(state);
		}
		return reducedStates;
	}
	
	/**
	 * sat computation for Exists(Until) formula
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatExistsUntil(ThereExists formula, Set<State> states){
		
		PathFormula pathFormula = formula.pathFormula;
		if(!(pathFormula instanceof Until))
			return null;
		StateFormula left = ((Until)pathFormula).left;
		StateFormula right = ((Until)pathFormula).right;
		Set<State>leftStates = getSat(left, states);
		Set<State>rightStates = getSat(right, states);
		Set<String>rightActions = ((Until)pathFormula).getRightActions();
		Set<String>leftActions = ((Until)pathFormula).getLeftActions();
		
		if(!((Until)pathFormula).getRightActions().isEmpty()){
			rightStates = getSatPrefix(rightStates, ((Until)pathFormula).getRightActions());			
		}		
		if(!((Until)pathFormula).getLeftActions().isEmpty()){
			leftStates = getSatSuffix(leftStates,rightStates, ((Until)pathFormula).getLeftActions());			
		}
		
		Set<State>reducedStates = new HashSet<>(rightStates);
		while(true){
			Set<State>s = collectionHelper.substraction(leftStates, reducedStates);
			Set<State>statesToRemove = new HashSet<>();
			for(State state:s){
				Set<State>posts = getPostCollection(state, states);
				Set<State>postsToRemove = new HashSet<>();					
				for(State p:posts){
					if(rightStates.contains(p)&& !rightActions.isEmpty()){
						int cnt = 0;
						Set<Transition> incomingTransitions = getIncomingTransitions(p)
								.stream().filter(x->x.getSource().equals(state.getName()) && x.getTarget().equals(p.getName()))
								.collect(Collectors.toSet());
						if(incomingTransitions.isEmpty())
							continue;
						for(Transition tr:incomingTransitions){				
							boolean isEmpty = collectionHelper
									.intersection(Arrays.stream(tr.getActions()).collect(Collectors.toSet()), rightActions)
									.isEmpty();
							if(isEmpty){
								cnt++;					
							}				
						}
						if(cnt == incomingTransitions.size())
							postsToRemove.add(p);
					}
					if(leftStates.contains(p)&& !leftActions.isEmpty()){
						int cnt = 0;
						Set<Transition> incomingTransitions = getIncomingTransitions(p)
								.stream().filter(x->x.getSource().equals(state.getName()) && x.getTarget().equals(p.getName()))
								.collect(Collectors.toSet());
						if(incomingTransitions.isEmpty())
							continue;
						for(Transition tr:incomingTransitions){				
							boolean isEmpty = collectionHelper
									.intersection(Arrays.stream(tr.getActions()).collect(Collectors.toSet()), leftActions)
									.isEmpty();
							if(isEmpty){
								cnt++;					
							}				
						}
						if(cnt == incomingTransitions.size())
							postsToRemove.add(p);
					}
				}	
			
				posts.removeAll(postsToRemove);
				posts.retainAll(reducedStates);
				if(posts.isEmpty())
					statesToRemove.add(state);
			}
			s.removeAll(statesToRemove);
			if(s.isEmpty())
				break;
			reducedStates.addAll(s);
		}
		
		return reducedStates;
	}
	
	/**
	 * sat computation for (a)F, where (a) is a set of actions, F - path formula
	 * @param states
	 * @param actions
	 * @return
	 */
	public Set<State>getSatPrefix(Set<State>states, Set<String> actions){
		Set<State>statesToRemove = new HashSet<>();
		for(State state:states){
			int cnt = 0;
			Set<Transition> incomingTransitions = getIncomingTransitions(state)
					.stream().filter(x->!x.getSource().equals(x.getTarget()))
					.collect(Collectors.toSet());
			if(incomingTransitions.isEmpty())
				continue;
			for(Transition tr:incomingTransitions){				
				boolean isEmpty = collectionHelper
						.intersection(Arrays.stream(tr.getActions()).collect(Collectors.toSet()), actions)
						.isEmpty();
				if(isEmpty){
					cnt++;					
				}				
			}
			if(cnt == incomingTransitions.size())
				statesToRemove.add(state);
		}
		states.removeAll(statesToRemove);
		return states;
	}
	
	/**
	 * sat computation for F(a), where (a) is a set of actions, F - path formula
	 * @param states
	 * @param actions
	 * @return
	 */
	public Set<State>getSatSuffix(Set<State>leftStates,Set<State>rightStates, Set<String> actions){
		Set<State>statesToRemove = new HashSet<>();
		for(State state:leftStates){
			int cnt=0;
			Set<Transition> outcomingTransitions = getOutcomingTransitions(state)
					.stream().filter(x->!x.getSource().equals(x.getTarget()))
					.collect(Collectors.toSet());
			if(outcomingTransitions.isEmpty())
				continue;
			for(Transition tr:outcomingTransitions){
				boolean nextIsFromRightStates = rightStates.stream().anyMatch(x->x.getName().equals(tr.getTarget()));
				
				boolean isEmpty = collectionHelper.intersection(Arrays.stream(tr.getActions()).collect(Collectors.toSet()), actions).isEmpty();
				if(isEmpty && !nextIsFromRightStates){
					cnt++;
				}					
			}			
			if(cnt == outcomingTransitions.size())
				statesToRemove.add(state);
		}		
		leftStates.removeAll(statesToRemove);
		return leftStates;
	}
	
	/**
	 * sat computation for Exists(Always)formula
	 * @param formula
	 * @param states
	 * @return
	 */
	private Set<State>getSatExistsAlways(ThereExists formula, Set<State> states){
		PathFormula pathFormula = formula.pathFormula;
		if(!(pathFormula instanceof Always))
			return null;
		StateFormula f = ((Always)pathFormula).stateFormula;
		Set<State>fStates = getSat(f, states);	
		if(!((Always)pathFormula).getActions().isEmpty()){
			fStates = getSatPrefix(fStates, ((Always)pathFormula).getActions());
		}
		Set<State>reducedStates = new HashSet<>(fStates);
		while(true){
			Set<State>s = new HashSet<>(reducedStates);
			Set<State>statesToRemove = new HashSet<>();
			for(State state:s){
				Set<State>posts = getPostCollection(state, states);				
				posts.retainAll(reducedStates);
				if(posts.isEmpty())
					statesToRemove.add(state);
			}			
			if(statesToRemove.size() == 0){
				break;
			}				
			reducedStates.removeAll(statesToRemove);
		}
		return reducedStates;
	}
	
	/**
	 * get all successors of the state
	 * @param state
	 * @param states
	 * @return
	 */
	private Set<State> getPostCollection(State state, Set<State> states){
		Set<String>postStrings = Arrays.asList(model.getTransitions())
				.stream()
				.filter(x->x.getSource().equals(state.getName()))
				.map(x->x.getTarget())
				.collect(Collectors.toSet());
		Set<State>postStates = states.stream()
				.filter(x->postStrings.contains(x.getName()))
				.collect(Collectors.toSet());
		return postStates;
	}
	
	/**
	 * get all incoming transitions of the state
	 * @param state
	 * @return
	 */
	private Set<Transition>getIncomingTransitions(State state){
		return Arrays.asList(model.getTransitions())
				.stream()
				.filter(x->x.getTarget().equals(state.getName()))										
				.collect(Collectors.toSet());
	}
	
	/**
	 * get all outcoming transitions of the state
	 * @param state
	 * @return
	 */
	private Set<Transition>getOutcomingTransitions(State state){
		return Arrays.asList(model.getTransitions())
				.stream()
				.filter(x->x.getSource().equals(state.getName()))										
				.collect(Collectors.toSet());
	}
}
