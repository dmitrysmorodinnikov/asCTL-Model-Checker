package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

public class ModelCheckerTest_4 {
	@Test
	public void test4_1(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_1.json").parse();
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
	public void test4_2(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_2.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			System.out.print("test4_2 Counterexample: ");
			System.out.println(modelCheker.getTraceString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4_3(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_3.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			System.out.print("test4_3 Counterexample: ");
			System.out.println(modelCheker.getTraceString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4_4(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_4.json").parse();
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
	public void test4_5(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_5.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			System.out.print("test4_5 Counterexample: ");
			System.out.println(modelCheker.getTraceString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4_6(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model4.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula4_6.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			System.out.print("test4_6 Counterexample: ");
			System.out.println(modelCheker.getTraceString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
