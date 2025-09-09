class Employee {
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;

    private static int totalEmployees = 0;
    private static int empCounter = 1;

    public Employee(String empName, String department, double baseSalary) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = baseSalary;
        this.empType = "Full-Time";
        totalEmployees++;
    }

    public Employee(String empName, String department, double hourlyRate, int hoursWorked) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = hourlyRate * hoursWorked;
        this.empType = "Part-Time";
        totalEmployees++;
    }

    public Employee(String empName, String department, double contractAmount, boolean isContract) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = contractAmount;
        this.empType = "Contract";
        totalEmployees++;
    }

    private static String generateEmpId() {
        return String.format("EMP%03d", empCounter++);
    }

    // Method Overloading: calculateSalary
    public double calculateSalary(double bonus) {
        if (empType.equals("Full-Time")) {
            return baseSalary + bonus;
        }
        return baseSalary;
    }

    public double calculateSalary(int hours, double hourlyRate) {
        if (empType.equals("Part-Time")) {
            return hours * hourlyRate;
        }
        return baseSalary;
    }

    public double calculateSalary() {
        return baseSalary;
    }

    // Method Overloading: calculateTax
    public double calculateTax(double salary) {
        double tax = 0;
        if (empType.equals("Full-Time")) {
            tax = salary * 0.20;
        } else if (empType.equals("Part-Time")) {
            tax = salary * 0.10;
        } else if (empType.equals("Contract")) {
            tax = salary * 0.15;
        }
        return tax;
    }

    public void generatePaySlip(double bonus) {
        double salary = calculateSalary(bonus);
        double tax = calculateTax(salary);
        System.out.println("\n--- Pay Slip ---");
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Department: " + department);
        System.out.println("Type: " + empType);
        System.out.println("Gross Salary: " + salary);
        System.out.println("Tax: " + tax);
        System.out.println("Net Salary: " + (salary - tax));
    }

    public void generatePaySlip(int hours, double hourlyRate) {
        double salary = calculateSalary(hours, hourlyRate);
        double tax = calculateTax(salary);
        System.out.println("\n--- Pay Slip ---");
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Department: " + department);
        System.out.println("Type: " + empType);
        System.out.println("Gross Salary: " + salary);
        System.out.println("Tax: " + tax);
        System.out.println("Net Salary: " + (salary - tax));
    }

    public void generatePaySlip() {
        double salary = calculateSalary();
        double tax = calculateTax(salary);
        System.out.println("\n--- Pay Slip ---");
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Department: " + department);
        System.out.println("Type: " + empType);
        System.out.println("Gross Salary: " + salary);
        System.out.println("Tax: " + tax);
        System.out.println("Net Salary: " + (salary - tax));
    }

    public void displayEmployeeInfo() {
        System.out.println("\n--- Employee Info ---");
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Department: " + department);
        System.out.println("Type: " + empType);
        System.out.println("Base Salary: " + baseSalary);
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        Employee e1 = new Employee("Aswathy", "IT", 50000); // Full-time
        Employee e2 = new Employee("Bharti", "HR", 500, 40); // Part-time
        Employee e3 = new Employee("Carry", "Finance", 30000, true); // Contract

        e1.displayEmployeeInfo();
        e2.displayEmployeeInfo();
        e3.displayEmployeeInfo();

        e1.generatePaySlip(5000); // Full-time with bonus
        e2.generatePaySlip(40, 500); // Part-time with hours and rate
        e3.generatePaySlip(); // Contract

        System.out.println("\nTotal Employees: " + Employee.getTotalEmployees());
    }
}
