package exceptions;

public class InvalidAccountException extends BankOperationException {
    public InvalidAccountException(String message) {
        super(message);
    }
}
