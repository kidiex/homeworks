package exceptions;

public class InsufficientFundsException extends BankOperationException {
    private final double currentBalance;
    private final double requestedAmount;
    
    public InsufficientFundsException(double currentBalance, double requestedAmount) {
        super("Недостаточно средств. Текущий баланс: " + currentBalance + 
              ", запрошенная сумма: " + requestedAmount);
        this.currentBalance = currentBalance;
        this.requestedAmount = requestedAmount;
    }
    
    public double getCurrentBalance() { return currentBalance; }
    public double getRequestedAmount() { return requestedAmount; }
}
