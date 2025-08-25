import java.util.*;

public class SimpleTextCompressor {

    // Count character frequency without HashMap
    public static Object[] countFrequencies(String text) {
        char[] chars = new char[text.length()];
        int[] freq = new int[text.length()];
        int uniqueCount = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // check if already counted
            int index = -1;
            for (int j = 0; j < uniqueCount; j++) {
                if (chars[j] == c) {
                    index = j;
                    break;
                }
            }

            if (index == -1) {
                chars[uniqueCount] = c;
                freq[uniqueCount] = 1;
                uniqueCount++;
            } else {
                freq[index]++;
            }
        }

        // trim arrays to unique size
        char[] finalChars = Arrays.copyOf(chars, uniqueCount);
        int[] finalFreq = Arrays.copyOf(freq, uniqueCount);

        return new Object[]{finalChars, finalFreq};
    }

    // Create compression codes (shorter for frequent chars)
    public static String[][] createCodes(char[] chars, int[] freq) {
        int n = chars.length;
        String[][] mapping = new String[n][2];

        // Sort chars by frequency (descending)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (freq[j] > freq[i]) {
                    int tmpF = freq[i]; freq[i] = freq[j]; freq[j] = tmpF;
                    char tmpC = chars[i]; chars[i] = chars[j]; chars[j] = tmpC;
                }
            }
        }

        // Assign codes (simple: frequent chars get 1-digit codes, then 2-digit, etc.)
        String symbols = "0123456789!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int codeIndex = 0;

        for (int i = 0; i < n; i++) {
            mapping[i][0] = String.valueOf(chars[i]); // original char
            mapping[i][1] = String.valueOf(symbols.charAt(codeIndex)); // assigned code
            codeIndex++;
        }

        return mapping;
    }

    // Compress text
    public static String compress(String text, String[][] mapping) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (String[] map : mapping) {
                if (map[0].charAt(0) == c) {
                    sb.append(map[1]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    // Decompress text
    public static String decompress(String compressed, String[][] mapping) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < compressed.length(); i++) {
            String code = String.valueOf(compressed.charAt(i));
            for (String[] map : mapping) {
                if (map[1].equals(code)) {
                    sb.append(map[0]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    // Display analysis
    public static void displayAnalysis(String text, char[] chars, int[] freq, String[][] mapping, String compressed, String decompressed) {
        System.out.println("Character Frequency Table:");
        System.out.printf("%-10s %-10s\n", "Char", "Frequency");
        for (int i = 0; i < chars.length; i++) {
            System.out.printf("%-10s %-10d\n", (chars[i] == ' ' ? "[space]" : String.valueOf(chars[i])), freq[i]);
        }

        System.out.println("\nCompression Mapping:");
        System.out.printf("%-10s %-10s\n", "Char", "Code");
        for (String[] map : mapping) {
            System.out.printf("%-10s %-10s\n", (map[0].equals(" ") ? "[space]" : map[0]), map[1]);
        }

        System.out.println("\nOriginal Text: " + text);
        System.out.println("Compressed Text: " + compressed);
        System.out.println("Decompressed Text: " + decompressed);

        double compressionRatio = ((double) compressed.length() / text.length()) * 100;
        System.out.printf("Compression Efficiency: %.2f%%\n", 100 - compressionRatio);
    }

    // Main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter text to compress:");
        String text = sc.nextLine();

        // Count frequencies
        Object[] result = countFrequencies(text);
        char[] chars = (char[]) result[0];
        int[] freq = (int[]) result[1];

        // Create codes
        String[][] mapping = createCodes(chars, freq);

        // Compress
        String compressed = compress(text, mapping);

        // Decompress
        String decompressed = decompress(compressed, mapping);

        // Display full analysis
        displayAnalysis(text, chars, freq, mapping, compressed, decompressed);
    }
}
