import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Account implements Comparable<Account> {
    private final String accountNumber;
    private final String holderName;
    private double balance;

    public Account(String accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString() {
        return String.format("Account[%s | %-12s | Balance: Rs. %.2f]", accountNumber, holderName, balance);
    }
}

class AccountStore {
    private final Map<String, Account> accounts = new HashMap<>();
    private final List<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        accounts.put(account.getAccountNumber(), account);
        transactionHistory.add("Registered account: " + account.getAccountNumber());
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public List<Account> sortedByBalance() {
        List<Account> sortedList = new ArrayList<>(accounts.values());
        sortedList.sort(Comparator.comparing(Account::getBalance, Comparator.reverseOrder()));
        return sortedList;
    }

    public List<String> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
}

class TransactionProcessor {
    private final ConcurrentHashMap<String, Account> sharedAccounts = new ConcurrentHashMap<>();

    public void registerAccount(Account acc) {
        sharedAccounts.put(acc.getAccountNumber(), acc);
    }

    public void executeConcurrentDeposit(String accNumber, double amount) {
        Account acc = sharedAccounts.get(accNumber);
        if (acc != null) {
            acc.deposit(amount);
        }
    }

    public Account getAccount(String accNumber) {
        return sharedAccounts.get(accNumber);
    }
}

public class PartB {
    public static void main(String[] args) {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 12 - Part B: MiniBank Collections Storage, Sorting & Concurrent Map");
        System.out.println();

        AccountStore store = new AccountStore();
        Account a1 = new Account("AC1001", "Rudra Modi", 5000.0);
        Account a2 = new Account("AC1002", "Aarav Patel", 2000.0);
        Account a3 = new Account("AC1003", "Diya Shah", 9000.0);
        Account a4 = new Account("AC1004", "Kabir Mehta", 15400.0);

        store.add(a1);
        store.add(a2);
        store.add(a3);
        store.add(a4);

        System.out.println("Direct Hash Lookup via get('AC1002'):");
        Account fetched = store.get("AC1002");
        System.out.println("  " + fetched);

        System.out.println();
        System.out.println("All Accounts (Natural Order by Account Number via Comparable):");
        List<Account> naturalOrderList = store.getAll();
        Collections.sort(naturalOrderList);
        for (Account a : naturalOrderList) {
            System.out.println("  " + a);
        }

        System.out.println();
        System.out.println("All Accounts (Sorted by Balance Descending via Comparator):");
        List<Account> sortedList = store.sortedByBalance();
        for (Account a : sortedList) {
            System.out.println("  " + a);
        }

        System.out.println();
        System.out.println("Initializing ConcurrentHashMap TransactionProcessor for Multi-threading:");
        TransactionProcessor processor = new TransactionProcessor();
        processor.registerAccount(a1);
        processor.registerAccount(a3);
        processor.executeConcurrentDeposit("AC1001", 2500.0);
        System.out.println("  Updated AC1001 via Concurrent Processor: " + processor.getAccount("AC1001"));
    }
}
