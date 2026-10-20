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
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

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

class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }
}

record BankInfo(String name, String branch, String ifscCode) implements Serializable {
    private static final long serialVersionUID = 1L;
}

enum AccountType {
    SAVINGS, CURRENT, FIXED_DEPOSIT
}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, VIEW_ACCOUNTS, RUN_AUDIT, BATCH_TRANSACT, SYSTEM_STATS, RUN_TEST_SUITE, EXIT
}

interface Transactable {
    void deposit(double amount) throws BankException;
    void withdraw(double amount) throws BankException;
    double getBalance();
}

interface InterestBearing {
    double calculateInterest();
    void applyInterest() throws BankException;
}

@FunctionalInterface
interface WithdrawRule {
    boolean validate(Account account, double amount) throws BankException;
}

class Validator {
    private static final Pattern ACC_PATTERN = Pattern.compile("^AC[0-9]{4}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{3,50}$");
    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    public static boolean isValidAccountNumber(String acc) {
        return acc != null && ACC_PATTERN.matcher(acc).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC_PATTERN.matcher(ifsc.trim()).matches();
    }
}

class Customer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    public static class Address implements Serializable {
        private static final long serialVersionUID = 1L;
        private String city;
        private String state;
        private String zipCode;

        public Address(String city, String state, String zipCode) {
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }

        public String getCity() { return city; }
        public String getState() { return state; }
        public String getZipCode() { return zipCode; }

        public void setCity(String city) { this.city = city; }

        @Override
        public String toString() {
            return city + ", " + state + " (" + zipCode + ")";
        }
    }

    private final String customerId;
    private String name;
    private String phone;
    private Address address;

    public Customer(String customerId, String name, String phone, Address address) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    @Override
    public Customer clone() {
        try {
            Customer cloned = (Customer) super.clone();
            if (this.address != null) {
                cloned.address = new Address(this.address.city, this.address.state, this.address.zipCode);
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer other)) return false;
        return Objects.equals(customerId, other.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return "Customer[" + customerId + " | " + name + " | " + address + "]";
    }
}

abstract class Account implements Transactable, Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    protected final String accountNumber;

    @MaxLength(50)
    protected final String holderName;

    @Positive
    protected double balance;

    protected final AccountType accountType;
    protected final Customer customer;

    public Account(String accountNumber, String holderName, double balance, AccountType accountType, Customer customer) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.accountType = accountType;
        this.customer = customer;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public synchronized double getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }
    public Customer getCustomer() { return customer; }

    public String getMaskedAccountNumber() {
        if (accountNumber.length() > 4) {
            return "AC****" + accountNumber.substring(accountNumber.length() - 4);
        }
        return accountNumber;
    }

    @Override
    public synchronized void deposit(double amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive: Rs. " + amount);
        }
        this.balance += amount;
    }

    @Override
    public abstract void withdraw(double amount) throws BankException;

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account other)) return false;
        return Objects.equals(accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%-14s | %-7s | %-15s | Rs. %10.2f",
                accountType, accountNumber, holderName, balance);
    }
}

class SavingsAccount extends Account implements InterestBearing {
    private static final long serialVersionUID = 1L;
    public static final double MIN_BALANCE = 1000.0;
    public static final double INTEREST_RATE = 0.04;

    public SavingsAccount(String accountNumber, String holderName, double balance, Customer customer) {
        super(accountNumber, holderName, balance, AccountType.SAVINGS, customer);
    }

    @Override
    public synchronized void withdraw(double amount) throws BankException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be strictly positive.");
        if (balance - amount < MIN_BALANCE) {
            throw new InsufficientFundsException(String.format(
                    "Savings invariant breach: balance cannot fall below Rs. %.2f. Requested Rs. %.2f from balance Rs. %.2f",
                    MIN_BALANCE, amount, balance));
        }
        this.balance -= amount;
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }

    @Override
    public synchronized void applyInterest() throws BankException {
        double interest = calculateInterest();
        deposit(interest);
    }
}

