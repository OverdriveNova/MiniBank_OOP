import java.util.Objects;

class PartB {
    public static class Customer implements Cloneable {
        private String name, email, mobile;
        private final String customerId;
        private Address address;

        public static class Address {
            private String line, city, pincode;
            public Address(String line, String city, String pincode) {
                this.line = line; this.city = city; this.pincode = pincode;
            }
            @Override public String toString() { return line + ", " + city + " - " + pincode; }
            public String getLine() { return line; }
            public String getCity() { return city; }
            public String getPincode() { return pincode; }
        }

        public Customer(String name, String email, String mobile, Address address) {
            this.name = name; this.email = email; this.mobile = mobile;
            this.address = address;
            this.customerId = "CUST101";
        }

        public String getName() { return name; }
        public Address getAddress() { return address; }
        public void setAddress(Address address) { this.address = address; }

        @Override public Customer clone() {
            try {
                Customer cloned = (Customer) super.clone();
                if (this.address != null) {
                    cloned.address = new Address(this.address.line, this.address.city, this.address.pincode);
                }
                return cloned;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    public static class Account {
        private final String accountNumber;
        private String ownerName;
        private long balance;
        private boolean active;

        public Account(String accountNumber, String ownerName, long balance, boolean active) {
            this.accountNumber = accountNumber;
            this.ownerName = ownerName;
            this.balance = balance;
            this.active = active;
        }

        @Override public String toString() {
            return accountNumber + " | " + ownerName + " | Rs. " + balance + " | " + (active ? "Active" : "Inactive");
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Account account = (Account) o;
            return Objects.equals(accountNumber, account.accountNumber);
        }

        @Override public int hashCode() { return Objects.hash(accountNumber); }

        public String buildStatement() {
            return "AccNo: " + accountNumber + "\nOwner: " + ownerName + "\nBalance: Rs. " + balance;
        }
    }

    public static void main(String[] args) {
        System.out.println("RUDRA MODI || 25DCS058");
        System.out.println();
        System.out.println("Practical 3 - Part B: MiniBank Deep-Copy Cloning & Address Subsystem");
        System.out.println();
        Customer.Address addr1 = new Customer.Address("101 Residency Rd", "Nadiad", "387001");
        Customer cust1 = new Customer("Rudra", "rudra@bank.com", "9876543210", addr1);

        Customer cust2 = cust1.clone();
        cust2.setAddress(new Customer.Address("404 High St", "Anand", "388001"));

        System.out.println("Original Address: " + cust1.getAddress());
        System.out.println("Cloned Address (Deep Copied): " + cust2.getAddress());

        Account a1 = new Account("AC0001", "Rudra", 5000, true);
        Account a2 = new Account("AC0001", "Rudra Duplicate", 12000, false);
        System.out.println("a1.equals(a2) by Account Number? " + a1.equals(a2));

        System.out.println("Type checking via instanceof:");
        Object obj = a1;
        System.out.println("Is Account? " + (obj instanceof Account));
    }
}
