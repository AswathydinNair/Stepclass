import java.util.Scanner; 
 
public class step2 { 
   public static int[] findTrimIndices(String str) { 
       int start = 0, end = str.length() - 1; 
       while (start <= end && str.charAt(start) == ' ') { 
           start++; 
       } 
       while (end >= start && str.charAt(end) == ' ') { 
           end--; 
       } 
       return new int[]{start, end}; 
   }
 
   public static String customSubstring(String str, int start, int end) { 
       StringBuilder sb = new StringBuilder(); 
       for (int i = start; i <= end; i++) { 
           sb.append(str.charAt(i)); 
       } 
       return sb.toString(); 
   } 
 
   public static boolean customEquals(String s1, String s2) { 
       if (s1.length() != s2.length()) return false; 
       for (int i = 0; i < s1.length(); i++) { 
           if (s1.charAt(i) != s2.charAt(i)) return false; 
       } 
       return true; 
   } 
 
   public static void main(String[] args) { 
       Scanner sc = new Scanner(System.in); 
       System.out.println("Enter a string with leading and trailing spaces:"); 
       String input = sc.nextLine(); 
 
       int[] indices = findTrimIndices(input); 
       String trimmedCustom = (indices[0] > indices[1]) ? "" : 
customSubstring(input, indices[0], indices[1]); 
       String trimmedBuiltIn = input.trim(); 
 
       boolean areEqual = customEquals(trimmedCustom, trimmedBuiltIn); 
 
       System.out.println("Trimmed using custom method: '" + trimmedCustom + "'"); 
       System.out.println("Trimmed using built-in trim(): '" + trimmedBuiltIn + 
"'"); 
       System.out.println("Are both trimmed strings equal? " + areEqual); 
   } 
}