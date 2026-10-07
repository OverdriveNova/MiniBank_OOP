class PartB {
    public static class Account {
        private final String accountNumber;
        private long balance;

        public Account(String accountNumber, long initialBalance) {
            this.accountNumber = accountNumber;
            this.balance = initialBalance;
        }

        public String getAccountNumber() { return accountNumber; }
        public long getBalance() { return balance; }

        public void unsafeDeposit(long amount) {
            long current = balance;
            try { Thread.sleep(1); } catch (InterruptedException ignored) {}
            balance = current + amount;
        }

        public synchronized void safeDeposit(long amount) {
            balance += amount;
        }
    }

    public static class AccountWorker implements Runnable {
        private final Account account;
        private final int times;
        private final long amount;
        private final boolean useSafe;

        public AccountWorker(Account account, int times, long amount, boolean useSafe) {
            this.account = account;
            this.times = times;
            this.amount = amount;
            this.useSafe = useSafe;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                if (useSafe) {
                    account.safeDeposit(amount);
                } else {
                    account.unsafeDeposit(amount);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 9 - Part B: MiniBank Multithreaded Account Synchronization");
        System.out.println();
        int numThreads = 10;
        int operationsPerThread = 100;
        long depositAmount = 10;
        long expectedTotal = numThreads * operationsPerThread * depositAmount;

        System.out.println("Configuration: " + numThreads + " Concurrent Threads | " + operationsPerThread + " Deposits of Rs. " + depositAmount + " each");
        System.out.println("Expected Final Balance: Rs. " + expectedTotal);

        System.out.println("\nPhase 1: Unsynchronized Concurrent Deposits (Race Condition):");
        Account unsafeAcc = new Account("AC1001", 0);
        Thread[] threads1 = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads1[i] = new Thread(new AccountWorker(unsafeAcc, operationsPerThread, depositAmount, false), "WorkerThread-" + (i + 1));
            threads1[i].start();
        }
        for (Thread t : threads1) t.join();
        System.out.println("Unsafe Final Balance: Rs. " + unsafeAcc.getBalance() + " (Lost Money: Rs. " + (expectedTotal - unsafeAcc.getBalance()) + ")");

        System.out.println("\nPhase 2: Synchronized Method Deposits (Thread-Safe Execution):");
        Account safeAcc = new Account("AC1002", 0);
        Thread[] threads2 = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads2[i] = new Thread(new AccountWorker(safeAcc, operationsPerThread, depositAmount, true), "SyncWorkerThread-" + (i + 1));
            System.out.println("  -> Starting Thread: " + threads2[i].getName() + " [State: " + threads2[i].getState() + "]");
            threads2[i].start();
        }
        for (Thread t : threads2) t.join();
        System.out.println("Synchronized Final Balance: Rs. " + safeAcc.getBalance() + " [EXACT MATCH - Thread Safe]");
    }
}
