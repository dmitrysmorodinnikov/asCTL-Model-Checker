package modelChecker;

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

public class SubsetCalculatorImpl implements SubsetCalculator {

	private Model model;
	private CollectionHelper collectionHelper;
	
	public SubsetCalculatorImpl() {
		collectionHelper = new CollectionHelper();
	}
	
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
	
	private Set<State>getSatBool(BoolProp formula, Set<State> states){
		return states;
	}
	
	private Set<State>getSatAtomicProp(AtomicProp formula, Set<State> states){		
		Set<State>reducedStates = states.stream()
				.filter(x->Arrays.asList(x.getLabel()).contains(formula.label)).collect(Collectors.toSet());		
		return reducedStates;
	}
	
	private Set<State>getSatAnd(And formula, Set<State> states){
		Set<State>leftStates = getSat(formula.left, states);
		Set<State>rightStates = getSat(formula.right, states);
		Set<State>reducedStates = leftStates.stream()
									.filter(rightStates::contains)
									.collect(Collectors.toSet());
		return reducedStates;
	}	
	
	private Set<State>getSatNot(Not formula, Set<State> states){
		Set<State>fStates = getSat(formula.stateFormula, states);
		Set<State>copy = new HashSet<>(states);
		copy.removeAll(fStates);
		return copy;
	}
	
	private Set<State>getSatExistsNext(ThereExists formula, Set<State> states){
		Set<State>reducedStates = new HashSet<>();
		PathFormula pathFormula = formula.pathFormula;
		if(!(pathFormula instanceof Next))
			return null;
		StateFormula f = ((Next)pathFormula).stateFormula;
		Set<State>fStates = getSat(f, states);
		for(State state:states){
			Set<State>posts = getPostCollection(state, states);
			posts.retainAll(fStates);
			if(!posts.isEmpty())
				reducedStates.add(state);
		}
		return reducedStates;
	}
	
	private Set<State>getSatExistsUntil(ThereExists formula, Set<State> states){
		
		PathFormula pathFormula = formula.pathFormula;
		if(!(pathFormula instanceof Until))
			return null;
		StateFormula left = ((Until)pathFormula).left;
		StateFormula right = ((Until)pathFormula).right;
		Set<State>leftStates = getSat(left, states);
		Set<State>rightStates = getSat(right, states);
		
		Set<State>reducedStates = new HashSet<>(rightStates);
		while(true){
			Set<State>s = collectionHelper.substraction(leftStates, reducedStates);
			Set<State>statesToRemove = new HashSet<>();
			for(State state:s){
				Set<State>posts = getPostCollection(state, states);
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
	
	private Set<State>getSatExistsAlways(ThereExists formula, Set<State> states){
		PathFormula pathFormula = formula.pathFormula;
		if(!(pathFormula instanceof Next))
			return null;
		StateFormula f = ((Always)pathFormula).stateFormula;
		Set<State>fStates = getSat(f, states);		
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
			s.removeAll(statesToRemove);
			if(s.isEmpty())
				break;
			reducedStates.removeAll(s);
		}
		return reducedStates;
	}
	
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
}
