import java.util.Scanner;

public class UniqueCharactersFinder {

    public static int findLength(String text) {
        int count = 0;
        try {
            while (true) {
                text.charAt(count);
                count++;
            }
        } catch (Exception e) {
            // end of string
        }
        return count;
    }

    public static char[] findUniqueCharacters(String text) {
        int len = findLength(text);
        char[] uniqueChars = new char[len];
        int uniqueCount = 0;

        for (int i = 0; i < len; i++) {
            char current = text.charAt(i);
            boolean isUnique = true;

            for (int j = 0; j < i; j++) {
                if (text.charAt(j) == current) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                uniqueChars[uniqueCount] = current;
                uniqueCount++;
            }
        }

        char[] finalUnique = new char[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            finalUnique[i] = uniqueChars[i];
        }

        return finalUnique;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        char[] unique = findUniqueCharacters(input);

        System.out.print("Unique characters: ");
        for (char c : unique) {
            System.out.print(c + " ");
        }
    }
}

