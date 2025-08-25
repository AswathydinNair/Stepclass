public class longeststring { 
    public static void main(String[] args) { 
        String text = "Write a program to split the text into words and find the shortest and longest strings in a given text";
        String[] words = text.split("\\s+"); 
        String shortest = words[0]; 
        String longest = words[0]; 
 
        for (String word : words) { 
            if (word.length() < shortest.length()) { 
                shortest = word; 
            } 
            if (word.length() > longest.length()) { 
                longest = word; 
            } 
        } 
 
        System.out.println("Shortest word: " + shortest); 
        System.out.println("Longest word: " + longest); 
    }
}