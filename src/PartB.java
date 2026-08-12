import java.util.Scanner;

class PartB {
    public record BankInfo(String name, String branch) {}

    public enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, WORKING_HOURS, EXIT }

    public static void main(String[] args) {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 1 - Part B: MiniBank Console Menu Shell");
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank Central", "Main Branch (DEPSTAR)");
        System.out.println("BANK: " + bank.name() + " | BRANCH: " + bank.branch());

        boolean running = true;

        while (running) {
            System.out.println("\n1. Open Account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Working Hours\n6. Exit");
            System.out.print("Enter choice (1-6): ");

            int choice = 0;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Please enter a valid number!");
                scanner.next();
                continue;
            }

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.WORKING_HOURS;
                case 6 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid menu option!");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT -> System.out.println("Open Account - to be implemented in a later lab.");
                case DEPOSIT -> System.out.println("Deposit - to be implemented in a later lab.");
                case WITHDRAW -> System.out.println("Withdraw - to be implemented in a later lab.");
                case TRANSFER -> System.out.println("Transfer - to be implemented in a later lab.");
                case WORKING_HOURS -> System.out.println("Working hours: 09:00 AM to 04:00 PM, Mon-Fri.");
                case EXIT -> {
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    running = false;
                }
            }
        }

        scanner.close();
    }
}
