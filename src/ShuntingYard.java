import java.util.*;


/**
 * Implementation of Dijkstra's Shunting Yard Algorithm
 * Converts infix expressions to postfix (Reverse Polish Notation)
 *
 * Example: "3 + 4 * 2" becomes "3 4 2 * +"
 *
 * Algorithm Overview:
 * 1. Read tokens from left to right
 * 2. If operand (number), add to output
 * 3. If operator, pop operators from stack based on precedence rules
 * 4. If '(', push to stack
 * 5. If ')', pop until matching '(' is found
 * 6. At end, pop remaining operators to output
 */
public class ShuntingYard {


    /**
     * Converts an infix expression to postfix notation
     *
     * @param infix The infix expression as a string (e.g., "3 + 4 * 2")
     * @return The postfix expression as a string (e.g., "3 4 2 * +")
     *
     * Hint: Use a Stack for operators and a Queue or List for output
     * Consider how to handle:
     * - Operands (numbers)
     * - Operators (+, -, *, /)
     * - Parentheses
     */
    public String infixToPostfix(String infix) {
        // TODO: Implement the shunting yard algorithm
        // You'll need:
        // - A stack to hold operators temporarily
        // - An output queue or list for the result
        // - Logic to tokenize the input string
        // - Logic to handle operator precedence

        //public class Stack
        //public abstract class Queue
        Stack<String> stack= new Stack<>();
        Queue<String> queue= new LinkedList<>();
        String postfix="";

        ArrayList<String> result=this.tokenize(infix);//tokenize first

        for(int i=1;i<result.size();i++)
        {
            if(isOperator(result.get(i)) && isOperator(result.get(i-1))) //check the consecutive operators
            {
                throw new InvalidExpressionException();
            }
            if(i==result.size()-1 && this.isOperator(result.get(i))) //check whether the expression ends with operator
            {
                throw new InvalidExpressionException();
            }
        }

        for(int i=0;i<result.size();i++)
        {
            if(this.isNumber(result.get(i))) //if number, directly add to queue
            {
             queue.add(result.get(i));

            }
            else if(this.isOperator(result.get(i))) //if operator, check precedence
            {
                if(stack.isEmpty())
                {
                    stack.push(result.get(i));
                }
                else if(getPrecedence(result.get(i).charAt(0))==3 && getPrecedence(stack.peek().charAt(0))==3) //handling exponents
                {
                    stack.push(result.get(i));
                }
                else if(getPrecedence(stack.peek().charAt(0))>=getPrecedence(result.get(i).charAt(0)))
                //if the first in stack has a higher or equal precedence than the operator
                {
                    while((!stack.isEmpty()) && (getPrecedence(stack.peek().charAt(0))>=getPrecedence(result.get(i).charAt(0))))
                    //pop everything until the first in stack has a lower precedence
                    {
                        queue.add(stack.peek());
                        stack.pop();
                    }
                    stack.push(result.get(i)); //push the operator into stack
                }
                else //the first in stack has a lower precedence
                {
                    stack.push(result.get(i));
                }
            }
            else if(result.get(i).equals("(")) //handling parentheses
            {
                stack.push(result.get(i));
            }
            else if(result.get(i).equals(")"))
            {
                while(!stack.isEmpty() && !stack.peek().equals("("))//pop everything until the left parentheses
                {
                    queue.add(stack.peek());
                    stack.pop();
                }
                if(stack.isEmpty())//unmatched parentheses
                {
                    throw new InvalidExpressionException();
                }
                stack.pop();
            }

            else//throw exception
            {
                throw new InvalidExpressionException();
            }
        }

        while(!stack.isEmpty()) //add remaining operator to the queue
        {
            queue.add(stack.peek());
            stack.pop();
        }
        while(!queue.isEmpty()) //enqueue everything in the queue
        {
            postfix+=queue.peek()+" ";
            queue.poll();
        }
        return postfix;
    }


    /**
     * Determines the precedence of an operator
     * Higher number = higher precedence
     *
     * @param operator The operator character
     * @return The precedence level (higher is more important)
     *
     * Hint: Multiplication and division have higher precedence than addition and subtraction
     * Standard precedence: *, / = 2; +, - = 1
     */


    private int getPrecedence(char operator) {
        // TODO: Return appropriate precedence values
        if(operator=='^')
            return 3;
        if(operator=='*' || operator=='/')
            return 2;
        if(operator=='+'||operator=='-')
            return 1;
        if(operator=='(')
            return 0;
        return -1;

    }


