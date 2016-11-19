package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

public class ModelCheckerTest_5 {
	@Test
	public void test5_1(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model5.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula5_1.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertTrue(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5_2(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model5.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula5_2.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5_3(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model5.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula5_3.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
