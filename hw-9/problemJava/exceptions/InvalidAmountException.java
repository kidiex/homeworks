package exceptions;

public class InvalidAmountException extends BankOperationException {
    public InvalidAmountException(double amount) {
        super("Некорректная сумма: " + amount + ". Сумма должна быть положительной.");
    }
}
