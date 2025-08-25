import java.util.Scanner; 
 
public class step3 { 
   public static int[] generateRandomAges(int n) { 
       int[] ages = new int[n]; 
      for (int i = 0; i < n; i++) { 
           ages[i] = 10 + (int)(Math.random() * 90); // 2-digit age (10-99) 
       } 
       return ages; 
   } 
 
   public static String[][] checkVotingEligibility(int[] ages) { 
       String[][] result = new String[ages.length][2]; 
       for (int i = 0; i < ages.length; i++) { 
           result[i][0] = String.valueOf(ages[i]); 
           if (ages[i] < 0) { 
               result[i][1] = "false"; 
           } else if (ages[i] >= 18) { 
               result[i][1] = "true"; 
           } else { 
               result[i][1] = "false"; 
           } 
       } 
       return result; 
   } 
 
   public static void displayTable(String[][] data) { 
       System.out.println("Age\tCan Vote"); 
       for (String[] row : data) { 
           System.out.println(row[0] + "\t" + row[1]); 
       } 
   } 
 
   public static void main(String[] args) { 
       Scanner sc = new Scanner(System.in); 
       int n = 10; 
       int[] ages = new int[n]; 
       System.out.println("Enter the age of " + n + " students:"); 
       for (int i = 0; i < n; i++) { 
           ages[i] = sc.nextInt(); 
       } 
       String[][] result = checkVotingEligibility(ages); 
       displayTable(result); 
       sc.close(); 
   } 
}
