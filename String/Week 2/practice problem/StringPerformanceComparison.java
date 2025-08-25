public class StringPerformanceComparison {
    public static void main(String[] args) {
        System.out.println("=== PERFORMANCE COMPARISON ===");

        // Test 1: String concatenation performance
        long startTime, endTime;

        startTime = System.nanoTime();
        String result1 = concatenateWithString(1000);
        endTime = System.nanoTime();
        System.out.println("String concatenation time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        String result2 = concatenateWithStringBuilder(1000);
        endTime = System.nanoTime();
        System.out.println("StringBuilder concatenation time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        String result3 = concatenateWithStringBuffer(1000);
        endTime = System.nanoTime();
        System.out.println("StringBuffer concatenation time: " + (endTime - startTime) + " ns");

        // Demonstrating StringBuilder methods
        System.out.println("\n=== STRINGBUILDER METHODS ===");
        demonstrateStringBuilderMethods();

        // Demonstrating thread safety
        System.out.println("\n=== THREAD SAFETY DEMONSTRATION ===");
        demonstrateThreadSafety();

        // Comparing string comparison methods
        System.out.println("\n=== STRING COMPARISON METHODS ===");
        compareStringComparisonMethods();

        // Demonstrating memory efficiency
        System.out.println("\n=== MEMORY EFFICIENCY ===");
        demonstrateMemoryEfficiency();
    }

    // Method using String concatenation (inefficient)
    public static String concatenateWithString(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "Java " + i + " ";
        }
        return result;
    }

    // Method using StringBuilder (efficient, not thread-safe)
    public static String concatenateWithStringBuilder(int iterations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java ").append(i).append(" ");
        }
        return sb.toString();
    }

    // Method using StringBuffer (efficient, thread-safe)
    public static String concatenateWithStringBuffer(int iterations) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java ").append(i).append(" ");
        }
        return sb.toString();
    }

    // Demonstrating StringBuilder methods
    public static void demonstrateStringBuilderMethods() {
        StringBuilder sb = new StringBuilder("Hello World");
        System.out.println("Original: " + sb);

        sb.append(" Java");
        System.out.println("append(): " + sb);

        sb.insert(6, "Beautiful ");
        System.out.println("insert(): " + sb);

        sb.delete(6, 16);
        System.out.println("delete(): " + sb);

        sb.deleteCharAt(5);
        System.out.println("deleteCharAt(): " + sb);

        sb.reverse();
        System.out.println("reverse(): " + sb);
        sb.reverse(); // undo reverse

        sb.replace(0, 5, "Hi");
        System.out.println("replace(): " + sb);

        sb.setCharAt(3, 'X');
        System.out.println("setCharAt(): " + sb);

        System.out.println("capacity(): " + sb.capacity());
        sb.ensureCapacity(100);
        System.out.println("ensureCapacity(100): " + sb.capacity());

        sb.trimToSize();
        System.out.println("trimToSize(): capacity -> " + sb.capacity());
    }

    // Demonstrating StringBuffer thread safety
    public static void demonstrateThreadSafety() {
        StringBuffer buffer = new StringBuffer("Start");
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                buffer.append("X");
                System.out.println(Thread.currentThread().getName() + " -> " + buffer);
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final StringBuffer: " + buffer);
        System.out.println("Note: With StringBuilder, output would be inconsistent due to no synchronization.");
    }

    // Comparing string comparison methods
    public static void compareStringComparisonMethods() {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println("== operator: str1 == str2 -> " + (str1 == str2));
        System.out.println("== operator: str1 == str3 -> " + (str1 == str3));

        System.out.println("equals(): str1.equals(str2) -> " + str1.equals(str2));
        System.out.println("equals(): str1.equals(str3) -> " + str1.equals(str3));

        System.out.println("equalsIgnoreCase(): \"hello\".equalsIgnoreCase(\"HELLO\") -> " + "hello".equalsIgnoreCase("HELLO"));

        System.out.println("compareTo(): \"apple\".compareTo(\"banana\") -> " + "apple".compareTo("banana"));
        System.out.println("compareToIgnoreCase(): \"apple\".compareToIgnoreCase(\"APPLE\") -> " + "apple".compareToIgnoreCase("APPLE"));

        System.out.println("\n== compares references, equals() compares contents, compareTo() compares lexicographically.");
    }

    // Demonstrating memory efficiency
    public static void demonstrateMemoryEfficiency() {
        Runtime runtime = Runtime.getRuntime();

        long before = runtime.freeMemory();
        String s1 = "Hello";
        String s2 = "Hello"; // reuses same object from String pool
        String s3 = new String("Hello"); // creates new object
        long after = runtime.freeMemory();

        System.out.println("Memory before: " + before);
        System.out.println("Memory after creating strings: " + after);
        System.out.println("String pool reused s1 & s2, but s3 is separate.");

        StringBuilder sb = new StringBuilder();
        System.out.println("Initial capacity of StringBuilder: " + sb.capacity());
        for (int i = 0; i < 1000; i++) {
            sb.append("Java");
        }
        System.out.println("Capacity after appending: " + sb.capacity());
        sb.trimToSize();
        System.out.println("Capacity after trimToSize: " + sb.capacity());
    }
}

