import java.util.Scanner; 
 
public class Step1 { 
   public static String checkCharType(char ch) { 
       if (ch >= 'A' && ch <= 'Z') { 
           ch = (char)(ch + 32); // Convert to lowercase using ASCII 
       } 
       if (ch >= 'a' && ch <= 'z') { 
           if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') { 
               return "Vowel"; 
           } else { 
               return "Consonant"; 
           } 
       } 
       return "Not a Letter"; 
   } 
 
   public static String[][] findVowelsConsonants(String str) { 
       String[][] result = new String[str.length()][2]; 
       for (int i = 0; i < str.length(); i++) { 
           char ch = str.charAt(i); 
           result[i][0] = String.valueOf(ch); 
           result[i][1] = checkCharType(ch); 
       } 
       return result; 
   } 
 
   public static void displayTable(String[][] arr) { 
       System.out.printf("%-10s%-15s\n", "Character", "Type"); 
       System.out.println("-------------------------"); 
       for (String[] row : arr) { 
           System.out.printf("%-10s%-15s\n", row[0], row[1]); 
       } 
   } 
 
   public static void main(String[] args) { 
       Scanner sc = new Scanner(System.in); 
       System.out.print("Enter a string: "); 
       String input = sc.nextLine(); 
       String[][] result = findVowelsConsonants(input); 
       displayTable(result); 
       sc.close(); 
   } 
} 
 