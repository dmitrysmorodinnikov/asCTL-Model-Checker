/**
 * 
 */
package modelChecker;

import java.util.Set;

import formula.stateFormula.And;
import formula.stateFormula.StateFormula;
import model.State;

/**
 * @author keylor
 *
 */
public interface SubsetCalculator {
	
	public Set<State> getSat(StateFormula formula, Set<State> S);
	
}
