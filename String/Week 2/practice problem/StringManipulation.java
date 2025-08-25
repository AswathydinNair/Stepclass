import java.util.*;

public class StringManipulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for input
        System.out.print("Enter a sentence with mixed formatting: ");
        String input = scanner.nextLine();

        // 1. trim() - Remove extra spaces
        String trimmed = input.trim();
        System.out.println("\n1. Trimmed: " + trimmed);

        // 2. replace() - Replace all spaces with underscores
        String replacedSpaces = trimmed.replace(" ", "_");
        System.out.println("2. Spaces replaced: " + replacedSpaces);

        // 3. replaceAll() - Remove all digits using regex
        String noDigits = trimmed.replaceAll("\\d", "");
        System.out.println("3. Without digits: " + noDigits);

        // 4. split() - Split sentence into words array
        String[] words = trimmed.split("\\s+");
        System.out.println("4. Words array: " + Arrays.toString(words));

        // 5. join() - Rejoin words with " | " separator
        String joined = String.join(" | ", words);
        System.out.println("5. Joined with | : " + joined);

        // Additional processing
        System.out.println("\n--- Additional Processing ---");

        // Remove punctuation
        String noPunctuation = removePunctuation(trimmed);
        System.out.println("6. Without punctuation: " + noPunctuation);

        // Capitalize words
        String capitalized = capitalizeWords(noPunctuation);
        System.out.println("7. Capitalized: " + capitalized);

        // Reverse word order
        String reversed = reverseWordOrder(noPunctuation);
        System.out.println("8. Reversed word order: " + reversed);

        // Word frequency
        System.out.println("9. Word frequency:");
        countWordFrequency(noPunctuation);

        scanner.close();
    }

    // Method to remove punctuation
    public static String removePunctuation(String text) {
        return text.replaceAll("\\p{Punct}", ""); // removes all punctuation
    }

    // Method to capitalize each word
    public static String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Method to reverse word order
    public static String reverseWordOrder(String text) {
        String[] words = text.split("\\s+");
        Collections.reverse(Arrays.asList(words));
        return String.join(" ", words);
    }

    // Method to count word frequency
    public static void countWordFrequency(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        Map<String, Integer> freqMap = new LinkedHashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            System.out.println("   " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
