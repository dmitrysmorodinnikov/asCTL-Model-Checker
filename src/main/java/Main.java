import java.io.IOException;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;

public class Main {

	public static void main(String[] args){
		
		try {
			FormulaParser parser = new FormulaParser("C:\\Users\\Dmitry\\Desktop\\test.json");
			StateFormula formula = parser.parse();
			System.out.println("!!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}