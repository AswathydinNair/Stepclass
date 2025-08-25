import java.util.*;
public class ASCIIprocessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user to enter a string
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        System.out.println("\n--- Character Analysis ---");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int ascii = (int) ch;

            // 1. Display character and ASCII code
            System.out.println("Character: '" + ch + "' | ASCII: " + ascii);

            // 2. Classify character
            String type = classifyCharacter(ch);
            System.out.println("   Type: " + type);

            // 3. If letter, show upper and lower versions
            if (Character.isLetter(ch)) {
                char upper = Character.toUpperCase(ch);
                char lower = Character.toLowerCase(ch);
                System.out.println("   Uppercase: '" + upper + "' (" + (int) upper + ")");
                System.out.println("   Lowercase: '" + lower + "' (" + (int) lower + ")");

                // 4. Difference between upper and lower ASCII
                System.out.println("   ASCII Difference: " + Math.abs((int) upper - (int) lower));
            }
            System.out.println();
        }

        // ASCII art example
        System.out.println("\n--- ASCII Art ---");
        displayASCIITable(65, 90); // A-Z

        // Caesar cipher
        System.out.print("\nEnter shift value for Caesar Cipher: ");
        int shift = scanner.nextInt();
        scanner.nextLine(); // consume newline
        String encrypted = caesarCipher(input, shift);
        System.out.println("Encrypted (Caesar Cipher): " + encrypted);

        // Convert to ASCII array
        int[] asciiArr = stringToASCII(input);
        System.out.println("\nASCII Array: " + Arrays.toString(asciiArr));

        // Convert back to string
        String restored = asciiToString(asciiArr);
        System.out.println("Restored String from ASCII Array: " + restored);

        scanner.close();
    }

    // Method to classify character type
    public static String classifyCharacter(char ch) {
        if (Character.isUpperCase(ch)) return "Uppercase Letter";
        else if (Character.isLowerCase(ch)) return "Lowercase Letter";
        else if (Character.isDigit(ch)) return "Digit";
        else return "Special Character";
    }

    // Method to convert case using ASCII manipulation
    public static char toggleCase(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char) (ch + 32); // Upper → Lower
        } else if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 32); // Lower → Upper
        }
        return ch; // No change for non-letters
    }

    // Method to implement Caesar cipher
    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                char shifted = (char) ((ch - 'A' + shift) % 26 + 'A');
                result.append(shifted);
            } else if (Character.isLowerCase(ch)) {
                char shifted = (char) ((ch - 'a' + shift) % 26 + 'a');
                result.append(shifted);
            } else {
                result.append(ch); // Leave digits and symbols unchanged
            }
        }
        return result.toString();
    }

    // Method to create ASCII table for a range
    public static void displayASCIITable(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.println(i + " -> " + (char) i);
        }
    }

    // Method to convert string to ASCII array
    public static int[] stringToASCII(String text) {
        int[] asciiValues = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            asciiValues[i] = (int) text.charAt(i);
        }
        return asciiValues;
    }

    // Method to convert ASCII array back to string
    public static String asciiToString(int[] asciiValues) {
        StringBuilder sb = new StringBuilder();
        for (int value : asciiValues) {
            sb.append((char) value);
        }
        return sb.toString();
    }}