    /**
     * Checks if a character is an operator
     *
     * @param c The character to check
     * @return true if the character is an operator (+, -, *, /)
     *
     * Hint: Compare against the four basic operators
     */
    private boolean isOperator(String c) {
        // TODO: Check if c is one of: +, -, *, /, ^
        return (c.equals("+") || c.equals("-") || c.equals("*") ||c.equals("/")||c.equals("^"));
    }


    /**
     * Checks if a string is a number (operand)
     *
     * @param token The string to check
     * @return true if the string represents a valid number
     *
     * Hint: You can use try-catch with parsing, or check character by character
     */
    private boolean isNumber(String token) {
        // TODO: Determine if the token is a valid number
        // Consider: integers, decimals, negative numbers

        boolean isValidNumber=true;
        try{
            Double.parseDouble(token);
            isValidNumber=true;
        } catch (NumberFormatException error)
        {
            isValidNumber=false;
        }

        return isValidNumber;
    }


    /**
     * Evaluates a postfix expression and returns the result
     *
     * @param postfix The postfix expression to evaluate
     * @return The numerical result of the expression
     *
     * Hint: Use a stack to store operands
     * When you encounter an operator:
     * 1. Pop two operands
     * 2. Apply the operator
     * 3. Push the result back
     */
    public double evaluatePostfix(String postfix) {
        // TODO: Implement postfix evaluation
        // Algorithm:
        // - For each token:
        //   - If number: push to stack
        //   - If operator: pop two values, compute, push result
        // - Final stack value is the answer
        ArrayList<String> evaluate=new ArrayList<>();
        evaluate=this.tokenize(postfix);

        Stack<String> stack= new Stack<>();
        double num1=0;
        double num2=0;
        String result="";


        for(int i=0;i<evaluate.size();i++)
        {
            if(this.isNumber(evaluate.get(i))) //if number, add directly to stack
            {
             stack.add(evaluate.get(i));
            }
            else if(this.isOperator(evaluate.get(i))) //evaluate the expressions
            {
             num2=Double.parseDouble(stack.peek());
             stack.pop();
             num1=Double.parseDouble(stack.peek());
             stack.pop();
             if(evaluate.get(i).equals("*"))
             {
                 result=String.valueOf(num1*num2);
                 stack.add(result);
             }
             else if(evaluate.get(i).equals("/"))
             {
                 result=String.valueOf(num1/num2);
                 stack.add(result);
             }
             else if(evaluate.get(i).equals("+"))
             {
                 result=String.valueOf(num1+num2);
                 stack.add(result);
             }
             else if(evaluate.get(i).equals("-"))
             {
                 result=String.valueOf(num1-num2);
                 stack.add(result);
             }
             else if(evaluate.get(i).equals("^"))
             {
                 result=String.valueOf(Math.pow(num1,num2));
                 stack.add(result);
             }
            }
        }



        return Double.parseDouble(stack.peek());
    }


    /**
     * Tokenizes an expression string into individual tokens
     *
     * @param expression The expression to tokenize
     * @return A list of tokens (numbers and operators)
     *
     * Hint: Split by spaces, or iterate character by character
     * Consider multi-digit numbers
     */
    public ArrayList<String> tokenize(String expression) {
        // TODO: Break the expression into tokens
        // Example: "3 + -4 * 2" -> ["3", "+", "-4", "*", "2"]
        int check=0;
        String token="";
        boolean checkOperator=false;
        if(expression.isEmpty())
        {
            throw new InvalidExpressionException();
        }
        ArrayList<String> result=new ArrayList<>();
        int newInd=0;
        while(expression.contains(" ")) // separate tokens based on spaces
        {
            newInd=expression.indexOf(" ");
            token=expression.substring(0,newInd);
            result.add(token);
            if(token.equals("("))//check whether there are matching parentheses
            {
             check++;
            }
            if(token.equals(")"))
            {
                check--;
            }
            expression=expression.substring(newInd+1);
        }
        if(expression.equals(")"))//check the last term
        {
            check--;
        }
        if(expression.equals("("))
        {
            check++;
        }
        if(check!=0) //unmatched parentheses
        {
            throw new InvalidExpressionException();
        }

        result.add(expression);

        return result;
    }


}
