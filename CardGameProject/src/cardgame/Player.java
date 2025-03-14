// Player.java
package cardgame;

//Player.java
import java.util.*;
import java.io.*;

public class Player extends Thread {
 private int playerId;  // The ID of the player (1, 2, ...)
 private List<Card> hand;  // List of cards that the player holds
 private CardDeck leftDeck;  // The deck from which the player draws
 private CardDeck rightDeck;  // The deck to which the player discards

 public Player(int playerId, CardDeck leftDeck, CardDeck rightDeck) {
     this.playerId = playerId;
     this.hand = new ArrayList<>(4);  // A player starts with 4 cards
     this.leftDeck = leftDeck;
     this.rightDeck = rightDeck;
 }

 public List<Card> getHand() {
     return hand;
 }

 public void run() {
     try {
         // If the player starts with 4 identical cards, they win
         if (checkForWin()) {
             System.out.println("Player " + playerId + " wins");
             writeToFile("wins");
             return;
         }

         // Game continues if the player doesn't win initially
         while (true) {
             drawCard();
             discardCard();
             if (checkForWin()) {
                 System.out.println("Player " + playerId + " wins");
                 writeToFile("wins");
                 break;
             }
             Thread.sleep(500);  // Add a small delay for simulation
         }
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
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
	    writeToFile("draws", card);
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
	        writeToFile("discards", discardedCard);
	    } else {
	        // Handle case where no valid card is found to discard
	        System.out.println("Player " + playerId + " has no card to discard.");
	    }
	}

 private void writeToFile(String action, Card card) {
     try (BufferedWriter writer = new BufferedWriter(new FileWriter("player" + playerId + "_output.txt", true))) {
         writer.write("Player " + playerId + " " + action + " a " + card.getValue());
         writer.newLine();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

 private void writeToFile(String status) {
     try (BufferedWriter writer = new BufferedWriter(new FileWriter("player" + playerId + "_output.txt", true))) {
         writer.write("Player " + playerId + " " + status);
         writer.newLine();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}