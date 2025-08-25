import java.util.*;

public class PasswordStrengthAnalyzer {

    // Method to analyze password components using ASCII
    public static int[] analyzePassword(String password) {
        int upper = 0, lower = 0, digit = 0, special = 0;

        for (int i = 0; i < password.length(); i++) {
            int ascii = (int) password.charAt(i);

            if (ascii >= 65 && ascii <= 90) upper++; // Uppercase
            else if (ascii >= 97 && ascii <= 122) lower++; // Lowercase
            else if (ascii >= 48 && ascii <= 57) digit++; // Digit
            else if (ascii >= 33 && ascii <= 126) special++; // Printable ASCII special chars
        }

        return new int[]{upper, lower, digit, special};
    }

    // Method to calculate strength score
    public static int calculateScore(String password, int[] counts) {
        int score = 0;

        // Length points
        if (password.length() > 8) {
            score += (password.length() - 8) * 2;
        }

        // Variety points
        if (counts[0] > 0) score += 10; // uppercase
        if (counts[1] > 0) score += 10; // lowercase
        if (counts[2] > 0) score += 10; // digit
        if (counts[3] > 0) score += 10; // special

        // Deduct points for common patterns
        String lowerPass = password.toLowerCase();
        if (lowerPass.contains("123") || lowerPass.contains("abc") || lowerPass.contains("qwerty")) {
            score -= 10;
        }

        return score;
    }

    // Method to determine strength level
    public static String getStrengthLevel(int score) {
        if (score <= 20) return "Weak";
        else if (score <= 50) return "Medium";
        else return "Strong";
    }

    // Method to generate a strong password
    public static String generatePassword(int length) {
        if (length < 4) length = 8; // Minimum length safeguard

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specials = "!@#$%^&*()-_=+[]{};:,.<>?/";

        Random rand = new Random();
        StringBuilder password = new StringBuilder();

        // Ensure at least one from each category
        password.append(upper.charAt(rand.nextInt(upper.length())));
        password.append(lower.charAt(rand.nextInt(lower.length())));
        password.append(digits.charAt(rand.nextInt(digits.length())));
        password.append(specials.charAt(rand.nextInt(specials.length())));

        String allChars = upper + lower + digits + specials;

        // Fill remaining characters
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(rand.nextInt(allChars.length())));
        }

        // Shuffle characters
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < password.length(); i++) {
            charList.add(password.charAt(i));
        }
        Collections.shuffle(charList);

        StringBuilder shuffled = new StringBuilder();
        for (char c : charList) {
            shuffled.append(c);
        }

        return shuffled.toString();
    }

    // Method to display analysis in tabular format
    public static void displayResults(String[] passwords) {
        System.out.printf("%-15s %-8s %-8s %-8s %-8s %-12s %-8s %-10s\n",
                "Password", "Length", "Upper", "Lower", "Digits", "SpecialChars", "Score", "Strength");
        System.out.println("--------------------------------------------------------------------------------");

        for (String password : passwords) {
            int[] counts = analyzePassword(password);
            int score = calculateScore(password, counts);
            String strength = getStrengthLevel(score);

            System.out.printf("%-15s %-8d %-8d %-8d %-8d %-12d %-8d %-10s\n",
                    password, password.length(), counts[0], counts[1], counts[2], counts[3], score, strength);
        }
    }

    // Main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input multiple passwords
        System.out.println("Enter number of passwords to analyze: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        String[] passwords = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter password " + (i + 1) + ":");
            passwords[i] = sc.nextLine();
        }

        // Display results
        displayResults(passwords);

        // Generate new strong password
        System.out.println("\nEnter desired length for generated strong password: ");
        int length = sc.nextInt();
        String generated = generatePassword(length);
        System.out.println("Generated Strong Password: " + generated);
    }
}
