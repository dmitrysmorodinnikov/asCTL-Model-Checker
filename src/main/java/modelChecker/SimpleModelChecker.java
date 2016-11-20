package modelChecker;

import java.io.IOException;
import java.util.Set;

import formula.FormulaParser;
import formula.stateFormula.And;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

public class SimpleModelChecker implements ModelChecker {
	
	private SubsetCalculator satCalculator;
	private ENFGenerator enfGenerator;
	private TraceGenerator traceGenerator;
	private String[] trace;
	
	public SimpleModelChecker(SubsetCalculator satCalculator){
		this.satCalculator = satCalculator;
		this.enfGenerator = new ENFGenerator();
		this.traceGenerator = new TraceGenerator(satCalculator, enfGenerator);
	}

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
    	
    	model.validate();
    	
    	StateFormula finalQuery = query; 
    	if (constraint != null){
    		finalQuery = new And(constraint, query);
    	}
    	
    	//Get the ENF form of the query
    	StateFormula enfQuery = enfGenerator.getENF(finalQuery);
    	//System.out.println("ENF Query: " + enfQuery);
    	
        //Get the set of states that satisfy the formula
    	Set<State> sat = satCalculator.getSat(enfQuery, model.getStatesSet());
    	
    	boolean result = sat.containsAll(model.getInitialStates());
    	if (!result){
    		trace = traceGenerator.getCounterExample(model, query, enfQuery, sat);
    	}
    	
    	//If all the initial states satisfy the formula, then the formula holds
    	return result;
    }

    @Override
    public String[] getTrace() {
    	
    	System.out.print("Trace ");
    	for(String s : trace)
    		System.out.print( " -> " + s);
        return trace;
    }
    
    public String getTraceString(){
    	StringBuilder sb = new StringBuilder();
    	for(String s : trace){
    		sb.append(" -> ");
    		sb.append(s);
    	}
        return sb.toString();
    }
    
    
    public static void main(String[] args){
    	
    	try {
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_6.json").parse();
			SimpleModelChecker modelChecker = new SimpleModelChecker(new SubsetCalculatorImpl(model)); 
			boolean res = modelChecker.check(model, null, formula);
			
			if (res == false){
				modelChecker.getTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
