import java.util.regex.Pattern;

class PartB {
    public static class Validator {
        private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
        private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
        private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
        private static final Pattern AMOUNT_PATTERN = Pattern.compile("^[1-9][0-9]*$");

        public static boolean isValidMobile(String mobile) { return MOBILE_PATTERN.matcher(mobile).matches(); }
        public static boolean isValidEmail(String email) { return EMAIL_PATTERN.matcher(email).matches(); }
        public static boolean isValidPan(String pan) { return PAN_PATTERN.matcher(pan).matches(); }
        public static boolean isValidIfsc(String ifsc) { return IFSC_PATTERN.matcher(ifsc).matches(); }
        public static boolean isValidAmount(String amount) { return AMOUNT_PATTERN.matcher(amount).matches(); }
    }

    public enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }
    public record Command(TransactionType type, String accountNumber, long amount) {}

    public static class CommandParser {
        public static Command parse(String line) throws IllegalArgumentException {
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length != 3) {
                throw new IllegalArgumentException("Error: Invalid token count.");
            }
            TransactionType type = TransactionType.valueOf(tokens[0].toUpperCase());
            String accountNumber = tokens[1];
            if (!Validator.isValidAmount(tokens[2])) {
                throw new IllegalArgumentException("Error: Invalid amount.");
            }
            long amount = Long.parseLong(tokens[2]);
            return new Command(type, accountNumber, amount);
        }
    }

    public static class Account {
        private String accountNumber, ownerName;
        private long balance;
        public Account(String accountNumber, String ownerName, long balance) {
            this.accountNumber = accountNumber; this.ownerName = ownerName; this.balance = balance;
        }
        public String getAccountNumber() { return accountNumber; }
        public String getOwnerName() { return ownerName; }
        public long getBalance() { return balance; }
    }

    public static class StatementFormatter {
        public static String buildStatement(Account account) {
            StringBuilder sb = new StringBuilder();
            sb.append("==================================================\n");
            sb.append("                 MINIBANK STATEMENT               \n");
            sb.append("==================================================\n");
            sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
            sb.append("Owner Name     : ").append(account.getOwnerName()).append("\n");
            sb.append("Current Balance: Rs. ").append(account.getBalance()).append("\n");
            sb.append("==================================================\n");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 4 - Part B: MiniBank Regex Validation & Masked Formatting");
        System.out.println();
        System.out.println("isValidMobile(\"9876543210\") -> " + Validator.isValidMobile("9876543210"));
        System.out.println("isValidEmail(\"abc@xyz.com\") -> " + Validator.isValidEmail("abc@xyz.com"));

        try {
            Command c = CommandParser.parse("DEPOSIT AC0001 500");
            System.out.println("Parsed Command: " + c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Account acc = new Account("AC0001", "Rudra", 9000);
        System.out.println(StatementFormatter.buildStatement(acc));
    }
}
