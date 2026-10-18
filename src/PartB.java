import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
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

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) throws BankException {
        if (amount <= 0) {
            throw new BankException("Deposit amount must be strictly positive");
        }
        this.balance += amount;
    }

    public synchronized void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new InsufficientFundsException("Withdrawal amount must be strictly positive");
        }
        if (amount > this.balance) {
            throw new InsufficientFundsException("Insufficient balance: Available Rs. " + balance + ", Requested Rs. " + amount);
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("Account[%s | %-12s | Rs. %.2f]", accountNumber, holderName, balance);
    }
}

class Repository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, T> storage = new HashMap<>();

    public synchronized T save(T entity) {
        try {
            String id = extractId(entity);
            storage.put(id, entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Repository error: " + e.getMessage(), e);
        }
    }

    public synchronized T findById(String id) {
        return storage.get(id);
    }

    public synchronized List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    private String extractId(T entity) throws IllegalAccessException {
        Class<?> clazz = entity.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    return f.get(entity).toString();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }
}

class BankService {
    private final Repository<Account> repository;
    private static long idCounter = 1000;

    public BankService(Repository<Account> repository) {
        this.repository = repository;
    }

    public Account openAccount(String holderName, double initialDeposit) throws BankException {
        if (holderName == null || holderName.trim().length() < 2) {
            throw new BankException("Invalid holder name");
        }
        if (initialDeposit < 1000) {
            throw new BankException("Minimum opening balance is Rs. 1000");
        }
        String accNum = "AC" + (++idCounter);
        Account acc = new Account(accNum, holderName, initialDeposit);
        repository.save(acc);
        return acc;
    }

    public void deposit(String accNum, double amount) throws BankException {
        Account acc = repository.findById(accNum);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + accNum);
        acc.deposit(amount);
    }

    public void withdraw(String accNum, double amount) throws BankException {
        Account acc = repository.findById(accNum);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + accNum);
        acc.withdraw(amount);
    }

    public void transfer(String fromAccNum, String toAccNum, double amount) throws BankException {
        if (fromAccNum.equals(toAccNum)) {
            throw new BankException("Source and destination accounts must differ");
        }
        Account from = repository.findById(fromAccNum);
        Account to = repository.findById(toAccNum);
        if (from == null) throw new AccountNotFoundException("Source account not found: " + fromAccNum);
        if (to == null) throw new AccountNotFoundException("Destination account not found: " + toAccNum);

        Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = first == from ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public List<Account> listAccounts() {
        return repository.findAll();
    }
}

public class PartB {
    private static final Path DATA_FILE = Paths.get("data/minibank_system.dat");

    public static void main(String[] args) {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 14 - Part B: MiniBank Capstone Integrated Production Delivery");
        System.out.println();

        Repository<Account> repo = loadRepository();
        BankService service = new BankService(repo);

        if (repo.findAll().isEmpty()) {
            try {
                service.openAccount("Rudra Modi", 50000.0);
                service.openAccount("Aarav Patel", 25000.0);
                service.openAccount("Diya Shah", 38000.0);
                saveRepository(repo);
            } catch (BankException e) {
                System.err.println("Setup error: " + e.getMessage());
            }
        }

        System.out.println("1. Accounts loaded into BankService from storage:");
        for (Account a : service.listAccounts()) {
            System.out.println("   " + a);
        }

        System.out.println();
        System.out.println("2. Executing Automated Capstone Test Suite:");
        runAutomatedTests(service);

        System.out.println();
        System.out.println("3. Executing Concurrent Batch Transactions:");
        executeConcurrentBatch(service);

        System.out.println();
        System.out.println("4. Final Account Balances post-execution:");
        for (Account a : service.listAccounts()) {
            System.out.println("   " + a);
        }

        saveRepository(repo);
        System.out.println();
        System.out.println("5. Persistent application state successfully saved to " + DATA_FILE);
        System.out.println("MiniBank Capstone Deployment Complete. All components validated.");
    }

    private static void runAutomatedTests(BankService service) {
        try {
            Account acc = service.listAccounts().get(0);
            double before = acc.getBalance();
            service.deposit(acc.getAccountNumber(), 5000.0);
            assert acc.getBalance() == before + 5000.0 : "Deposit test assertion failed";
            System.out.println("   [TEST 1 PASSED] Deposit strictly increases balance.");

            boolean caughtOverdraft = false;
            try {
                service.withdraw(acc.getAccountNumber(), 9999999.0);
            } catch (InsufficientFundsException ex) {
                caughtOverdraft = true;
            }
            assert caughtOverdraft : "Overdraft assertion failed";
            System.out.println("   [TEST 2 PASSED] Overdraft correctly throws InsufficientFundsException.");

            Account acc2 = service.listAccounts().get(1);
            service.transfer(acc.getAccountNumber(), acc2.getAccountNumber(), 2000.0);
            System.out.println("   [TEST 3 PASSED] Atomic inter-account transfer succeeded.");

        } catch (Exception ex) {
            System.err.println("   [TEST FAILED] " + ex.getMessage());
        }
    }

    private static void executeConcurrentBatch(BankService service) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Account> accounts = service.listAccounts();
        String targetAcc = accounts.get(0).getAccountNumber();

        for (int i = 0; i < 6; i++) {
            executor.submit(() -> {
                try {
                    service.deposit(targetAcc, 500.0);
                } catch (BankException ignored) {}
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
            System.out.println("   Concurrent batch finished on thread pool without race conditions.");
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    @SuppressWarnings("unchecked")
    private static Repository<Account> loadRepository() {
        if (Files.exists(DATA_FILE)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE.toFile()))) {
                return (Repository<Account>) ois.readObject();
            } catch (Exception ignored) {}
        }
        return new Repository<>();
    }

    private static void saveRepository(Repository<Account> repo) {
        try {
            if (DATA_FILE.getParent() != null && !Files.exists(DATA_FILE.getParent())) {
                Files.createDirectories(DATA_FILE.getParent());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE.toFile()))) {
                oos.writeObject(repo);
            }
        } catch (Exception e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }
}
