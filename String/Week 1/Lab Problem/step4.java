import java.util.Scanner; 
 
public class step4 { 
   
   public static String getComputerChoice() { 
       String[] choices = {"rock", "paper", "scissors"}; 
       int idx = (int) (Math.random() * 3); 
       return choices[idx]; 
   } 
 
   public static String getWinner(String user, String computer) { 
       if (user.equals(computer)) return "Draw"; 
       if ((user.equals("rock") && computer.equals("scissors")) || 
           (user.equals("paper") && computer.equals("rock")) || 
           (user.equals("scissors") && computer.equals("paper"))) { 
           return "Player"; 
       } 
       return "Computer"; 
   } 
 
   public static String[][] getStats(int playerWins, int computerWins, int draws, int 
totalGames) { 
       String[][] stats = new String[3][3]; 
       stats[0][0] = "Player Wins"; 
       stats[0][1] = String.valueOf(playerWins); 
       stats[0][2] = String.format("%.2f%%", (playerWins * 100.0) / totalGames); 
       stats[1][0] = "Computer Wins"; 
       stats[1][1] = String.valueOf(computerWins); 
       stats[1][2] = String.format("%.2f%%", (computerWins * 100.0) / totalGames); 
       stats[2][0] = "Draws"; 
       stats[2][1] = String.valueOf(draws); 
       stats[2][2] = String.format("%.2f%%", (draws * 100.0) / totalGames); 
       return stats; 
   } 
 
   public static void displayResults(String[][] gameResults, String[][] stats, int 
totalGames) { 
       System.out.println("\nGame\tPlayer\tComputer\tWinner"); 
       for (int i = 0; i < totalGames; i++) { 
           System.out.printf("%d\t%s\t%s\t\t%s\n", i + 1, gameResults[i][0], 
gameResults[i][1], gameResults[i][2]); 
       } 
       System.out.println("\nStats:"); 
       System.out.println("Type\t\tCount\tPercentage"); 
       for (String[] row : stats) { 
           System.out.printf("%-12s\t%s\t%s\n", row[0], row[1], row[2]); 
       } 
   } 
 
   public static void main(String[] args) { 
       Scanner sc = new Scanner(System.in); 
       System.out.print("Enter number of games: "); 
       int totalGames = sc.nextInt(); 
       sc.nextLine(); 
       String[][] gameResults = new String[totalGames][3]; 
       int playerWins = 0, computerWins = 0, draws = 0; 
 
       for (int i = 0; i < totalGames; i++) { 
           System.out.print("Enter your choice (rock/paper/scissors): "); 
           String userChoice = sc.nextLine().toLowerCase(); 
           while (!userChoice.equals("rock") && !userChoice.equals("paper") && 
!userChoice.equals("scissors")) { 
               System.out.print("Invalid input. Enter rock, paper, or scissors: "); 
               userChoice = sc.nextLine().toLowerCase(); 
           } 
           String computerChoice = getComputerChoice(); 
           String winner = getWinner(userChoice, computerChoice); 
           if (winner.equals("Player")) playerWins++; 
           else if (winner.equals("Computer")) computerWins++; 
           else draws++; 
           gameResults[i][0] = userChoice; 
           gameResults[i][1] = computerChoice; 
           gameResults[i][2] = winner; 
       } 
String[][] stats = getStats(playerWins, computerWins, draws, totalGames); 
displayResults(gameResults, stats, totalGames); 
sc.close(); 
} 
} 