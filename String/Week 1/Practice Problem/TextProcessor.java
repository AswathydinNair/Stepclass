import java.util.*;


public class TextProcessor {
   public static String cleanInput(String input) {
       input = input.trim().replaceAll("\\s+", " ");
       if (input.length() == 0) return "";
       return Character.toUpperCase(input.charAt(0)) + input.substring(1);
   }
   public static void analyzeText(String text) {
       String[] words = text.split("\\s+");
       int wordCount = words.length;
       int sentenceCount = text.split("[.!?]").length;
       int charCount = text.replaceAll("\\s+", "").length();


       String longestWord = "";
       for (String word : words) {
           String cleaned = word.replaceAll("[^a-zA-Z]", "");
           if (cleaned.length() > longestWord.length()) {
               longestWord = cleaned;
           }
       }
       Map<Character, Integer> freqMap = new HashMap<>();
       for (char ch : text.toLowerCase().toCharArray()) {
           if (Character.isLetter(ch)) {
               freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
           }
       }


       char mostCommonChar = ' ';
       int maxFreq = 0;
       for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
           if (entry.getValue() > maxFreq) {
               mostCommonChar = entry.getKey();
               maxFreq = entry.getValue();
           }
       }


       System.out.println("\n=== TEXT ANALYSIS ===");
       System.out.println("Word count: " + wordCount);
       System.out.println("Sentence count: " + sentenceCount);
       System.out.println("Character count (excluding spaces): " + charCount);
       System.out.println("Longest word: " + longestWord);
       System.out.println("Most common character: '" + mostCommonChar + "' (" + maxFreq + " times)");
   }
   public static String[] getWordsSorted(String text) {
       String cleaned = text.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
       String[] words = cleaned.split("\\s+");
       Arrays.sort(words);
       return words;
   }


   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);


       System.out.println("=== TEXT PROCESSOR ===");
       System.out.print("Enter a paragraph of text: ");
       String input = scanner.nextLine();


       String cleanedText = cleanInput(input);


       if (cleanedText.isEmpty()) {
           System.out.println("Invalid input. Please enter non-empty text.");
       } else {
           analyzeText(cleanedText);


           String[] sortedWords = getWordsSorted(cleanedText);
           System.out.println("\n=== SORTED WORDS ===");
           for (String word : sortedWords) {
               System.out.println(word);
           }
           System.out.print("\nSearch for a word: ");
           String searchWord = scanner.nextLine().toLowerCase();
           boolean found = Arrays.asList(sortedWords).contains(searchWord);


           if (found) {
               System.out.println("The word \"" + searchWord + "\" was found.");
           } else {
               System.out.println("The word \"" + searchWord + "\" was not found.");
           }
       }


       scanner.close();
   }
}

