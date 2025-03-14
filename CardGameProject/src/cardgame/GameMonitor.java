package cardgame;

//GameMonitor.java
public class GameMonitor {
	private static GameMonitor instance; //singleton pattern, only one instance needed. 
 	private boolean gameOver = false;
 	
 	//private constructor, an instance cannot be created from outside the class, so no duplicates
 	private GameMonitor() {}
 	
 	public static synchronized GameMonitor getInstance() {		//using synchronized key word yet to prevent threads accessing it simultaneously
 		if (instance == null) {
 			instance = new GameMonitor();
 		}
 		return instance;
 	}
 	
 	
 	

 public synchronized boolean isGameOver() {
     return gameOver;
 }

 public synchronized void declareWinner(Player winner) {
     if (!gameOver) {
         gameOver = true;
         System.out.println("Player " + winner.getName() + " wins!");
     }
 }
}