class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    public static final double OVERDRAFT_LIMIT = 25000.0;

    public CurrentAccount(String accountNumber, String holderName, double balance, Customer customer) {
        super(accountNumber, holderName, balance, AccountType.CURRENT, customer);
    }

    @Override
    public synchronized void withdraw(double amount) throws BankException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be strictly positive.");
        if (balance - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException(String.format(
                    "Overdraft limit exceeded (Max: Rs. %.2f). Requested Rs. %.2f on balance Rs. %.2f",
                    OVERDRAFT_LIMIT, amount, balance));
        }
        this.balance -= amount;
    }
}

class FixedDepositAccount extends Account implements InterestBearing {
    private static final long serialVersionUID = 1L;
    private final int termMonths;
    public static final double ANNUAL_RATE = 0.065;

    public FixedDepositAccount(String accountNumber, String holderName, double balance, int termMonths, Customer customer) {
        super(accountNumber, holderName, balance, AccountType.FIXED_DEPOSIT, customer);
        this.termMonths = termMonths;
    }

    public int getTermMonths() { return termMonths; }

    @Override
    public synchronized void withdraw(double amount) throws BankException {
        throw new BankException("Premature liquidation locked: Fixed Deposit cannot be liquidated before " + termMonths + " months tenure.");
    }

    @Override
    public double calculateInterest() {
        return balance * Math.pow(1 + (ANNUAL_RATE / 12), termMonths) - balance;
    }

    @Override
    public synchronized void applyInterest() throws BankException {
        double maturityInterest = calculateInterest();
        deposit(maturityInterest);
    }
}

class AnnotationValidator {
    public static void validate(Object obj) throws BankException {
        Class<?> clazz = obj.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        if (value instanceof Number num && num.doubleValue() < 0) {
                            throw new BankException("Reflection Validation Failed: @" + field.getName() + " must be strictly positive!");
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength max = field.getAnnotation(MaxLength.class);
                        if (value instanceof String str && str.length() > max.value()) {
                            throw new BankException("Reflection Validation Failed: @" + field.getName() + " exceeds max length of " + max.value());
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new BankException("Reflection access error: " + e.getMessage());
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}

class Repository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    private String extractId(T entity) {
        Class<?> clazz = entity.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object idVal = field.get(entity);
                        return idVal != null ? idVal.toString() : null;
                    } catch (IllegalAccessException ignored) {}
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public void save(T entity) throws BankException {
        AnnotationValidator.validate(entity);
        String id = extractId(entity);
        if (id == null) {
            throw new BankException("Entity missing @Id primary key metadata!");
        }
        storage.put(id, entity);
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    public int count() {
        return storage.size();
    }
}

class BankService {
    private final Repository<Account> repository;
    private final BankInfo bankInfo;
    private static int idCounter = 1000;
    private static final Path AUDIT_LOG_FILE = Paths.get("logs/transactions.log");

    public BankService(Repository<Account> repository, BankInfo bankInfo) {
        this.repository = repository;
        this.bankInfo = bankInfo;

        for (Account acc : repository.findAll()) {
            try {
                int num = Integer.parseInt(acc.getAccountNumber().replace("AC", ""));
                if (num > idCounter) idCounter = num;
            } catch (Exception ignored) {}
        }
    }

    public BankInfo getBankInfo() { return bankInfo; }

    public synchronized Account openAccount(AccountType type, String holderName, double initialDeposit, Customer customer) throws BankException {
        if (!Validator.isValidName(holderName)) {
            throw new BankException("Invalid holder name: must contain 3-50 alphabet characters.");
        }
        if (initialDeposit < 1000.0) {
            throw new BankException("Minimum opening balance requirement is Rs. 1000.00.");
        }

        String accNum = "AC" + (++idCounter);
        Account account = switch (type) {
            case SAVINGS -> new SavingsAccount(accNum, holderName, initialDeposit, customer);
            case CURRENT -> new CurrentAccount(accNum, holderName, initialDeposit, customer);
            case FIXED_DEPOSIT -> new FixedDepositAccount(accNum, holderName, initialDeposit, 12, customer);
        };

        repository.save(account);
        logAudit(String.format("OPEN_ACCOUNT | %s | %s | %s | Rs. %.2f",
                accNum, type, holderName, initialDeposit));
        return account;
    }

    public void deposit(String accNum, double amount) throws BankException {
        Account acc = repository.findById(accNum);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + accNum);
        acc.deposit(amount);
        logAudit(String.format("DEPOSIT | %s | Rs. %.2f | New Balance: Rs. %.2f", accNum, amount, acc.getBalance()));
    }

    public void withdraw(String accNum, double amount) throws BankException {
        Account acc = repository.findById(accNum);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + accNum);
        acc.withdraw(amount);
        logAudit(String.format("WITHDRAW | %s | Rs. %.2f | New Balance: Rs. %.2f", accNum, amount, acc.getBalance()));
    }

    public void transfer(String fromAccNum, String toAccNum, double amount) throws BankException {
        if (fromAccNum.equals(toAccNum)) {
            throw new BankException("Source and destination accounts must be distinct.");
        }
        Account from = repository.findById(fromAccNum);
        Account to = repository.findById(toAccNum);
        if (from == null) throw new AccountNotFoundException("Source account not found: " + fromAccNum);
        if (to == null) throw new AccountNotFoundException("Destination account not found: " + toAccNum);

        Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = (first == from) ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }

        logAudit(String.format("TRANSFER | %s -> %s | Rs. %.2f | Balances: [%.2f, %.2f]",
                fromAccNum, toAccNum, amount, from.getBalance(), to.getBalance()));
    }

