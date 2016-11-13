package modelChecker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import formula.pathFormula.Next;
import formula.pathFormula.Until;
import formula.stateFormula.And;
import formula.stateFormula.AtomicProp;
import formula.stateFormula.BoolProp;
import formula.stateFormula.Not;
import formula.stateFormula.StateFormula;
import formula.stateFormula.ThereExists;
import model.Model;
import model.State;
import model.Transition;

public class SimpleModelChecker implements ModelChecker {
	
	private SubsetCalculator satCalculator;
	
	public SimpleModelChecker(SubsetCalculator satCalculator){
		this.satCalculator = satCalculator;
	}

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        //Get the set of states that satisfy the formula
    	Set<State> sat = satCalculator.getSat(query, model.getStatesSet());
    	//If all the initial states satisfy the formula, then the formula holds
    	return sat.containsAll(model.getInitialStates());
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void main(String[] args){
    	
    	//Test Model
    	Model model = new Model();
    	
    	//States
    	State s1 = new State();
    	s1.setInit(true);
    	s1.setName("s1");
    	s1.setLabel(new String[]{"b", "c"});
    	
    	State s2 = new State();
    	s2.setInit(false);
    	s2.setName("s2");
    	s2.setLabel(new String[]{"a", "b", "c"});
    	
    	State s3 = new State();
    	s3.setInit(false);
    	s3.setName("s3");
    	s3.setLabel(new String[]{"a"});
    	
    	//Transitions
    	Transition t1 = new Transition();
    	t1.setSource("s1");
    	t1.setTarget("s2");
    	Transition t2 = new Transition();
    	t2.setSource("s2");
    	t2.setTarget("s3");
    	
    	Set<State> initialStates = new HashSet<State>();
    	initialStates.add(s1);
    	
    	model.setStates(new State[]{s1, s2, s3});
    	model.setInitialStates(initialStates);
    	model.setTransitions(new Transition[]{t1, t2});
    	
    	
    	//Test Formula
    	AtomicProp a = new AtomicProp("a");
    	AtomicProp b = new AtomicProp("b");
    	AtomicProp c = new AtomicProp("c");
    	
    	Not notC = new Not(c);
    	Until until = new Until(b, notC, null, null);
    	ThereExists existsU = new ThereExists(until);
    	
    	Next next = new Next(a, null);
    	ThereExists existsNext = new ThereExists(next);
    	
    	And and = new And(existsNext, existsU);
    	
    	DummySubsetCalculator calc = new DummySubsetCalculator();
    	
    	System.out.println("This is a test with: " + and);
    	
    	SimpleModelChecker checker = new SimpleModelChecker(calc);
    	
    	boolean result = checker.check(model, null, and);
    	System.out.println("FORMULA HOLDS: " + result);
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
