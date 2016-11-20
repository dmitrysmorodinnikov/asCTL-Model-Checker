package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

public class ModelCheckerTest_MutualExclusion {
	@Test
	public void test1(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_1.json").parse();
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
	public void test2(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_2.json").parse();
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
	public void test3(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_3.json").parse();
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
	public void test4(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_4.json").parse();
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
	public void test5(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_5.json").parse();
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
	public void test6(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_6.json").parse();
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
	public void test7(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_7.json").parse();
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
	public void test8(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_8.json").parse();
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
	public void test9(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_9.json").parse();
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
	public void test10(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_10.json").parse();
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
	public void test11(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_11.json").parse();
			StateFormula constraint = new FormulaParser("src/test/resources/constraints/constraint1.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, constraint, formula);
			assertFalse(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test12(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model_mutual.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula_mutual_12.json").parse();
			StateFormula constraint = new FormulaParser("src/test/resources/constraints/constraint1.json").parse();
			SubsetCalculator calculator = new SubsetCalculatorImpl(model);
			SimpleModelChecker modelCheker = new SimpleModelChecker(calculator);
			boolean actualOuput = modelCheker.check(model, constraint, formula);
			assertFalse(actualOuput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
