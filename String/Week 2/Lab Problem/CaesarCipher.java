import java.util.Scanner;

public class CaesarCipher {
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch >= 'A' && ch <= 'Z') { 
                char newChar = (char) (((ch - 'A' + shift) % 26 + 26) % 26 + 'A');
                result.append(newChar);
            } else if (ch >= 'a' && ch <= 'z') { 
                char newChar = (char) (((ch - 'a' + shift) % 26 + 26) % 26 + 'a');
                result.append(newChar);
            } else {
                result.append(ch); 
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift); 
    }

    public static void displayAscii(String label, String text) {
        System.out.println("\n" + label + ": " + text);
        System.out.print("ASCII values: ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print((int) text.charAt(i) + " ");
        }
        System.out.println();
    }

    public static boolean validate(String original, String decrypted) {
        return original.equals(decrypted);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text to encrypt: ");
        String text = sc.nextLine();

        System.out.print("Enter shift value: ");
        int shift = sc.nextInt();

        String encrypted = encrypt(text, shift);
        String decrypted = decrypt(encrypted, shift);

        displayAscii("Original Text", text);
        displayAscii("Encrypted Text", encrypted);
        displayAscii("Decrypted Text", decrypted);

        System.out.println("\nDecryption successful? " + validate(text, decrypted));

        sc.close();
    }
}

