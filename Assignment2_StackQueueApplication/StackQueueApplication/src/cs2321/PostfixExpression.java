package cs2321;

/**
 * Alex Hromada
 * Assignment 2
 * This class has a method that receives a postfix expression and evaluates it to an integer result
 *
 */
public class PostfixExpression {
	
	/**
	 * Evaluate a postfix expression. 
	 * Postfix expression notation has operands first, following by the operations.
	 * For example:
	 *    13 5 *           is same as 13 * 5 
	 *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6  
	 *    
	 * In this homework, expression in the argument only contains
	 *     integer, +, -, *, / and a space between every number and operation. 
	 * You may assume the result will be integer as well. 
	 * 
	 * @param exp The postfix expression
	 * @return the result of the expression
	 */
	public static int evaluate(String exp) {
		DLLStack<Integer> s = new DLLStack<>();
		String[] tokens = exp.split(" ");

		// Loop through each token from the string
		for (String t:
			 tokens) {

			// If token is a number, push to the stack
			if(isNumeric(t)){
				int num = Integer.parseInt(t);
				s.push(num);
			}

			else{

				// Pop the numbers from the stack set to temp variables
				int num1 = s.pop();
				int num2 = s.pop();


				switch (t){
					case "+":
						s.push(num2 + num1);
						break;
					case "-":
						s.push(num2 - num1);
						break;
					case "*":
						s.push(num2 * num1);
						break;
					case "/":
						// Throw ArithmeticException if it tried to divide by 0
						if(num1 == 0){
							throw new ArithmeticException("Divide by 0");
						}
						s.push(num2 / num1);
						break;
				}

			}

		}
		// Pop the final result and return
		return s.pop();
	}

	/**
	 * Helper method that tests if a given token is a number
	 * @param numStr token
	 * @return true if a number, false otherwise
	 */
	private static boolean isNumeric(String numStr) {

		try {
			int i = Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;

	}
	
}
