
package modelChecker;

import java.util.HashSet;

import formula.pathFormula.Always;
import formula.pathFormula.Eventually;
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

/**
 * 
 * Generates the ENF 
 *
 */
public class ENFGenerator {
	
	
	public StateFormula getENF(StateFormula formula){
		
		if (formula instanceof AtomicProp){
			return formula;
		}
		else if (formula instanceof BoolProp){
			return getENF((BoolProp) formula);
		}
		else if (formula instanceof Not){
			return getENF((Not) formula);
		}
		else if (formula instanceof And){
			return getENF((And) formula);
		}
		else if (formula instanceof Or){
			return getENF((Or) formula);
		}
		else if (formula instanceof ThereExists){
			return getENF((ThereExists) formula);
		}
		else if (formula instanceof ForAll){
			return getENF((ForAll) formula);
		}
		return null;
	}
	
	/**
	 * ENF for BooleanProp: 
	 * 		true = true
	 * 		false = not(true)
	 * @param formula
	 * @return
	 */
	private StateFormula getENF(BoolProp formula){
		//If true, return true
		if (formula.value)
			return formula;
		//If false, return the neg of true
		BoolProp trueProp = new BoolProp(true);
		Not not = new Not(trueProp);
		return not;
	}
	
	/**
	 * ENF for Not: 
	 * 		not(P) = not(ENF(P)) 
	 * @param formula
	 * @return
	 */
	private StateFormula getENF(Not formula){
		return new Not(getENF(formula.stateFormula));
	}
	
	/**
	 * ENF for AND:
	 * 		P and Q = ENF(P) and ENF(Q)
	 * @param formula
	 * @return
	 */
	private StateFormula getENF(And formula){
		return new And(getENF(formula.left), getENF(formula.right));
	}
	
	/**
	 * ENF for OR: 
	 * 	P or Q = not( not(ENF(P)) AND not(ENF(Q)) )
	 * @param formula
	 * @return
	 */
	private StateFormula getENF(Or formula){
		
		StateFormula newLeft = new Not(getENF(formula.left));
		StateFormula newRight = new Not(getENF(formula.right));
		
		return new Not(new And(newLeft, newRight));
	}
	
	/**
	 * ENF for Exists: 
	 * 		Exists(P U Q) = Exists(ENF(P) U ENF(Q))
	 * 		Exists(Next(P)) = Exists(Next(ENF(P)))
	 * 		Exists(Always(P)) = Exists(Always(ENF(P)))
	 * 		Exists(Eventually(P)) = Exists(true U ENF(P))
	 * @param formula
	 * @return
	 */
	private StateFormula getENF(ThereExists formula){
		//Exists(P U Q) case
		if (formula.pathFormula instanceof Until){
			Until originalUntil = (Until) formula.pathFormula;
			StateFormula newLeft = getENF(originalUntil.left);
			StateFormula newRigth = getENF(originalUntil.right);
			
			Until newUntil = new Until(newLeft, newRigth, originalUntil.getLeftActions(), 
					originalUntil.getRightActions());
			
			return new ThereExists(newUntil);
		}
		else if (formula.pathFormula instanceof Next){
			Next originalNext = (Next) formula.pathFormula;
			return new ThereExists(new Next(getENF(originalNext.stateFormula), originalNext.getActions()));
		}
		else if (formula.pathFormula instanceof Always){
			Always originalAlways = (Always) formula.pathFormula;
			return new ThereExists(new Always(getENF(originalAlways.stateFormula), originalAlways.getActions()));
		}
		else if (formula.pathFormula instanceof Eventually){
			Eventually originalEventually = (Eventually) formula.pathFormula;
			
			Until until = new Until(new BoolProp(true), getENF(originalEventually.stateFormula), 
					originalEventually.getLeftActions(), originalEventually.getRightActions());
			
			return new ThereExists(until);
			
		}
		return null;
		
	}
	
	/**
	 * ENF for ForAll: 
	 * 		ForAll(P U Q) = (not(Exists(not(ENF(Q)) U (not(ENF(P)) and not(ENF(Q))))) 
	 * 						and
	 * 						(not(Exists(Always(not(ENF(Q))))))
	 * 		ForAll(Next(P)) = not(Exists(Next(not(ENF(P)))))
	 * 		ForAll(Always(P)) = not(Exists(true U ENF(P)))
	 * 		ForAll(Eventually(P)) = not(Exists(Always(not(ENF(P)))))
	 * @param formula
	 * @return
	 */
	private StateFormula getENF(ForAll formula){
		
		if (formula.pathFormula instanceof Until){
			Until originalUntil = (Until) formula.pathFormula;
			
			StateFormula enfP = getENF(originalUntil.left);
			StateFormula enfQ = getENF(originalUntil.right);
			
			//Left part of the final AND
				//Until Right
			StateFormula untilRight = new And(new Not(enfP), new Not(enfQ));
				//Until Left
			StateFormula untilLeft = new Not(enfQ);
			
			Until rightUntil = new Until(untilLeft, untilRight,
					originalUntil.getLeftActions(), originalUntil.getRightActions());
			
			StateFormula leftAnd = new Not(new ThereExists(rightUntil));
			
			//Right part of the final AND
			StateFormula rightAnd = 
				new Not(
					new ThereExists(
								new Always(
										new Not(enfQ), originalUntil.getRightActions()
										)
							)
					);
			//Final Formula
			return new And(leftAnd, rightAnd);
			
		}
		else if (formula.pathFormula instanceof Next){
			Next originalNext = (Next) formula.pathFormula;
			Next newNext = new Next(new Not(getENF(originalNext.stateFormula)), originalNext.getActions());
			return new Not(new ThereExists(newNext));
		}
		else if (formula.pathFormula instanceof Always){
			Always originalAlways = (Always) formula.pathFormula;
			Until newUntil = new Until(new BoolProp(true), getENF(originalAlways.stateFormula),
					new HashSet<String>(), originalAlways.getActions());
			return new Not(new ThereExists(newUntil));
		}
		else if (formula.pathFormula instanceof Eventually){
			Eventually originalEventually = (Eventually) formula.pathFormula;
			Always newAlways = new Always(new Not(getENF(originalEventually.stateFormula)), originalEventually.getRightActions());
			return new Not(new ThereExists(newAlways));
		}
		return null;
	}
	
//	public static void main(String[] args){
//		
//		ENFGenerator generator = new ENFGenerator();
//		
//		StateFormula atomic = new AtomicProp("a");
//		StateFormula atomic1 = new AtomicProp("b");
//		
//		//true test
//		//StateFormula test = new BoolProp(false);
//		
//		//StateFormula test = new Not(atomic);
//		//StateFormula test = new ForAll(new Next(atomic, new HashSet<String>()));
//		//StateFormula test = new Or(atomic, atomic1);
//		StateFormula test = new ThereExists(new Eventually(atomic, new HashSet<String>(), new HashSet<String>()));
//		
//		System.out.println("Original:  " + test);
//		System.out.println("Result:  " + generator.getENF(test));
//	}
	
	
}
