import java.util.*;

public class SimpleSpellChecker {

    // Method to split a sentence into words (without using split())
    public static String[] extractWords(String sentence) {
        ArrayList<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (!Character.isLetter(ch)) { // word boundary
                if (i > start) {
                    words.add(sentence.substring(start, i));
                }
                start = i + 1;
            }
        }
        // Add last word
        if (start < sentence.length()) {
            words.add(sentence.substring(start));
        }
        return words.toArray(new String[0]);
    }

    // Method to calculate string distance
    public static int stringDistance(String w1, String w2) {
        int len1 = w1.length();
        int len2 = w2.length();

        // If same length, count character differences
        if (len1 == len2) {
            int diff = 0;
            for (int i = 0; i < len1; i++) {
                if (w1.charAt(i) != w2.charAt(i)) diff++;
            }
            return diff;
        } 
        // If different length, use insertion/deletion approximation
        else {
            return Math.abs(len1 - len2);
        }
    }

    // Method to find the closest matching word from dictionary
    public static String findClosestWord(String word, String[] dictionary) {
        int minDist = Integer.MAX_VALUE;
        String closest = word; // default: same word

        for (String dictWord : dictionary) {
            int dist = stringDistance(word.toLowerCase(), dictWord.toLowerCase());
            if (dist < minDist) {
                minDist = dist;
                closest = dictWord;
            }
        }

        return (minDist <= 2) ? closest : word; // suggest only if distance ≤ 2
    }

    // Method to display results in tabular format
    public static void displayResults(String[] words, String[] dictionary) {
        System.out.printf("%-15s %-15s %-10s %-12s\n", "Word", "Suggestion", "Distance", "Status");
        System.out.println("---------------------------------------------------------------");

        for (String word : words) {
            String suggestion = findClosestWord(word, dictionary);
            int distance = stringDistance(word.toLowerCase(), suggestion.toLowerCase());
            String status = (distance == 0) ? "Correct" : "Misspelled";
            System.out.printf("%-15s %-15s %-10d %-12s\n", word, suggestion, distance, status);
        }
    }

    // Main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Dictionary of correct words
        String[] dictionary = {"hello", "world", "java", "program", "spell", "checker", "simple", "example"};

        System.out.println("Enter a sentence:");
        String sentence = sc.nextLine();

        // Extract words manually
        String[] words = extractWords(sentence);

        // Display spell check results
        displayResults(words, dictionary);
    }
}

