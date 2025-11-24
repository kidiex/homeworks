import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import java.io.StringReader;

import javax.naming.InsufficientResourcesException;

class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    public void BankAccountInit() {
        account = new BankAccount("Vlad Elec", 1000.0);
    }

    @Test
    @DisplayName("Проверка корректного возвращения баланса после пополнения")
    void testGetBalance_afterDeposit() {
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance());
    }

    @Test
    @DisplayName("Проверка корректного возвращения баланса после снятия")
    void testGetBalance_afterWithdraw() {
        account.withdraw(150.0);
        assertEquals(850.0, account.getBalance());
    }

    @Test
    @DisplayName("Проверка корректного возвращения баланса после перевода")
    void testGetBalance_afterTransfer() {
        BankAccount targetAccount = new BankAccount("Vova Elec", 500.0);
        account.transfer(targetAccount, 200.0);
        assertEquals(800.0, account.getBalance());
        assertEquals(700.0, targetAccount.getBalance());
    }

    @Test
    @DisplayName("Проверка возвращения номера счета")
    void testGetAccountNumber() {
        assertEquals("Vlad Elec", account.getAccountNumber());
    }

    @Test
    @DisplayName("Проверка статуса активности счета - активный по умолчанию")
    void testIsActive_default() {
        assertTrue(account.isActive());
    }

    @Test
    @DisplayName("Проверка статуса активности счета - после деактивации")
    void testIsActive_afterDeactivate() {
        account.deactivate();
        assertFalse(account.isActive());
    }

    @Test
    @DisplayName("Проверка расчета процентов для активного счета")
    void testCalculateMonthlyInterest_activeAccount() {
        double expectedInterest = 1000.0 * (0.06 / 12.0);
        assertEquals(expectedInterest, account.calculateMonthlyInterest(6));
    }

    @Test
    @DisplayName("Проверка возвращения 0 процентов для неактивного счета")
    void testCalculateMonthlyInterest_inactiveAccount() {
        account.deactivate();
        assertEquals(0.0, account.calculateMonthlyInterest(6));
    }

    @Test
    @DisplayName("Проверка расчета с разными процентными ставками")
    void testCalculateMonthlyInterest_differentRates() {
        double expectedInterest1 = 1000.0 * (0.03 / 12.0);
        assertEquals(expectedInterest1, account.calculateMonthlyInterest(3));

        double expectedInterest2 = 1000.0 * (0.12 / 12.0);
        assertEquals(expectedInterest2, account.calculateMonthlyInterest(12));
    }

    @Test
    @DisplayName("Проверка генерации IllegalArgumentException при null номере счета")
    void testConstructor_nullAccountNumber_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount(null, 500.0);
        });
        assertEquals("Account number cannot be null or empty", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации исключения при отрицательном начальном балансе")
    void testConstructor_negativeInitialBalance_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount("Vova Elec", -100.0);
        });
        assertEquals("Initial balance cannot be negative", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IllegalStateException при пополнении неактивного счета")
    void testDeposit_inactiveAccount_throwsException() {
        account.deactivate();
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            account.deposit(100.0);
        });
        assertEquals("Cannot deposit to inactive account", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IllegalArgumentException при отрицательной сумме пополнения")
    void testDeposit_negativeDeposit_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100.0);
        });
        assertEquals("Deposit amount must be positive", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации InsufficientFundsException при недостатке средств")
    void testWithdraw_insufficientFunds_throwsException() {
        InsufficientFundsException thrown = assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(1000.1);
        });
        assertEquals("Insufficient funds for withdrawal", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IllegalStateException при снятии с неактивного счета")
    void testWithdraw_inactiveAccount_throwsException() {
        account.deactivate();
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            account.withdraw(100.0);
        });
        assertEquals("Cannot withdraw from inactive account", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IllegalArgumentException при снятии отрицательной суммы")
    void testWithdraw_negativeWithdraw_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-100.0);
        });
        assertEquals("Withdrawal amount must be positive", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IllegalArgumentException при снятии нулевой суммы")
    void testWithdraw_zeroWithdraw_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0.0);
        });
        assertEquals("Withdrawal amount must be positive", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации исключений при null целевом счете")
    void testTransfer_nullTargetAccount_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            account.transfer(null, 100.0);
        });
        assertEquals("Target account cannot be null", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации исключений при переводе на неактивный целевой счет")
    void testTransfer_inactiveTargetAccount_throwsException() {
        BankAccount targetAccount = new BankAccount("Vova Elec", 500.0);
        targetAccount.deactivate();
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            account.transfer(targetAccount, 100.0);
        });
        assertEquals("Cannot transfer to inactive account", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка, что метод не бросает исключения при валидных параметрах(1)")
    void testDeposit_validAmount_doesNotThrowException() {
        assertDoesNotThrow(() -> account.deposit(100.0));
        assertEquals(1100.0, account.getBalance());
    }

    @Test
    @DisplayName("Проверка, что метод не бросает исключения при валидных параметрах(2)")
    void testWithdraw_validAmount_doesNotThrowException() {
        assertDoesNotThrow(() -> account.withdraw(100.0));
        assertEquals(900.0, account.getBalance());
    }

    @Test
    @DisplayName("Проверка, что метод не бросает исключения при корректных данных")
    void testTransfer_validData_doesNotThrowException() {
        BankAccount targetAccount = new BankAccount("Vova Elec", 500.0);
        assertDoesNotThrow(() -> account.transfer(targetAccount, 100.0));
        assertEquals(900.0, account.getBalance());
        assertEquals(600.0, targetAccount.getBalance());
    }

    @Test
    @DisplayName("Проверка, что расчет процентов не бросает исключения при положительной ставке")
    void testCalculateMonthlyInterest_positiveRate_doesNotThrowException() {
        assertDoesNotThrow(() -> account.calculateMonthlyInterest(5));
        assertEquals(1000.0 * (0.05 / 12.0), account.calculateMonthlyInterest(5));
    }

    @Test
    @DisplayName("Проверка, что расчет процентов не бросает исключения при нулевой ставке")
    void testCalculateMonthlyInterest_zeroRate_doesNotThrowException() {
        assertDoesNotThrow(() -> account.calculateMonthlyInterest(0.0));
        assertEquals(0.0, account.calculateMonthlyInterest(0.0));
    }

    @Test
    @DisplayName("Проверка корректного создания объекта из валидных данных")
    void testReadFromReader_validData_createsCorrectAccount() throws IOException {
        String data = "Vova Delec,2500.0,true";
        StringReader reader = new StringReader(data);
        BankAccount newAccount = BankAccount.readFromReader(reader);

        assertNotNull(newAccount);
        assertEquals("Vova Delec", newAccount.getAccountNumber());
        assertEquals(2500.0, newAccount.getBalance());
        assertTrue(newAccount.isActive());
    }

    @Test
    @DisplayName("Проверка обработки неактивного счета при чтении")
    void testReadFromReader_inactiveAccountData_createsInactiveAccount() throws IOException {
        String data = "Vova Delec,500.0,false";
        StringReader reader = new StringReader(data);
        BankAccount newAccount = BankAccount.readFromReader(reader);

        assertNotNull(newAccount);
        assertEquals("Vova Delec", newAccount.getAccountNumber());
        assertEquals(500.0, newAccount.getBalance());
        assertFalse(newAccount.isActive());
    }

    @Test
    @DisplayName("Проверка генерации IOException при неверном формате данных (недостаточно частей)")
    void testReadFromReader_invalidFormat_notEnoughParts_throwsIOException() {
        String data = "Vova Delec,100.0";
        StringReader reader = new StringReader(data);

        IOException thrown = assertThrows(IOException.class, () -> BankAccount.readFromReader(reader));
        assertEquals("Invalid data format", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IOException при неверном формате данных (слишком много частей)")
    void testReadFromReader_invalidFormat_tooManyParts_throwsIOException() {
        String data = "Vova Delec,100.0,true,extra";
        StringReader reader = new StringReader(data);

        IOException thrown = assertThrows(IOException.class, () -> BankAccount.readFromReader(reader));
        assertEquals("Invalid data format", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IOException при неверном формате данных (невалидное число)")
    void testReadFromReader_invalidNumberFormat_throwsIOException() {
        String data = "Vova Delec,stringValue,true";
        StringReader reader = new StringReader(data);

        IOException thrown = assertThrows(IOException.class, () -> BankAccount.readFromReader(reader));
        assertTrue(thrown.getMessage().contains("Invalid number format in data"));
    }


    @Test
    @DisplayName("Проверка генерации IOException при пустых данных")
    void testReadFromReader_emptyData_throwsIOException() {
        String data = "";
        StringReader reader = new StringReader(data);

        IOException thrown = assertThrows(IOException.class, () -> BankAccount.readFromReader(reader));
        assertEquals("Empty or null data in reader", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IOException при null данных в потоке")
    void testReadFromReader_nullData_throwsIOException() {
        StringReader reader = new StringReader("");
        IOException thrown = assertThrows(IOException.class, () -> BankAccount.readFromReader(reader));
        assertEquals("Empty or null data in reader", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации IOException при отрицательном балансе в данных")
    void testReadFromReader_negativeBalanceInData_throwsIOException() {
        String data = "Vova Delec,-100.0,true";
        StringReader reader = new StringReader(data);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> BankAccount.readFromReader(reader));
        assertTrue(thrown.getMessage().contains("Initial balance cannot be negative"));
    }

    @Test
    @DisplayName("Проверка генерации IOException при пустом номере счета в данных")
    void testReadFromReader_emptyAccountNumberInData_throwsIOException() {
        String data = " ,1000.0,true";
        StringReader reader = new StringReader(data);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> BankAccount.readFromReader(reader));
        assertTrue(thrown.getMessage().contains("Account number cannot be null or empty"));
    }


}