public class Main {
    public static void main(String[] args) {

        //The infix notation has to be separated by spaces
        ShuntingYard shuntingYard = new ShuntingYard();

        System.out.println(shuntingYard.infixToPostfix("+ 3 + ( 2 - ( 1 +  -1 ) ) )"));
    }
}