package exceptions;

class BankOperationException extends RuntimeException {
    public BankOperationException(String message) {
        super(message);
    }
}