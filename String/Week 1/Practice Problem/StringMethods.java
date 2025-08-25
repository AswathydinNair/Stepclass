import java.util.Scanner;
public class StringMethods {


   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);


       System.out.print("Enter your full name (first and last): ");
       String fullName = scanner.nextLine();


       System.out.print("Enter your favorite programming language: ");
       String language = scanner.nextLine();


       System.out.print("Write a sentence about your programming experience: ");
       String experience = scanner.nextLine();
       String[] nameParts = fullName.trim().split("\\s+");
       String firstName = nameParts.length > 0 ? nameParts[0] : "";
       String lastName = nameParts.length > 1 ? nameParts[1] : "";


       int charCount = experience.replace(" ", "").length();
       String upperLanguage = language.toUpperCase();
       System.out.println("\n--- Summary ---");
       System.out.println("First Name: " + firstName);
       System.out.println("Last Name: " + lastName);
       System.out.println("Favorite Language (UPPERCASE): " + upperLanguage);
       System.out.println("Characters in your sentence (excluding spaces): " + charCount);


       scanner.close();
   }
}
