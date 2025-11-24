import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class BankAccount {
    private double balance;
    private String accountNumber;
    private boolean isActive;
    
    public BankAccount(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
    }
    
    /**
     * Пополнение счета
     */
    public void deposit(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Cannot deposit to inactive account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }
    
    /**
     * Снятие средств
     */
    public void withdraw(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Cannot withdraw from inactive account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        balance -= amount;
    }
    
    /**
     * Перевод средств на другой счет
     */
    public void transfer(BankAccount targetAccount, double amount) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        if (!targetAccount.isActive()) {
            throw new IllegalStateException("Cannot transfer to inactive account");
        }
        this.withdraw(amount);
        targetAccount.deposit(amount);
    }
    
    /**
     * Расчет ежемесячных процентов
     */
    public double calculateMonthlyInterest(double annualRate) {
        if (annualRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (!isActive) {
            return 0.0;
        }
        return balance * (annualRate / 12) / 100;
    }
    
    /**
     * Чтение информации о счете из Reader
     * Формат: "accountNumber,balance,isActive"
     */
    public static BankAccount readFromReader(Reader reader) throws IOException {
        try (BufferedReader br = new BufferedReader(reader)) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Empty or null data in reader");
            }
            
            String[] parts = line.split(",");
            if (parts.length != 3) {
                throw new IOException("Invalid data format");
            }
            
            String accNumber = parts[0];
            double balance = Double.parseDouble(parts[1]);
            boolean active = Boolean.parseBoolean(parts[2]);
            
            BankAccount account = new BankAccount(accNumber, balance);
            if (!active) {
                account.deactivate();
            }
            return account;
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in data", e);
        }
    }
    
    // Геттеры
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public boolean isActive() { return isActive; }
    
    public void deactivate() { this.isActive = false; }
    public void activate() { this.isActive = true; }
}
