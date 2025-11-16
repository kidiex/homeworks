import model.*;
import exceptions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Тестирование банковских операций ===");
        
        // Создаем счета
        BankAccount account1 = new BankAccount("123456", 1000.0, "Иван Иванов");
        BankAccount account2 = new BankAccount("654321", 500.0, "Петр Петров");
        
        // Тестируем операции
        System.out.println(account1);
        System.out.println(account2);
        
        System.out.println("\n1. Тест внесения средств:");
        try {
            account1.deposit(200.0);
            System.out.println("Внесено: " + 200.0 + ". Новый баланс: " + account1.getBalance());
        }
        catch(InvalidAmountException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n2. Тест внесения средств:");
        try {
            account1.deposit(-200.0);
            System.out.println("Внесено: " + -200.0 + ". Новый баланс: " + account1.getBalance());
        } catch(InvalidAmountException e) {
            System.out.println(e.getMessage());
        }
        
        
        System.out.println("\n3. Тест снятия средств:");
        try {
            account1.withdraw(300.0);
            System.out.println("Снято: " + 300.0 + ". Новый баланс: " + account1.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n4. Тест снятия средств:");
        try {
            account1.withdraw(900.0);
            System.out.println("Снято: " + 900.0 + ". Новый баланс: " + account1.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n5. Тест снятия средств:");
        try {
            account1.withdraw(1900.0);
            System.out.println("Снято: " + 1900.0 + ". Новый баланс: " + account1.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        
        
        System.out.println("\n6. Тест перевода:");
        try {
            account1.transfer(account2, 400.0);
            System.out.println("Перевод " + 400.0 + " со счета " + 
                account1.getAccountNumber() + " на счет " 
                + account2.getAccountNumber());
            System.out.println("Перевод завершен");
        } catch (InvalidAccountException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n7. Попытка перевода на несуществующий счет:");
        try {
            account1.transfer(null, 100.0);
            System.out.println("Перевод " + 400.0 + " со счета " + 
                account1.getAccountNumber() + " на счет " 
                + account2.getAccountNumber());
            System.out.println("Перевод завершен");
        } catch (InvalidAccountException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\n8. Тест начисления процентов:");
        try {
            account1.applyInterest(0.05);  // 5%
            System.out.println("Начислены проценты: " + 0.05 + ". Новый баланс: " + account1.getBalance());
        } catch (InvalidAccountException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        
        
        System.out.println("\nИтоговое состояние счетов:");
        System.out.println();
        System.out.println(account1);
        System.out.println();
        System.out.println(account2);
    }
}
