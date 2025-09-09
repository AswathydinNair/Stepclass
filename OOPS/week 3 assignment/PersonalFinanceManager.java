import java.util.*;

class PersonalAccount {
    private String accountHolderName;
    private String accountNumber;
    private double currentBalance;
    private double totalIncome;
    private double totalExpenses;
    private static int totalAccounts = 0;
    private static String bankName = "Default Bank";

    public PersonalAccount(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = generateAccountNumber();
        this.currentBalance = 0;
        this.totalIncome = 0;
        this.totalExpenses = 0;
        totalAccounts++;
    }

    public void addIncome(double amount, String description) {
        if (amount <= 0) return;
        currentBalance += amount;
        totalIncome += amount;
        System.out.println(accountHolderName + " | " + description + " | +" + amount);
    }

    public void addExpense(double amount, String description) {
        if (amount <= 0) return;
        if (amount > currentBalance) {
            System.out.println(accountHolderName + " | Insufficient balance for " + description);
            return;
        }
        currentBalance -= amount;
        totalExpenses += amount;
        System.out.println(accountHolderName + " | " + description + " | -" + amount);
    }

    public double calculateSavings() {
        return totalIncome - totalExpenses;
    }

    public void displayAccountSummary() {
        System.out.println("Bank: " + bankName);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: " + currentBalance);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpenses);
        System.out.println("Savings: " + calculateSavings());
        System.out.println("----------------------------");
    }

    public static void setBankName(String name) {
        bankName = name;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static String generateAccountNumber() {
        return String.format("AC%04d", totalAccounts + 1);
    }

    public static String getBankName() {
        return bankName;
    }
}

public class PersonalFinanceManager {
    public static void main(String[] args) {
        PersonalAccount.setBankName("FinWise Bank");
        PersonalAccount a1 = new PersonalAccount("Aswathy");
        PersonalAccount a2 = new PersonalAccount("Rahul");
        PersonalAccount a3 = new PersonalAccount("seema");

        a1.addIncome(25000, "Salary");
        a1.addExpense(5000, "Rent");
        a1.addExpense(2000, "Groceries");
        a1.addIncome(3000, "Freelance");

        a2.addIncome(18000, "Salary");
        a2.addExpense(4000, "Rent");
        a2.addExpense(1500, "Transport");

        a3.addIncome(30000, "Salary");
        a3.addExpense(12000, "Laptop");
        a3.addIncome(2000, "Cashback");
        a3.addExpense(2500, "Dining");

        a1.displayAccountSummary();
        a2.displayAccountSummary();
        a3.displayAccountSummary();

        System.out.println("Bank Name (static shared): " + PersonalAccount.getBankName());
        System.out.println("Total Accounts (static): " + PersonalAccount.getTotalAccounts());
    }
}

