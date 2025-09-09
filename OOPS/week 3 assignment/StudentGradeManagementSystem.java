import java.util.*;

class Subject {
    String subjectCode;
    String subjectName;
    int credits;
    String instructor;

    public Subject(String subjectCode, String subjectName, int credits, String instructor) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return subjectCode + " - " + subjectName + " (" + credits + " credits, Instructor: " + instructor + ")";
    }
}

class Student {
    String studentId;
    String studentName;
    String className;
    String[] subjects;
    double[][] marks;
    double gpa;

    static int totalStudents = 0;
    static String schoolName = "Mystery School";
    static String[] gradingScale = {"A: 90-100", "B: 80-89", "C: 70-79", "D: 60-69", "F: <60"};
    static double passPercentage = 40.0;

    public Student(String studentId, String studentName, String className, String[] subjects) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.className = className;
        this.subjects = subjects;
        this.marks = new double[subjects.length][];
        this.gpa = 0.0;
        totalStudents++;
    }

    public void addMarks(String subject, double[] scores) {
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].equalsIgnoreCase(subject)) {
                marks[i] = scores;
                return;
            }
        }
        System.out.println("Subject not found for student " + studentName);
    }

    public void calculateGPA() {
        double totalPercentage = 0;
        int subjectCount = 0;
        for (int i = 0; i < subjects.length; i++) {
            if (marks[i] != null) {
                double sum = 0;
                for (double m : marks[i]) sum += m;
                double avg = sum / marks[i].length;
                totalPercentage += avg;
                subjectCount++;
            }
        }
        if (subjectCount > 0) {
            double avgPercentage = totalPercentage / subjectCount;
            this.gpa = avgPercentage / 20.0; // scale: 100 → 5.0 GPA
        }
    }

    public void generateReportCard() {
        System.out.println("\n--- Report Card for " + studentName + " (" + studentId + ") ---");
        for (int i = 0; i < subjects.length; i++) {
            if (marks[i] != null) {
                double sum = 0;
                for (double m : marks[i]) sum += m;
                double avg = sum / marks[i].length;
                System.out.println(subjects[i] + ": " + avg + "% (" + getGrade(avg) + ")");
            }
        }
        System.out.println("GPA: " + gpa);
        System.out.println("Promotion Eligibility: " + (checkPromotionEligibility() ? "Yes" : "No"));
    }

    public boolean checkPromotionEligibility() {
        return gpa >= (passPercentage / 20.0);
    }

    private String getGrade(double percentage) {
        if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B";
        else if (percentage >= 70) return "C";
        else if (percentage >= 60) return "D";
        else return "F";
    }

    public static void setGradingScale(String[] newScale) {
        gradingScale = newScale;
    }

    public static double calculateClassAverage(Student[] students) {
        double total = 0;
        int count = 0;
        for (Student s : students) {
            s.calculateGPA();
            total += s.gpa;
            count++;
        }
        return (count > 0) ? total / count : 0;
    }

    public static List<Student> getTopPerformers(Student[] students, int count) {
        Arrays.sort(students, (a, b) -> Double.compare(b.gpa, a.gpa));
        List<Student> top = new ArrayList<>();
        for (int i = 0; i < Math.min(count, students.length); i++) {
            top.add(students[i]);
        }
        return top;
    }

    public static void generateSchoolReport(Student[] students) {
        System.out.println("\n=== " + schoolName + " Report ===");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Average GPA: " + calculateClassAverage(students));
        System.out.println("Top Performers:");
        List<Student> top = getTopPerformers(students, 3);
        for (Student s : top) {
            System.out.println(s.studentName + " - GPA: " + s.gpa);
        }
    }
}

public class StudentGradeManagementSystem {
    public static void main(String[] args) {
        String[] subjects = {"Math", "Science", "English"};

        Student s1 = new Student("S001", "Aswathy", "A1", subjects);
        Student s2 = new Student("S002", "Bhavana", "A1", subjects);
        Student s3 = new Student("S003", "Ciya", "B1", subjects);

        s1.addMarks("Math", new double[]{90, 95, 88});
        s1.addMarks("Science", new double[]{85, 80, 90});
        s1.addMarks("English", new double[]{92, 88, 91});
        s1.calculateGPA();

        s2.addMarks("Math", new double[]{70, 75, 68});
        s2.addMarks("Science", new double[]{60, 65, 62});
        s2.addMarks("English", new double[]{72, 70, 74});
        s2.calculateGPA();

        s3.addMarks("Math", new double[]{95, 98, 92});
        s3.addMarks("Science", new double[]{88, 85, 90});
        s3.addMarks("English", new double[]{91, 93, 89});
        s3.calculateGPA();

        s1.generateReportCard();
        s2.generateReportCard();
        s3.generateReportCard();

        Student[] allStudents = {s1, s2, s3};
        Student.generateSchoolReport(allStudents);
    }
}
