import java.util.*;

public class CSVAnalyzer {

    // ===== b) Parse CSV without split() =====
    public static String[][] parseCSV(String input) {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> currentRow = new ArrayList<>();
        StringBuilder field = new StringBuilder();

        boolean inQuotes = false;
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (ch == '"') {
                inQuotes = !inQuotes; // toggle quoted field
            } else if (ch == ',' && !inQuotes) {
                currentRow.add(field.toString());
                field.setLength(0);
            } else if ((ch == '\n' || ch == '\r') && !inQuotes) {
                if (field.length() > 0 || !currentRow.isEmpty()) {
                    currentRow.add(field.toString());
                    rows.add(new ArrayList<>(currentRow));
                }
                currentRow.clear();
                field.setLength(0);
            } else {
                field.append(ch);
            }
        }

        // add last row if exists
        if (field.length() > 0 || !currentRow.isEmpty()) {
            currentRow.add(field.toString());
            rows.add(currentRow);
        }

        // Convert to 2D array
        String[][] data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i).toArray(new String[0]);
        }
        return data;
    }

    // ===== c) Validate and clean =====
    public static String[][] cleanData(String[][] data, List<String> qualityIssues) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != null) {
                    data[i][j] = data[i][j].trim();
                }

                if (data[i][j].isEmpty()) {
                    qualityIssues.add("Row " + (i + 1) + ", Col " + (j + 1) + " → Missing value");
                }

                // check numeric fields (try parse)
                if (i > 0) { // skip header row
                    String val = data[i][j];
                    boolean isNumeric = true;
                    for (int k = 0; k < val.length(); k++) {
                        char c = val.charAt(k);
                        if (!(c >= '0' && c <= '9' || c == '.' || c == '-')) {
                            isNumeric = false;
                            break;
                        }
                    }
                    if (!isNumeric && !val.isEmpty()) {
                        qualityIssues.add("Row " + (i + 1) + ", Col " + (j + 1) + " → Invalid numeric: " + val);
                    }
                }
            }
        }
        return data;
    }

    // ===== d) Data Analysis =====
    public static void analyzeData(String[][] data, StringBuilder report, List<String> qualityIssues) {
        int rows = data.length;
        int cols = data[0].length;

        report.append("\n=== Column Statistics ===\n");

        for (int j = 0; j < cols; j++) {
            boolean numeric = true;
            ArrayList<Double> values = new ArrayList<>();
            HashSet<String> unique = new HashSet<>();

            for (int i = 1; i < rows; i++) { // skip header
                String val = data[i][j];
                if (val.isEmpty()) continue;
                try {
                    double num = Double.parseDouble(val);
                    values.add(num);
                } catch (NumberFormatException e) {
                    numeric = false;
                    unique.add(val);
                }
            }

            report.append("Column ").append(j + 1).append(" (").append(data[0][j]).append("):\n");
            if (numeric && !values.isEmpty()) {
                double min = Collections.min(values);
                double max = Collections.max(values);
                double sum = 0;
                for (double v : values) sum += v;
                double avg = sum / values.size();

                report.append(String.format("   Min=%.2f, Max=%.2f, Avg=%.2f\n", min, max, avg));
            } else {
                report.append("   Unique values: ").append(unique.size()).append(" → ").append(unique).append("\n");
            }
        }
    }

    // ===== e) Format output table =====
    public static String formatTable(String[][] data, List<String> qualityIssues) {
        int rows = data.length, cols = data[0].length;
        int[] colWidths = new int[cols];

        // find max width per column
        for (int j = 0; j < cols; j++) {
            int maxLen = 0;
            for (int i = 0; i < rows; i++) {
                if (data[i][j] != null) {
                    maxLen = Math.max(maxLen, data[i][j].length());
                }
            }
            colWidths[j] = maxLen + 2;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Data Table ===\n");

        for (int i = 0; i < rows; i++) {
            sb.append("|");
            for (int j = 0; j < cols; j++) {
                String cell = data[i][j];
                sb.append(String.format(" %-" + colWidths[j] + "s |", cell));
            }
            sb.append("\n");
            if (i == 0) { // header underline
                sb.append("|");
                for (int w : colWidths) {
                    sb.append("-".repeat(w + 2)).append("|");
                }
                sb.append("\n");
            }
        }

        if (!qualityIssues.isEmpty()) {
            sb.append("\n=== Data Quality Issues ===\n");
            for (String issue : qualityIssues) {
                sb.append(" - ").append(issue).append("\n");
            }
        }

        return sb.toString();
    }

    // ===== f) Summary Report =====
    public static void summaryReport(String[][] data, List<String> qualityIssues, StringBuilder report) {
        int totalRecords = data.length - 1;
        int totalIssues = qualityIssues.size();
        int totalFields = (data.length - 1) * data[0].length;
        int validFields = totalFields - totalIssues;
        double completeness = (validFields * 100.0) / totalFields;

        report.append("\n=== Summary Report ===\n");
        report.append("Total records: ").append(totalRecords).append("\n");
        report.append("Total fields: ").append(totalFields).append("\n");
        report.append("Data quality issues: ").append(totalIssues).append("\n");
        report.append(String.format("Completeness: %.2f%%\n", completeness));
    }

    // ===== g) Main =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter CSV-like data (end input with an empty line):");

        StringBuilder input = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) break;
            input.append(line).append("\n");
        }

        // Parse
        String[][] data = parseCSV(input.toString());

        // Clean
        List<String> qualityIssues = new ArrayList<>();
        data = cleanData(data, qualityIssues);

        // Analyze
        StringBuilder report = new StringBuilder();
        analyzeData(data, report, qualityIssues);

        // Format table
        String table = formatTable(data, qualityIssues);

        // Summary
        summaryReport(data, qualityIssues, report);

        // Output all
        System.out.println(table);
        System.out.println(report);
    }
}
