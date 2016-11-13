/**
 * 
 */
package modelChecker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import formula.pathFormula.Next;
import formula.pathFormula.Until;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.State;

/**
 * @author keylor
 * Calculates the states that satisfy a given formulae for a fixed TS
 */
public class DummySubsetCalculator implements SubsetCalculator {

	/* (non-Javadoc)
	 * @see modelChecker.SubsetCalculator#getSat(formula.stateFormula.StateFormula, java.util.Set)
	 */
	@Override 
	public Set<State> getSat(StateFormula formula, Set<State> S) {
		
		//TODO: Do we need the set of all the states as well here?
		
		
		Set<State> result = new HashSet<State>();
		
		Iterator<State> it = S.iterator();
		while(it.hasNext()){
			State s = it.next();
			
			//Check a, b, c
			if (formula instanceof AtomicProp) {
				AtomicProp prop = (AtomicProp) formula;
				if (prop.label.equals("a")){
					if (s.getName().equals("s2")){
						result.add(s);
					}
					if (s.getName().equals("s3")){
						result.add(s);
					}
				}
				
				if (prop.label.equals("b")){
					if (s.getName().equals("s1")){
						result.add(s);
					}
					if (s.getName().equals("s2")){
						result.add(s);
					}
				}
				
				if (prop.label.equals("c")){
					if (s.getName().equals("s1")){
						result.add(s);
					}
					if (s.getName().equals("s2")){
						result.add(s);
					}
				}
			}
			
			//Not c
			if (formula instanceof Not) {
				if (s.getName().equals("s3")){
					result.add(s);
				}
			}
			
			//AND
			if (formula instanceof And) {
				if (s.getName().equals("s1")){
					result.add(s);
				}
				if (s.getName().equals("s2")){
					result.add(s);
				}
			}
			
			
			//Exists
			if (formula instanceof ThereExists) {
				ThereExists f = (ThereExists) formula;
				//Until
				if (f.pathFormula instanceof Until){
					if (s.getName().equals("s1")){
						result.add(s);
					}
					if (s.getName().equals("s2")){
						result.add(s);
					}
				}
				
				//Next
				if (f.pathFormula instanceof Next){
					if (s.getName().equals("s1")){
						result.add(s);
					}
					if (s.getName().equals("s2")){
						result.add(s);
					}
				}
			}
			
		}
		
		return result;
	}

}
