import java.util.*;

public class ReplaceFind {
    public static List<Integer> findOccurrences(String text, String find) {
        List <Integer> positions = new ArrayList<>();
        int index = text.indexOf(find);

        while (index != -1) {
            positions.add(index);
            index = text.indexOf(find, index + find.length());
        }
        return positions;
    }
    public static String manualReplace(String text, String find, String replace) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < text.length()) {
            if (i <= text.length() - find.length() && 
                text.substring(i, i + find.length()).equals(find)) {
                result.append(replace);  
                i += find.length();  
            } else {
                result.append(text.charAt(i));  
                i++;
            }
        }
        return result.toString();
    }
    public static boolean compareResults(String manual, String text, String find, String replace) {
        String builtIn = text.replace(find, replace);
        return manual.equals(builtIn);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the main text: ");
        String text = sc.nextLine();

        System.out.print("Enter the substring to find: ");
        String find = sc.nextLine();

        System.out.print("Enter the replacement substring: ");
        String replace = sc.nextLine();

        List<Integer> positions = findOccurrences(text, find);
        System.out.println("Occurrences of substring at positions: " + positions);

        String manualResult = manualReplace(text, find, replace);
        System.out.println("Manual Replace Result: " + manualResult);

        String builtInResult = text.replace(find, replace);
        System.out.println("Built-in Replace Result: " + builtInResult);

        boolean isSame = compareResults(manualResult, text, find, replace);
        System.out.println("Are both results equal? " + isSame);

        sc.close();
    }
}
