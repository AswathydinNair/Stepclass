public class StringManipulation {
   public static void main(String[] args) {
       String str1 = "Java Programming";
       String str2 = new String("Java Programming");
       char[] charArray = {'J', 'a', 'v', 'a', ' ', 'P', 'r', 'o', 'g', 'r', 'a', 'm', 'm', 'i', 'n', 'g'};
       String str3 = new String(charArray);


       System.out.println("Comparing using == :");
       System.out.println("str1 == str2: " + (str1 == str2));
       System.out.println("str1 == str3: " + (str1 == str3));


       System.out.println("\nComparing using .equals():");
       System.out.println("str1.equals(str2): " + str1.equals(str2));
       System.out.println("str1.equals(str3): " + str1.equals(str3));


       System.out.println("\nExplanation:");
       System.out.println("== compares references (memory locations), not content.");
       System.out.println(".equals() compares the actual content of the strings.");


       String quote = "Programming Quote:\n\"Code is poetry\" - Unknown\nPath: C:\\Java\\Projects";
       System.out.println("\n" + quote);
   }
}


