import java.util.*;

public class TextExpressionCalculator {

    // ===== Validation helpers (ASCII based) =====
    private static boolean isDigitASCII(char ch) { return ch >= 48 && ch <= 57; }       // '0'..'9'
    private static boolean isOperatorASCII(char ch) { return ch == '+' || ch == '-' || ch == '*' || ch == '/'; }
    private static boolean isParen(char ch) { return ch == '(' || ch == ')'; }
    private static boolean isSpace(char ch) { return ch == ' '; }

    // Find previous non-space char index before i
    private static int prevNonSpace(String s, int i) {
        for (int k = i - 1; k >= 0; k--) if (!isSpace(s.charAt(k))) return k;
        return -1;
    }
    // Find next non-space char index after i
    private static int nextNonSpace(String s, int i) {
        for (int k = i + 1; k < s.length(); k++) if (!isSpace(s.charAt(k))) return k;
        return -1;
    }

    // a) + b) Validate expression format
    public static boolean validateExpression(String expr) {
        if (expr == null || expr.trim().isEmpty()) return false;

        int balance = 0; // parentheses balance
        boolean hasDigit = false;

        int firstIdx = -1, lastIdx = -1;
        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            // i. Valid characters
            if (!(isDigitASCII(ch) || isOperatorASCII(ch) || isParen(ch) || isSpace(ch))) {
                return false;
            }

            if (isDigitASCII(ch)) hasDigit = true;

            // track first/last non-space
            if (!isSpace(ch)) {
                if (firstIdx == -1) firstIdx = i;
                lastIdx = i;
            }

            // ii. Validate operator placement & parentheses matching
            if (ch == '(') {
                balance++;
                int next = nextNonSpace(expr, i);
                if (next == -1) return false;                    // nothing after '('
                char nn = expr.charAt(next);
                if (nn == ')' || isOperatorASCII(nn)) return false; // "()", "(+"
            } else if (ch == ')') {
                balance--;
                if (balance < 0) return false; // closing before opening
                int prev = prevNonSpace(expr, i);
                if (prev == -1) return false;
                char pp = expr.charAt(prev);
                if (isOperatorASCII(pp) || pp == '(') return false; // "+)", "()"
            } else if (isOperatorASCII(ch)) {
                int prev = prevNonSpace(expr, i);
                int next = nextNonSpace(expr, i);
                if (prev == -1 || next == -1) return false; // operator at ends
                char pch = expr.charAt(prev), nch = expr.charAt(next);
                if (isOperatorASCII(pch) || pch == '(') return false; // "++", "(+"
                if (isOperatorASCII(nch) || nch == ')') return false; // "+*", "+)"
            }
        }

