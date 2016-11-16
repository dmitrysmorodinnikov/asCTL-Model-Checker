package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import modelChecker.ModelChecker;
import modelChecker.SimpleModelChecker;
import model.Model;

public class ModelCheckerTest_2 {
	@Test
	public void test2_1(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_1.json").parse();
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
	public void test2_2(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_2.json").parse();
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
	public void test2_3(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_3.json").parse();
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
	public void test2_4(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_4.json").parse();
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
	public void test2_5(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_5.json").parse();
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
	public void test2_6(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_6.json").parse();
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
	public void test2_7(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_7.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertFalse(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void test2_8(){
//		try {		
//			Model model = Model.parseModel("src/test/resources/models/model2.json");
//			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_8.json").parse();
//			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
//			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
//			boolean actualOuput = modelCheker.check(model, null, formula);
//			assertFalse(actualOuput);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void test2_9(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_9.json").parse();
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
	public void test2_10(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_10.json").parse();
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
	public void test2_11(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model2.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula2_11.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, null, formula);
			assertTrue(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
