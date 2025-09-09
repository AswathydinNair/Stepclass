import java.util.*;

abstract class Employee {
    String empId;
    String empName;
    String department;
    String designation;
    double baseSalary;
    String joinDate;
    boolean[] attendanceRecord; // 30 days

    static int totalEmployees = 0;
    static String companyName = "Mystery Corp";
    static double totalSalaryExpense = 0.0;
    static int workingDaysPerMonth = 30;

    public Employee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.designation = designation;
        this.baseSalary = baseSalary;
        this.joinDate = joinDate;
        this.attendanceRecord = new boolean[workingDaysPerMonth];
        totalEmployees++;
    }

    public void markAttendance(int day, boolean present) {
        if (day >= 1 && day <= workingDaysPerMonth) {
            attendanceRecord[day - 1] = present;
        }
    }

    public double calculateBonus() {
        int presentDays = 0;
        for (boolean day : attendanceRecord) {
            if (day) presentDays++;
        }
        double attendanceRate = (double) presentDays / workingDaysPerMonth;
        if (attendanceRate >= 0.9) return baseSalary * 0.1;
        if (attendanceRate >= 0.75) return baseSalary * 0.05;
        return 0;
    }

    public void requestLeave(int day) {
        if (day >= 1 && day <= workingDaysPerMonth) {
            attendanceRecord[day - 1] = false;
            System.out.println(empName + " leave approved for day " + day);
        }
    }

    public void generatePaySlip() {
        double salary = calculateSalary();
        double bonus = calculateBonus();
        double total = salary + bonus;
        System.out.println("\n=== Pay Slip ===");
        System.out.println("Company: " + companyName);
        System.out.println("Employee: " + empName + " (" + empId + ")");
        System.out.println("Designation: " + designation);
        System.out.println("Base Salary: " + baseSalary);
        System.out.println("Calculated Salary: " + salary);
        System.out.println("Bonus: " + bonus);
        System.out.println("Total Pay: " + total);
    }

    public abstract double calculateSalary();

    public static double calculateCompanyPayroll(List<Employee> employees) {
        double total = 0;
        for (Employee e : employees) {
            total += e.calculateSalary() + e.calculateBonus();
        }
        totalSalaryExpense = total;
        return total;
    }

    public static void getAttendanceReport(List<Employee> employees) {
        System.out.println("\n=== Attendance Report ===");
        for (Employee e : employees) {
            int presentDays = 0;
            for (boolean b : e.attendanceRecord) if (b) presentDays++;
            System.out.println(e.empName + ": " + presentDays + "/" + workingDaysPerMonth + " days present");
        }
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        super(empId, empName, department, designation, baseSalary, joinDate);
    }

    @Override
    public double calculateSalary() {
        int presentDays = 0;
        for (boolean b : attendanceRecord) if (b) presentDays++;
        return (baseSalary / workingDaysPerMonth) * presentDays;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        super(empId, empName, department, designation, baseSalary, joinDate);
    }

    @Override
    public double calculateSalary() {
        int presentDays = 0;
        for (boolean b : attendanceRecord) if (b) presentDays++;
        return (baseSalary / (workingDaysPerMonth / 2)) * presentDays; // part-time lower base
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String empId, String empName, String department, String designation, double baseSalary, String joinDate) {
        super(empId, empName, department, designation, baseSalary, joinDate);
    }

    @Override
    public double calculateSalary() {
        int presentDays = 0;
        for (boolean b : attendanceRecord) if (b) presentDays++;
        return (baseSalary / workingDaysPerMonth) * presentDays; // no bonus usually
    }

    @Override
    public double calculateBonus() {
        return 0; // contract employees don’t get performance bonus
    }
}

class Department {
    String deptId;
    String deptName;
    Employee manager;
    List<Employee> employees;
    double budget;

    public Department(String deptId, String deptName, Employee manager, double budget) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.manager = manager;
        this.employees = new ArrayList<>();
        this.employees.add(manager);
        this.budget = budget;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public double calculateDepartmentExpense() {
        double total = 0;
        for (Employee e : employees) {
            total += e.calculateSalary() + e.calculateBonus();
        }
        return total;
    }

    public static void getDepartmentWiseExpenses(List<Department> departments) {
        System.out.println("\n=== Department Wise Expenses ===");
        for (Department d : departments) {
            System.out.println(d.deptName + " Expense: " + d.calculateDepartmentExpense());
        }
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        Employee e1 = new FullTimeEmployee("E1", "Aswathy", "IT", "Developer", 60000, "2020-01-10");
        Employee e2 = new PartTimeEmployee("E2", "shristi", "IT", "Support", 30000, "2021-03-15");
        Employee e3 = new ContractEmployee("E3", "Carry", "HR", "Consultant", 40000, "2022-06-01");

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);

        Department d1 = new Department("D1", "IT", e1, 200000);
        d1.addEmployee(e2);
        Department d2 = new Department("D2", "HR", e3, 100000);

        List<Department> departments = new ArrayList<>();
        departments.add(d1);
        departments.add(d2);

        // Mark attendance
        for (int i = 1; i <= 25; i++) {
            e1.markAttendance(i, true);
            if (i % 2 == 0) e2.markAttendance(i, true);
            if (i <= 20) e3.markAttendance(i, true);
        }

        e1.requestLeave(26);

        // Generate payslips
        e1.generatePaySlip();
        e2.generatePaySlip();
        e3.generatePaySlip();

        // Company payroll
        double payroll = Employee.calculateCompanyPayroll(employees);
        System.out.println("\nTotal Company Payroll: " + payroll);

        // Department expenses
        Department.getDepartmentWiseExpenses(departments);

        // Attendance report
        Employee.getAttendanceReport(employees);
    }
}