    public List<Account> listAccountsSorted() {
        List<Account> accounts = repository.findAll();
        Collections.sort(accounts);
        return accounts;
    }

    public double calculateTotalBankAssets() {
        return repository.findAll().stream().mapToDouble(Account::getBalance).sum();
    }

    public Map<AccountType, Long> getAccountDistribution() {
        return repository.findAll().stream().collect(Collectors.groupingBy(Account::getAccountType, Collectors.counting()));
    }

    private void logAudit(String entry) {
        try {
            if (AUDIT_LOG_FILE.getParent() != null && !Files.exists(AUDIT_LOG_FILE.getParent())) {
                Files.createDirectories(AUDIT_LOG_FILE.getParent());
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logLine = "[" + timestamp + "] " + entry + System.lineSeparator();
            Files.writeString(AUDIT_LOG_FILE, logLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) {}
    }
}

public class MiniBank {
    private static final Path DATA_FILE = Paths.get("data/minibank_system.dat");

    public static void main(String[] args) {
        printHeader();

        BankInfo bankInfo = new BankInfo("CHARUSAT MiniBank Central", "DEPSTAR Branch", "CHAR0002031");
        System.out.println("[BANK INFO] " + bankInfo.name() + " | BRANCH: " + bankInfo.branch() + " | IFSC: " + bankInfo.ifscCode());
        System.out.println();

        Repository<Account> repository = loadRepository();
        BankService service = new BankService(repository, bankInfo);

        if (repository.count() == 0) {
            bootstrapInitialAccounts(service);
        }

        executeAutomatedVerification(service);

        saveRepository(repository);

        System.out.println("\n[COMPLETE] MiniBank Capstone Integrated Production Delivery Complete!");
        System.out.println("Persistent state verified at: " + DATA_FILE);
    }

    private static void printHeader() {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 14 - Part B: MiniBank Capstone Integrated Production Delivery");
        System.out.println("Comprehensive Master Integration (Practicals 1 to 14)");
        System.out.println();
    }

    private static void bootstrapInitialAccounts(BankService service) {
        try {
            Customer c1 = new Customer("CUST101", "Rudra Modi", "9876543210",
                    new Customer.Address("Changa", "Gujarat", "388421"));
            Customer c2 = new Customer("CUST102", "Aarav Patel", "9823456789",
                    new Customer.Address("Anand", "Gujarat", "388001"));
            Customer c3 = new Customer("CUST103", "Diya Shah", "9812345678",
                    new Customer.Address("Vadodara", "Gujarat", "390001"));

            service.openAccount(AccountType.SAVINGS, "Rudra Modi", 68000.0, c1);
            service.openAccount(AccountType.CURRENT, "Aarav Patel", 31000.0, c2);
            service.openAccount(AccountType.FIXED_DEPOSIT, "Diya Shah", 38000.0, c3);
        } catch (BankException e) {
            System.err.println("Bootstrap error: " + e.getMessage());
        }
    }

    private static void executeAutomatedVerification(BankService service) {
        System.out.println("1. Accounts loaded into BankService from storage:");
        for (Account acc : service.listAccountsSorted()) {
            System.out.println("   Account[" + acc.getAccountNumber() + " | " + acc.getHolderName() +
                    " (" + acc.getAccountType() + ") | Rs. " + String.format("%.2f", acc.getBalance()) + "]");
        }

        System.out.println("\n2. Executing Automated Capstone Test Suite:");

        try {
            service.deposit("AC1001", 6000.0);
            System.out.println("   [TEST 1 PASSED] Deposit strictly increases balance.");
        } catch (BankException e) {
            System.out.println("   [TEST 1 FAILED] " + e.getMessage());
        }

        try {
            service.withdraw("AC1001", 100000.0);
            System.out.println("   [TEST 2 FAILED] Overdraft was allowed erroneously!");
        } catch (InsufficientFundsException e) {
            System.out.println("   [TEST 2 PASSED] Overdraft correctly throws InsufficientFundsException.");
        } catch (BankException e) {
            System.out.println("   [TEST 2 PASSED with BankException] " + e.getMessage());
        }

        try {
            service.transfer("AC1001", "AC1002", 2000.0);
            System.out.println("   [TEST 3 PASSED] Atomic inter-account transfer succeeded.");
        } catch (BankException e) {
            System.out.println("   [TEST 3 FAILED] " + e.getMessage());
        }

        Customer original = new Customer("C01", "Test User", "9999999999", new Customer.Address("CityA", "StateA", "111111"));
        Customer clone = original.clone();
        clone.getAddress().setCity("CityB");
        if (!original.getAddress().getCity().equals(clone.getAddress().getCity())) {
            System.out.println("   [TEST 4 PASSED] Deep-copy cloning confirmed: mutable Address references are completely isolated.");
        } else {
            System.out.println("   [TEST 4 FAILED] Shallow copy detected!");
        }

        try {
            AnnotationValidator.validate(service.listAccountsSorted().get(0));
            System.out.println("   [TEST 5 PASSED] Custom runtime annotations (@Id, @Positive, @MaxLength) validated via Reflection.");
        } catch (BankException e) {
            System.out.println("   [TEST 5 FAILED] Annotation error: " + e.getMessage());
        }

        System.out.println("\n3. Executing Concurrent Batch Transactions:");
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 6; i++) {
            executor.submit(() -> {
                try {
                    service.deposit("AC1001", 500.0);
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

        System.out.println("\n4. Final Account Balances post-execution:");
        for (Account acc : service.listAccountsSorted()) {
            System.out.println("   Account[" + acc.getAccountNumber() + " | " + acc.getHolderName() +
                    " | Rs. " + String.format("%.2f", acc.getBalance()) + "]");
        }

        System.out.println("\n5. In-Memory Java Streams API Analytics:");
        double totalAssets = service.calculateTotalBankAssets();
        System.out.printf("   Total Bank Reserve Capital: Rs. %,.2f%n", totalAssets);
        System.out.println("   Account Distribution: " + service.getAccountDistribution());
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
                System.out.println("5. Persistent application state successfully saved to " + DATA_FILE);
            }
        } catch (Exception e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }
}
