public class InvalidExpressionException extends RuntimeException {
    public InvalidExpressionException() {
        super("The expression is invalid");
    }

    public InvalidExpressionException(String message) {
        super(message);
    }
}
