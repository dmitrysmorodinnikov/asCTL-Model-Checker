package modelChecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;

public class ModelCheckerTest_3 {

	@Test
	public void test3_1(){
		try {		
			Model model = Model.parseModel("src/test/resources/models/model3.json");
			StateFormula formula = new FormulaParser("src/test/resources/formulas/formula3_1.json").parse();
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