        if (!hasDigit) return false;
        if (firstIdx != -1 && isOperatorASCII(expr.charAt(firstIdx))) return false;
        if (lastIdx != -1 && isOperatorASCII(expr.charAt(lastIdx))) return false;
        return balance == 0;
    }

    // c) Parse numbers/operators from a paren-free expression
    private static class Parsed {
        ArrayList<Integer> nums = new ArrayList<>();
        ArrayList<Character> ops = new ArrayList<>();
    }

    private static Parsed parseFlat(String part) {
        Parsed p = new Parsed();
        // Remove spaces for easier scanning
        String s = part.replace(" ", "");
        int i = 0, n = s.length();

        while (i < n) {
            if (i < n && isDigitASCII(s.charAt(i))) {
                int start = i;
                while (i < n && isDigitASCII(s.charAt(i))) i++;
                int val = Integer.parseInt(s.substring(start, i));
                p.nums.add(val);
            } else if (i < n && isOperatorASCII(s.charAt(i))) {
                p.ops.add(s.charAt(i));
                i++;
            } else {
                // if there are parentheses here, the caller used this on a non-flat part
                throw new IllegalArgumentException("Unexpected character in flat parse: '" + s.charAt(i) + "'");
            }
        }
        return p;
    }

    // helper to rebuild expression string from tokens (for step display)
    private static String buildExprString(List<Integer> nums, List<Character> ops) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.size(); i++) {
            if (i > 0) sb.append(' ').append(ops.get(i - 1)).append(' ');
            sb.append(nums.get(i));
        }
        return sb.toString();
    }

    // d) Evaluate a paren-free expression with precedence, logging steps
    private static int evaluateFlat(String part, StringBuilder steps) {
        Parsed p = parseFlat(part);
        if (p.nums.isEmpty()) throw new IllegalArgumentException("No numbers to evaluate.");

        // First pass: * and /
        int i = 0;
        while (i < p.ops.size()) {
            char op = p.ops.get(i);
            if (op == '*' || op == '/') {
                String before = buildExprString(p.nums, p.ops);
                int a = p.nums.get(i), b = p.nums.get(i + 1);
                if (op == '/' && b == 0) throw new ArithmeticException("Division by zero.");

                int res = (op == '*') ? (a * b) : (a / b);
                // Replace tokens: nums[i] = res; remove nums[i+1] and ops[i]
                p.nums.set(i, res);
                p.nums.remove(i + 1);
                p.ops.remove(i);

                String after = buildExprString(p.nums, p.ops);
                steps.append("  ").append(before).append("  ⇒  ").append(after).append('\n');
            } else {
                i++;
            }
        }

        // Second pass: + and - (left to right)
        while (!p.ops.isEmpty()) {
            String before = buildExprString(p.nums, p.ops);
            int a = p.nums.get(0), b = p.nums.get(1);
            char op = p.ops.get(0);
            int res = (op == '+') ? (a + b) : (a - b);

            // collapse front
            p.nums.set(0, res);
            p.nums.remove(1);
            p.ops.remove(0);

            String after = buildExprString(p.nums, p.ops);
            steps.append("  ").append(before).append("  ⇒  ").append(after).append('\n');
        }

        return p.nums.get(0);
    }

    // e) Handle parentheses using lastIndexOf('(') and indexOf(')', from there)
    public static int evaluateExpression(String expr, StringBuilder steps) {
        String working = expr;

        // Reduce innermost parentheses first
        while (working.contains("(")) {
            int l = working.lastIndexOf('(');
            int r = working.indexOf(')', l + 1);
            if (l == -1 || r == -1) throw new IllegalArgumentException("Mismatched parentheses.");

            String inside = working.substring(l + 1, r);
            steps.append("Evaluate inside parentheses: (").append(inside).append(")\n");
            int innerVal = evaluateFlat(inside, steps);

            String before = working;
            working = working.substring(0, l) + innerVal + working.substring(r + 1);
            steps.append("  Replace (").append(inside).append(") with ").append(innerVal).append(":\n")
                 .append("  ").append(before).append("  ⇒  ").append(working).append('\n');
        }

        // Now a flat expression remains
        steps.append("Evaluate remaining (no parentheses): ").append(working).append('\n');
        int result = evaluateFlat(working, steps);
        steps.append("Final simplified form: ").append(result).append("\n");
        return result;
    }

    // f) Display steps + validation result
    public static void displayCalculation(String original, boolean valid, String steps, Integer result) {
        System.out.println("Expression : " + original);
        System.out.println("Valid      : " + (valid ? "Yes" : "No"));
        if (!valid) {
            System.out.println("Reason     : Invalid characters/operator placement/parentheses.");
            System.out.println("------------------------------------------------------------");
            return;
        }
        System.out.println("---- Calculation Steps ----");
        System.out.print(steps);
        System.out.println("Result     : " + result);
        System.out.println("------------------------------------------------------------");
    }

    // g) Main: process multiple expressions with detailed steps
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of expressions to evaluate: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter expression " + i + ":");
            String expr = sc.nextLine();

            boolean valid = validateExpression(expr);
            StringBuilder steps = new StringBuilder();
            Integer result = null;

            if (valid) {
                try {
                    steps.append("Original: ").append(expr).append('\n');
                    result = evaluateExpression(expr, steps);
                } catch (Exception ex) {
                    valid = false;
                    steps = new StringBuilder("Error during evaluation: " + ex.getMessage() + "\n");
                }
            }

            displayCalculation(expr, valid, steps.toString(), result);
        }
    }
}
