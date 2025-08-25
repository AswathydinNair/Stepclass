import java.util.Scanner;

public class CaseConverter {

    // (b) Convert a character to uppercase using ASCII
    public static char toUpper(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char)(ch - 32);
        }
        return ch;
    }

    // (c) Convert a character to lowercase using ASCII
    public static char toLower(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char)(ch + 32);
        }
        return ch;
    }

    // Convert whole string to uppercase
    public static String convertToUpper(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toUpper(text.charAt(i)));
        }
        return result.toString();
    }

    // Convert whole string to lowercase
    public static String convertToLower(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toLower(text.charAt(i)));
        }
        return result.toString();
    }

    // (d) Title Case conversion
    public static String convertToTitleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean newWord = true; // start of new word

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch == ' ') {
                result.append(ch);
                newWord = true;
            } else {
                if (newWord) {
                    result.append(toUpper(ch));
                    newWord = false;
                } else {
                    result.append(toLower(ch));
                }
            }
        }
        return result.toString();
    }

    // (e) Compare manual results with built-in methods
    public static void compareResults(String text) {
        System.out.println("\nComparison with built-in methods:");
        System.out.println("----------------------------------");
        System.out.printf("%-20s %-30s %-30s\n", "Case", "Manual Result", "Built-in Result");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-30s %-30s\n", "Uppercase", convertToUpper(text), text.toUpperCase());
        System.out.printf("%-20s %-30s %-30s\n", "Lowercase", convertToLower(text), text.toLowerCase());
        System.out.printf("%-20s %-30s %-30s\n", "Title Case", convertToTitleCase(text), builtInTitleCase(text));
    }

    // Built-in version of Title Case (for comparison only)
    public static String builtInTitleCase(String text) {
        String[] words = text.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }

    // (f) Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a text: ");
        String text = sc.nextLine();

        compareResults(text);

        sc.close();
    }
}
