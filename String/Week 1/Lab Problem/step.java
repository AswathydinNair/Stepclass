public class step { 
   public static String checkCharType(char ch) { 
       // Convert to lowercase using ASCII if uppercase 
       if (ch >= 'A' && ch <= 'Z') { 
           ch = (char) (ch + 32); 
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
 
   public static int[] countVowelsAndConsonants(String str) { 
       int vowels = 0, consonants = 0; 
       for (int i = 0; i < str.length(); i++) { 
           String type = checkCharType(str.charAt(i)); 
           if (type.equals("Vowel")) { 
               vowels++; 
           } else if (type.equals("Consonant")) { 
               consonants++; 
           } 
       } 
       return new int[]{vowels, consonants}; 
   } 
 
   public static void main(String[] args) { 
       java.util.Scanner sc = new java.util.Scanner(System.in); 
       System.out.print("Enter a string: "); 
       String input = sc.nextLine(); 
       int[] result = countVowelsAndConsonants(input); 
       System.out.println("Vowels: " + result[0]); 
       System.out.println("Consonants: " + result[1]); 
       sc.close(); 
   } 
}
