import java.util.Scanner;

public class StringPerformTest {
    public static long stringConcatTest(int iterations) {
        long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < iterations; i++) {
            str += "abc"; 
        }
        long end = System.currentTimeMillis();
        System.out.println("String length: " + str.length());
        return end - start;
    }

    public static long stringBuilderTest(int iterations) {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("abc");
        }
        long end = System.currentTimeMillis();
        System.out.println("StringBuilder length: " + sb.length());
        return end - start;
    }
    public static long stringBufferTest(int iterations) {
        long start = System.currentTimeMillis();
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sbuf.append("abc");
        }
        long end = System.currentTimeMillis();
        System.out.println("StringBuffer length: " + sbuf.length());
        return end - start;
    }

    public static void displayResults(long timeString, long timeBuilder, long timeBuffer) {
        System.out.println("\nPerformance Comparison:");
        System.out.printf("%-15s %-20s %-20s\n", "Method", "Time Taken (ms)", "Memory Efficiency");
        System.out.printf("%-15s %-20d %-20s\n", "String", timeString, "Low (immutable)");
        System.out.printf("%-15s %-20d %-20s\n", "StringBuilder", timeBuilder, "High (mutable, not thread-safe)");
        System.out.printf("%-15s %-20d %-20s\n", "StringBuffer", timeBuffer, "Medium (mutable, thread-safe)");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of iterations (e.g. 1000, 10000, 100000): ");
        int iterations = sc.nextInt();

        long timeString = stringConcatTest(iterations);
        long timeBuilder = stringBuilderTest(iterations);
        long timeBuffer = stringBufferTest(iterations);

        displayResults(timeString, timeBuilder, timeBuffer);

        sc.close();
    }
}
