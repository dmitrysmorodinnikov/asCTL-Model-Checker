package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import modelChecker.ModelChecker;
import modelChecker.SimpleModelChecker;
import model.Model;

public class ModelCheckerTest_1 {	
	@Test
	public void test1_1(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_1.json").parse();
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
	public void test1_2(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_2.json").parse();
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
	public void test1_3(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_3.json").parse();
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
	public void test1_4(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_4.json").parse();
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
	public void test1_5(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_5.json").parse();
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
	public void test1_6(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_6.json").parse();
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
	public void test1_7(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_7.json").parse();
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
	public void test1_8(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_8.json").parse();
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
	public void test1_9(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_9.json").parse();
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
//	public void test1_10(){
//		try {		
//			Model model = Model.parseModel("src/test/resources/models/model1.json");
//			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_10.json").parse();
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
	public void test1_11(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model1.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_11.json").parse();
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
//	public void test1_12(){
//		try {		
//			Model model = Model.parseModel("src/test/resources/models/model1.json");
//			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula1_12.json").parse();
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
}
