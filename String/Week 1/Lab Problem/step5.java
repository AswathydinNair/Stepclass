import java.util.Scanner; 
 
public class step5 { 
   public static int[][] generateRandomScores(int numStudents) { 
       int[][] scores = new int[numStudents][3]; 
       for (int i = 0; i < numStudents; i++) { 
           for (int j = 0; j < 3; j++) { 
               scores[i][j] = 10 + (int)(Math.random() * 90); // 2-digit random 
//score (10-99)
           } 
       } 
       return scores; 
   }
 
   public static double[][] calculateResults(int[][] scores) { 
       int numStudents = scores.length; 
       double[][] results = new double[numStudents][3]; // total, average, 
//percentage 
       for (int i = 0; i < numStudents; i++) { 
           int total = scores[i][0] + scores[i][1] + scores[i][2]; 
           double average = total / 3.0; 
           double percentage = (total / 300.0) * 100; 
           results[i][0] = total; 
           results[i][1] = Math.round(average * 100.0) / 100.0; 
           results[i][2] = Math.round(percentage * 100.0) / 100.0; 
       } 
       return results; 
   } 
 
   public static String[] calculateGrades(double[][] results) { 
       int numStudents = results.length; 
       String[] grades = new String[numStudents]; 
       for (int i = 0; i < numStudents; i++) { 
           double percentage = results[i][2]; 
           if (percentage >= 90) grades[i] = "A"; 
           else if (percentage >= 80) grades[i] = "B"; 
           else if (percentage >= 70) grades[i] = "C"; 
           else if (percentage >= 60) grades[i] = "D"; 
           else grades[i] = "F"; 
       } 
       return grades; 
   } 

   public static void displayScorecard(int[][] scores, double[][] results, 
String[] grades) { 
       System.out.printf("%-10s%-10s%-10s%-10s%-10s%-12s%-12s%-8s\n", 
"Student", "Physics", "Chemistry", "Maths", "Total", "Average", "Percent", 
"Grade"); 
       for (int i = 0; i < scores.length; i++) { 
           System.out.printf("%-10d%-10d%-10d%-10d%-10.0f%-12.2f%-12.2f%-8s\n", 
               (i + 1), scores[i][0], scores[i][1], scores[i][2], 
               results[i][0], results[i][1], results[i][2], grades[i]); 
       } 
   } 
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in); 
       System.out.print("Enter number of students: "); 
       int n = sc.nextInt();
       int[][] scores = generateRandomScores(n);
       double[][] results = calculateResults(scores); 
       String[] grades = calculateGrades(results);
       displayScorecard(scores, results, grades);
       sc.close();
   }
} 
