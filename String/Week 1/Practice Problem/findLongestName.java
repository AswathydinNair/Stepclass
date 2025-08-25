 public static String findLongestName(String[] names) {
       String longest = "";
       for (String name : names) {
           if (name.length() > longest.length()) {
               longest = name;
           }
       }
       return longest;
   }


   // Counts how many names start with a given letter (case-insensitive)
   public static int countNamesStartingWith(String[] names, char letter) {
       int count = 0;
       char target = Character.toLowerCase(letter);
       for (String name : names) {
           if (!name.isEmpty() && Character.toLowerCase(name.charAt(0)) == target) {
               count++;
           }
       }
       return count;
   }


   // Formats names from "First Last" to "Last, First"
   public static String[] formatNames(String[] names) {
       String[] formatted = new String[names.length];
       for (int i = 0; i < names.length; i++) {
           String[] parts = names[i].split(" ");
           if (parts.length >= 2) {
               formatted[i] = parts[1] + ", " + parts[0];
           } else {
               formatted[i] = names[i]; // In case name doesn't follow "First Last" format
           }
       }
       return formatted;
   }


   public static void main(String[] args) {
       String[] students = {
           "seema patel", "Ashvini chourasia", "Aswathy nair",
           "Shambhavi", "Aditi krishna"
       };


       // Test findLongestName
       String longest = findLongestName(students);
       System.out.println("Longest name: " + longest);


       // Test countNamesStartingWith
       char letter = 'A';
       int count = countNamesStartingWith(students, letter);
       System.out.println("Number of names starting with '" + letter + "': " + count);


       // Test formatNames
       String[] formattedNames = formatNames(students);
       System.out.println("Formatted names (Last, First):");
       for (String name : formattedNames) {
           System.out.println(name);
       }
    }
}







