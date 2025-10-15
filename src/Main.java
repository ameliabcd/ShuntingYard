public class Main {
    public static void main(String[] args) {

        //The infix and postfix notation has to be separated by one space for each token
        ShuntingYard shuntingYard = new ShuntingYard();

        System.out.println(shuntingYard.infixToPostfix("3 + ( 2 - ( 1 + 2 * 3 ^ 2 ) )"));
        System.out.println(shuntingYard.evaluatePostfix("3 2 1 2 3 2 ^ * + - +"));
    }
}