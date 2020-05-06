package cs2321;

/**
 * Alex Hromada
 * Assignment 2
 * This Has a method that takes a string infix expression and converts it to a postfix expression
 *
 */
public class InfixToPostfix {
    /* Convert an infix expression and to a postfix expression
     * infix expression : operator is between operands. Ex. 3 + 5
     * postfix Expression: operator is after the operands. Ex. 3 5 +
     *
     * The infixExp expression includes the following
     *      operands:  integer or decimal numbers
     *      and operators: + , - , * , /
     *      and parenthesis: ( , )
     *
     *      For easy parsing the expression, there is a space between operands and operators, parenthesis.
     *  	Ex: "1 * ( 3 + 5 )"
     *      Notice there is no space before the first operand and after the last operand/parentheses.
     *
     * The postExp includes the following
     *      operands:  integer or decimal numbers
     *      and operators: + , - , * , /
     *
     *      For easy parsing the expression, there should have a space between operands and operators.
     *      Ex: "1 3 5 + *"
     *      Notice there is space before the first operand and last operator.
     *      Notice that postExp does not have parenthesis.
     */
    public static String convert(String infixExp) {

        String postFixExp = "";
        DLLStack<String> s = new DLLStack<>();

        // Split string by spaces and put into array
        String[] tokens = infixExp.split(" ");

		for (String t :
			 tokens) {
			System.out.println(t);
		}

        for (String t :
                tokens) {

            // If token is a number, add to postfix String
            if (isNumeric(t)) {
                postFixExp = postFixExp + " " + t;
            }

            // If token is an open parenthesis, push to stack
            else if (t.equals("(")) {
                s.push(t);
            }

            // If token is a closed parenthesis, pop from stack until open parenthesis and add to string
            else if (t.equals(")")) {
                while (!s.isEmpty() && !s.top().equals("(")) {
                    postFixExp = postFixExp + " " + s.pop();
                }
                if (!s.isEmpty() && !s.top().equals("(")) {
                    return "Invalid";
                } else{
					s.pop();
				}
            }

            // If operator, check the top of the stack and push or pop based on precedence
            else {
                while (!s.isEmpty() && operand(s.top()) >= operand(t)) {
					if (s.top().equals("(")) {
						return "Invalid";
					}
                    postFixExp = postFixExp + " " + s.pop();
                }
                s.push(t);
            }
        }

        // Pop remaining tokens from the stack and add to result string
        while (!s.isEmpty()) {
            if (s.top().equals("(")) {
                return "Invalid";
            }
            postFixExp = postFixExp + " " + s.pop();
        }

        return postFixExp;
    }

    /**
     * Tests if the input string is a number
     * @param numStr
     * @return true if number, false if not
     */
    private static boolean isNumeric(String numStr) {

        try {
            double d = Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;

    }

    /**
     * Checks precedence of operators
     * @param s
     * @return integer based om precedence of operator
     */
    private static int operand(String s) {
        switch (s) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
        }
        return -1;
    }
}
