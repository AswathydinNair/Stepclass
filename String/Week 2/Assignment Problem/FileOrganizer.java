import java.util.*;
import java.text.SimpleDateFormat;

public class FileOrganizer {

    // Struct to hold file info
    static class FileInfo {
        String originalName;
        String nameOnly;
        String extension;
        String category;
        String subCategory;
        String newName;
        boolean validName;
        boolean knownType;

        FileInfo(String originalName) {
            this.originalName = originalName;
        }
    }

    // ===== b) Extract file components =====
    public static FileInfo extractFileComponents(String filename) {
        FileInfo info = new FileInfo(filename);

        int dotPos = filename.lastIndexOf(".");
        if (dotPos == -1 || dotPos == filename.length() - 1) {
            info.extension = "";
            info.nameOnly = filename;
            info.validName = false;
            return info;
        }

        info.nameOnly = filename.substring(0, dotPos);
        info.extension = filename.substring(dotPos).toLowerCase();
        info.validName = validateFileName(info.nameOnly);
        return info;
    }

    // Validate filename using ASCII values
    public static boolean validateFileName(String name) {
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(c >= 'a' && c <= 'z' ||
                  c >= 'A' && c <= 'Z' ||
                  c >= '0' && c <= '9' ||
                  c == '_' || c == '-' || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    // ===== c) Categorize by extension =====
    public static void categorizeFiles(List<FileInfo> files, Map<String, Integer> categoryCount) {
        for (FileInfo f : files) {
            String ext = f.extension;
            if (ext.equals(".txt") || ext.equals(".doc")) {
                f.category = "Documents";
            } else if (ext.equals(".jpg") || ext.equals(".png")) {
                f.category = "Images";
            } else if (ext.equals(".mp3") || ext.equals(".wav")) {
                f.category = "Audio";
            } else if (ext.equals(".mp4") || ext.equals(".mkv")) {
                f.category = "Videos";
            } else if (ext.equals(".java") || ext.equals(".py")) {
                f.category = "Code";
            } else {
                f.category = "Unknown";
                f.knownType = false;
                continue;
            }
            f.knownType = true;
            categoryCount.put(f.category, categoryCount.getOrDefault(f.category, 0) + 1);
        }
    }

    // ===== d) Generate new names =====
    public static void generateNewNames(List<FileInfo> files) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Map<String, Integer> duplicateCounter = new HashMap<>();

        for (FileInfo f : files) {
            if (!f.knownType) {
                f.newName = "[UNKNOWN]_" + f.originalName;
                continue;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(f.category).append("_").append(date);

            // handle duplicates
            String base = sb.toString();
            int count = duplicateCounter.getOrDefault(base, 0) + 1;
            duplicateCounter.put(base, count);

            if (count > 1) {
                sb.append("_").append(count);
            }

            sb.append(f.extension);
            f.newName = sb.toString();
        }
    }

    // ===== e) Simulated content-based analysis =====
    public static void analyzeContent(List<FileInfo> files, Map<String, String> fakeContentMap) {
        for (FileInfo f : files) {
            if (f.extension.equals(".txt")) {
                String content = fakeContentMap.getOrDefault(f.originalName, "").toLowerCase();
                if (content.contains("resume")) {
                    f.subCategory = "Resume";
                } else if (content.contains("report")) {
                    f.subCategory = "Report";
                } else if (content.contains("public static void")) {
                    f.subCategory = "CodeSnippet";
                } else {
                    f.subCategory = "GeneralText";
                }
            }
        }
    }

    // ===== f) Display report =====
    public static void displayReport(List<FileInfo> files, Map<String, Integer> categoryCount) {
        System.out.println("\n=== File Organization Report ===");
        System.out.printf("%-25s %-12s %-20s %-15s\n", "Original Name", "Category", "New Name", "SubCategory");

        for (FileInfo f : files) {
            System.out.printf("%-25s %-12s %-20s %-15s\n",
                    f.originalName, f.category, f.newName, f.subCategory == null ? "-" : f.subCategory);
        }

        System.out.println("\n=== Category Counts ===");
        for (String cat : categoryCount.keySet()) {
            System.out.println(cat + ": " + categoryCount.get(cat));
        }

        System.out.println("\n=== Files Needing Attention ===");
        for (FileInfo f : files) {
            if (!f.validName || !f.knownType) {
                System.out.println(" - " + f.originalName + " (Invalid/Unknown)");
            }
        }
    }

    // ===== g) Batch rename commands =====
    public static void batchRenameCommands(List<FileInfo> files) {
        System.out.println("\n=== Batch Rename Commands ===");
        for (FileInfo f : files) {
            if (!f.newName.equals(f.originalName)) {
                System.out.println("rename \"" + f.originalName + "\" -> \"" + f.newName + "\"");
            }
        }
        System.out.println("Storage organization improvement: HIGH");
    }

    // ===== h) Main =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<FileInfo> files = new ArrayList<>();

        System.out.println("Enter file names (end with empty line):");
        while (true) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;
            files.add(extractFileComponents(line));
        }

        // Fake content map for simulation
        Map<String, String> fakeContent = new HashMap<>();
        fakeContent.put("myresume.txt", "This is my Resume with skills");
        fakeContent.put("projectreport.txt", "Annual Report 2023 data");
        fakeContent.put("helloworld.java", "public static void main");

        Map<String, Integer> categoryCount = new HashMap<>();
        categorizeFiles(files, categoryCount);
        generateNewNames(files);
        analyzeContent(files, fakeContent);
        displayReport(files, categoryCount);
        batchRenameCommands(files);
    }
}

