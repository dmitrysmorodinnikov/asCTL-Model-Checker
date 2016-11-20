/**
 * 
 */
package modelChecker;

import java.util.Set;

import formula.stateFormula.And;
import formula.stateFormula.StateFormula;
import model.State;

public interface SubsetCalculator {   
	
	public Set<State> getSat(StateFormula formula, Set<State> S);
	
	public Set<State>getSatPrefix(Set<State>states, Set<String> actions);
	
	public Set<State>getSatSuffix(Set<State>leftStates,Set<State>rightStates, Set<String> actions);
	
}
