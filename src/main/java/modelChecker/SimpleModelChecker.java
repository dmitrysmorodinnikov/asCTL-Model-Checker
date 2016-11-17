package modelChecker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import formula.FormulaParser;
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
	private ENFGenerator enfGenerator;
	
	public SimpleModelChecker(SubsetCalculator satCalculator){
		this.satCalculator = satCalculator;
		this.enfGenerator = new ENFGenerator();
	}

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
    	//Get the ENF form of the query
    	StateFormula enfQuery = enfGenerator.getENF(query);
    	
        //Get the set of states that satisfy the formula
    	Set<State> sat = satCalculator.getSat(enfQuery, model.getStatesSet());
    	//If all the initial states satisfy the formula, then the formula holds
    	return sat.containsAll(model.getInitialStates());
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void main(String[] args){
    	
    	try {
			Model model = Model.parseModel("src/test/resources/models/model3.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula3_1.json").parse();
			boolean res = new SimpleModelChecker(new SubsetCalculatorImpl(model)).check(model, null, formula);
			StateFormula f2 = formula;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
