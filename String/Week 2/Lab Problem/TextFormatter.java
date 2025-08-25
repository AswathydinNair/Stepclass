import java.util.*;

public class TextFormatter {

    public static List<String> getWords(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (start < i) {
                    words.add(text.substring(start, i));
                }
                start = i + 1;
            }
        }
        if (start < text.length()) {
            words.add(text.substring(start));
        }
        return words;
    }

    public static List<String> justifyText(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        int index = 0;

        while (index < words.size()) {
            int lineLength = words.get(index).length();
            int last = index + 1;

            while (last < words.size()) {
                if (lineLength + 1 + words.get(last).length() > width) break;
                lineLength += 1 + words.get(last).length();
                last++;
            }

            StringBuilder sb = new StringBuilder();
            int gaps = last - index - 1;

            if (last == words.size() || gaps == 0) {
                for (int i = index; i < last; i++) {
                    sb.append(words.get(i));
                    if (i < last - 1) sb.append(" ");
                }
                while (sb.length() < width) {
                    sb.append(" ");
                }
            } else {
                int spaces = (width - lineLength) / gaps;
                int extra = (width - lineLength) % gaps;

                for (int i = index; i < last - 1; i++) {
                    sb.append(words.get(i));
                    sb.append(" ");
                    for (int j = 0; j < spaces; j++) sb.append(" ");
                    if (extra > 0) {
                        sb.append(" ");
                        extra--;
                    }
                }
                sb.append(words.get(last - 1));
            }

            lines.add(sb.toString());
            index = last;
        }
        return lines;
    }

    public static List<String> centerAlign(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        int index = 0;

        while (index < words.size()) {
            int lineLength = words.get(index).length();
            int last = index + 1;

            while (last < words.size()) {
                if (lineLength + 1 + words.get(last).length() > width) break;
                lineLength += 1 + words.get(last).length();
                last++;
            }

            StringBuilder sb = new StringBuilder();
            StringBuilder temp = new StringBuilder();

            for (int i = index; i < last; i++) {
                temp.append(words.get(i));
                if (i < last - 1) temp.append(" ");
            }

            int padding = (width - temp.length()) / 2;
            for (int i = 0; i < padding; i++) sb.append(" ");
            sb.append(temp);

            while (sb.length() < width) sb.append(" ");

            lines.add(sb.toString());
            index = last;
        }

        return lines;
    }

    public static void comparePerformance(String text, int width) {
        List<String> words = getWords(text);

        long start1 = System.nanoTime();
        justifyText(words, width);
        long end1 = System.nanoTime();
        long timeBuilder = end1 - start1;

        long start2 = System.nanoTime();
        justifyWithConcat(words, width);
        long end2 = System.nanoTime();
        long timeConcat = end2 - start2;

        System.out.println("\nPerformance Analysis:");
        System.out.println("----------------------------------");
        System.out.printf("%-20s %-15s\n", "Method", "Time (ns)");
        System.out.println("----------------------------------");
        System.out.printf("%-20s %-15d\n", "StringBuilder", timeBuilder);
        System.out.printf("%-20s %-15d\n", "String Concatenation", timeConcat);
    }

    public static List<String> justifyWithConcat(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        int index = 0;

        while (index < words.size()) {
            int lineLength = words.get(index).length();
            int last = index + 1;

            while (last < words.size()) {
                if (lineLength + 1 + words.get(last).length() > width) break;
                lineLength += 1 + words.get(last).length();
                last++;
            }

            String line = "";
            for (int i = index; i < last; i++) {
                line += words.get(i);
                if (i < last - 1) line += " ";
            }
            while (line.length() < width) {
                line += " ";
            }
            lines.add(line);
            index = last;
        }
        return lines;
    }

    public static void displayFormatted(String label, List<String> lines) {
        System.out.println("\n" + label + ":");
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            System.out.printf("Line %2d (%2d chars): %s\n", i + 1, line.length(), line);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text to format: ");
        String text = sc.nextLine();

        System.out.print("Enter desired line width: ");
        int width = sc.nextInt();

        List<String> words = getWords(text);

        List<String> justified = justifyText(words, width);
        List<String> centered = centerAlign(words, width);

        System.out.println("\nOriginal Text:\n" + text);

        displayFormatted("Justified Text", justified);
        displayFormatted("Center-aligned Text", centered);

        comparePerformance(text, width);

        sc.close();
    }
}

