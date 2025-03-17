// Player.java
package cardgame;

import java.util.*;

public class Player extends Thread {
 private int playerId;  // The ID of the player (1, 2, ... n)
 private List<Card> hand;  // List of cards that the player holds
 private CardDeck leftDeck;  // The deck from which the player draws
 private CardDeck rightDeck;  // The deck to which the player discards
 
 private volatile boolean gameOver = false; //***flag to stop the players loop***

 public Player(int playerId, CardDeck leftDeck, CardDeck rightDeck) {
     this.playerId = playerId;
     this.hand = new ArrayList<>(4);  // A player starts with 4 cards
     this.leftDeck = leftDeck;
     this.rightDeck = rightDeck;
     
 }
 
 public List<Card> getHand() {	//needed to log players logs at the end
     return hand;
 }
 
 
 public int getPlayerID() {
     return playerId;
 }

 @Override
 public void run() {
     try {
    	 
    	 LogWriter.writeInitialHandToFile(this);
    	 
         // If the player starts with 4 identical cards, they win
         if (checkForWin()) {
        	 Messager.notifyOtherPlayers(this);
             System.out.println("Player " + playerId + " wins");
             LogWriter.writeFinalHandToFile(this, "wins");
             return;
         }

         // Game continues if the player doesn't win initially
         while (!gameOver) {
             drawCard();
             discardCard();
             
             if(gameOver) break; //check new hand after drawing AND discarding
             
             if (checkForWin()) {
                 System.out.println("Player " + playerId + " wins");
                 Messager.notifyOtherPlayers(this);
                 LogWriter.writeFinalHandToFile(this, "wins");
                 break;
             }
             Thread.sleep(500);  // Add a small delay for simulation
         }
         
         LogWriter.writeFinalHandToFile(this, "exits");
         
     } catch (InterruptedException e) {
    	 Thread.currentThread().interrupt();
         e.printStackTrace();
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

 private void drawCard() {
	    // Draw a card from the leftDeck
	    Card card = leftDeck.drawCard();
	    if (card == null) {
	        System.out.println("Deck is empty. Cannot draw a card.");
	        return;
	    }
	    // Add the drawn card to the player's hand
	    hand.add(card);
	    System.out.println("Player " + playerId + " draws a " + card.getValue() + " from deck " + leftDeck.getDeckId());
	    LogWriter.writeToFile(this, "draws", card);
	}

	private void discardCard() {
	    Card discardedCard = null;
	    
	    // Find a card to discard
	    for (Card card : hand) {
	        if (card.getValue() != playerId) {  // Discard a card that is not of the player's preferred denomination
	            discardedCard = card;
	            break;
	        }
	    }
	    
	    // If a valid card is found to discard
	    if (discardedCard != null) {
	        hand.remove(discardedCard);  // Remove the discarded card from the player's hand
	        rightDeck.discardCard(discardedCard);  // Discard the card to the rightDeck
	        System.out.println("Player " + playerId + " discards a " + discardedCard.getValue() + " to deck " + rightDeck.getDeckId());
	        LogWriter.writeToFile(this, "discards", discardedCard);
	    } else {
	        // Handle case where no valid card is found to discard
	        System.out.println("Player " + playerId + " has no card to discard.");
	    }
	}

	

}