class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    private static int totalAccounts = 0;
    private static int accountCounter = 1;

    public BankAccount(String accountHolderName, double initialDeposit) {
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    private static String generateAccountNumber() {
        return String.format("ACC%03d", accountCounter++);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + " | New Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + " | New Balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal or insufficient funds.");
        }
    }

    public void checkBalance() {
        System.out.println("Current Balance for " + accountHolderName + " (" + accountNumber + "): " + balance);
    }

    public void displayAccountInfo() {
        System.out.println("\n--- Account Info ---");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }
}

public class BankManagementSystem {
    public static void main(String[] args) {
        BankAccount[] accounts = new BankAccount[5];

        accounts[0] = new BankAccount("Aswathy", 6000);
        accounts[1] = new BankAccount("ashvini", 3900);
        accounts[2] = new BankAccount("Carry", 7800);

        accounts[0].deposit(2000);
        accounts[1].withdraw(500);
        accounts[2].withdraw(8000);

        accounts[0].checkBalance();
        accounts[1].checkBalance();
        accounts[2].checkBalance();

        for (int i = 0; i < BankAccount.getTotalAccounts(); i++) {
            accounts[i].displayAccountInfo();
        }

        System.out.println("\nTotal Accounts Created: " + BankAccount.getTotalAccounts());
    }
}
