import java.util.*;

public class EmailAnalyzer {
    public static boolean validateEmail(String email) {
        int atPos = email.indexOf('@');
        int lastAtPos = email.lastIndexOf('@');

        if (atPos == -1 || atPos != lastAtPos) return false;

        int dotPos = email.indexOf('.', atPos);
        if (dotPos == -1) return false;

        if (atPos == 0 || dotPos == atPos + 1 || dotPos == email.length() - 1) return false;
        return true;
    }

    public static Map<String, String> extractComponents(String email) {
        Map<String, String> parts = new HashMap<>();

        int atPos = email.indexOf('@');
        int dotPos = email.lastIndexOf('.');

        String username = email.substring(0, atPos);
        String domain = email.substring(atPos + 1);
        String domainName = email.substring(atPos + 1, dotPos);
        String extension = email.substring(dotPos + 1);

        parts.put("username", username);
        parts.put("domain", domain);
        parts.put("domainName", domainName);
        parts.put("extension", extension);

        return parts;
    }

    public static void analyzeStatistics(List<String> emails) {
        int validCount = 0, invalidCount = 0;
        int totalUsernameLength = 0;
        Map<String, Integer> domainCount = new HashMap<>();

        for (String email : emails) {
            if (validateEmail(email)) {
                validCount++;
                Map<String, String> parts = extractComponents(email);
                totalUsernameLength += parts.get("username").length();

                domainCount.put(parts.get("domain"), 
                                domainCount.getOrDefault(parts.get("domain"), 0) + 1);
            } else {
                invalidCount++;
            }
        }

        String mostCommonDomain = "N/A";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : domainCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonDomain = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        double avgUsernameLength = (validCount > 0) 
                ? (double) totalUsernameLength / validCount 
                : 0;

        System.out.println("\nEmail Statistics:");
        System.out.println("----------------------------");
        System.out.println("Total Valid Emails   : " + validCount);
        System.out.println("Total Invalid Emails : " + invalidCount);
        System.out.println("Most Common Domain   : " + mostCommonDomain);
        System.out.printf("Average Username Length: %.2f\n", avgUsernameLength);
    }
    public static void displayResults(List<String> emails) {
        System.out.println("\nDetailed Email Analysis:");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-15s %-20s %-15s %-10s %-10s\n", 
                          "Email", "Username", "Domain", "Domain Name", "Extension", "Valid?");
        System.out.println("------------------------------------------------------------------------------------");

        for (String email : emails) {
            if (validateEmail(email)) {
                Map<String, String> parts = extractComponents(email);
                System.out.printf("%-30s %-15s %-20s %-15s %-10s %-10s\n",
                                  email,
                                  parts.get("username"),
                                  parts.get("domain"),
                                  parts.get("domainName"),
                                  parts.get("extension"),
                                  "Yes");
            } else {
                System.out.printf("%-30s %-15s %-20s %-15s %-10s %-10s\n",
                                  email, "-", "-", "-", "-", "No");
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> emails = new ArrayList<>();

        System.out.println("Enter email addresses (type 'done' to finish):");
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("done")) break;
            emails.add(input);
        }

        displayResults(emails);
        analyzeStatistics(emails);
        sc.close();
    }
}
