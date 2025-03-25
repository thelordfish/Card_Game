// Player.java
package cardgame;

import java.util.*;

/*
 * C:\Users\Ollie Appleby\git\Card_Game\CardGameProject\pack.txt
 */

public class Player extends Thread implements Runnable {
 private int playerId;  // The ID of the player (1, 2, ... n)
 private List<Card> hand;  // List of cards that the player holds
 private CardDeck leftDeck;  // The deck from which the player draws
 private CardDeck rightDeck;  // The deck to which the player discards
 
 private static volatile boolean gameOver = false; //flag to stop the players loop - should prevent simultaneous winners

 public static boolean isGameOver() {	//method so it can be checked before takeTurn in Turn class
	 return gameOver;
 }
 
 public Player(int playerId) {
     this.playerId = playerId;
     this.hand = new ArrayList<>(4);  // A player starts with 4 cards
 }
 
 public void setLeftDeck(CardDeck leftDeck) {
	 this.leftDeck = leftDeck;
 }
 
 public void setRightDeck(CardDeck rightDeck) {
	 this.rightDeck = rightDeck;
 }
 
 public List<Card> getHand() {	
     return hand;
 }
 
 public void dealCard(Card card) {
	 hand.add(card);
 }
 public int getPlayerID() {
     return playerId;
 }

private void declareWin() throws InterruptedException {
	gameOver = true;	//update flag
    System.out.println("Player " + playerId + " wins");
    Messager.notifyOtherPlayers(this);
    Thread.sleep(50);  //gives time for the message to be processed
    LogWriter.writeFinalHandToFile(this, "wins");
	}

 //How the player plays the game:
 //loops
 
 @Override
 public void run() {
     try {
    	 
    	 LogWriter.writeInitialHandToFile(this);	//starting hand written to player log
    	 
         // If the player starts with 4 identical cards, they win, put in synchronized block to prevent race conditions
         synchronized (Player.class) {
	    	 if (!gameOver && checkForWin()) {
	    		 declareWin();
	             return;
	         }
         }

			// Game continues if the player doesn't win initially
         while (!gameOver) {
        	 Turn.takeTurn(this, leftDeck, rightDeck);

        	    if (gameOver) break;

        	    synchronized (Player.class) {		//prevents multiple threads winning simultaneously
	        	    if (!gameOver && checkForWin()) {
	        	    	declareWin();
	        	        break;
	        	    }
        	    }

        	    Thread.sleep(10);
        }

         //LogWriter.writeFinalHandToFile(this, "exits");
     } catch (InterruptedException e) {
    	 Thread.currentThread().interrupt();
     }
     }

 // observer pattern method from GameObserver interface- stops the player when another player wins

 public void onGameEnd(int winnerId) {
     gameOver = true;  // stops player's loop
     System.out.println("Player " + playerId + " exits. Player " + winnerId + " has won.");
     LogWriter.writeGameEndToFile(this, winnerId);  // log that they were informed by the winner

 }
 
 private boolean checkForWin() {
     Map<Integer, Integer> cardCounts = new HashMap<>();
     for (Card card : hand) {
         cardCounts.put(card.getValue(), cardCounts.getOrDefault(card.getValue(), 0) + 1);
     }
     // If there are 4 cards of the same value in the hand, the player wins
     return cardCounts.containsValue(4);
 }


	

}