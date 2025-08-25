public class Splitlength { 
    public static String[][] splitWordsWithLength(String text) { 
        String[] words = text.split("\\s+"); 
        String[][] result = new String[words.length][2]; 
        for (int i = 0; i < words.length; i++) { 
            result[i][0] = words[i]; 
            result[i][1] = String.valueOf(words[i].length()); 
        } 
        return result; 
    } 
 
    public static void main(String[] args) { 
        String text = "This is a sample sentence"; 
        String[][] wordsWithLength = splitWordsWithLength(text); 
        for (String[] pair : wordsWithLength) { 
            System.out.println(pair[0] + " : " + pair[1]); 
        } 
    } 
} 
