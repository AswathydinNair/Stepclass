import java.util.Random;
class BankAccount {
    String accountHolder;
    int accountNumber;
    double balance;

    BankAccount() {
        this.accountHolder = "Unknown";
        this.accountNumber = new Random().nextInt(900000) + 100000; 
        this.balance = 0.0;
    }

    BankAccount(String accountHolder) {
        this.accountHolder = accountHolder;
        this.accountNumber = new Random().nextInt(900000) + 100000;
        this.balance = 0.0;
    }

    BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.accountNumber = new Random().nextInt(900000) + 100000;
        this.balance = initialBalance;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited ₹" + amount + " successfully.");
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("withdrawn ₹" + amount + " successfully.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            System.out.println(" Withdrawal amount must be positive.");
        }
    }

    // Display account details
    void displayAccount() {
        System.out.println("Account Details ");
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Balance        : ₹" + balance);
    }
}

public class BankAccountSystem {
    public static void main(String[] args) {
        BankAccount acc1 = new BankAccount();  
        BankAccount acc2 = new BankAccount("Aswathy");  
        BankAccount acc3 = new BankAccount("Bhavana", 5000.0);  

        acc1.displayAccount();
        acc1.deposit(2050);
        acc1.withdraw(550);
        acc1.displayAccount();

        acc2.displayAccount();
        acc2.deposit(1500);
        acc2.withdraw(1200); 
        acc2.displayAccount();

        acc3.displayAccount();
        acc3.withdraw(3000);
        acc3.deposit(1500);
        acc3.displayAccount();
    }
}
