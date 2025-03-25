package cardgame;

//Deck.java
import java.util.*;
import java.io.*;

public class Deck {
 private int deckId;  // The ID of the deck (1, 2, ...)
 private Queue<Card> cards;  // The cards in the deck

 public Deck(int deckId, List<Card> cards) {
     this.deckId = deckId;
     this.cards = new LinkedList<>(cards);
 }

 public int getDeckId() {
     return deckId;
 }

 public synchronized Card drawCard() {
     return cards.poll();  // Draw the top card from the deck
 }

 public synchronized void discardCard(Card card) {
     cards.offer(card);  // Add the card to the bottom of the deck
 }

 public void writeDeckToFile() {
     try (BufferedWriter writer = new BufferedWriter(new FileWriter("deck" + deckId + "_output.txt"))) {
         writer.write("deck" + deckId + " contents: ");
         for (Card card : cards) {
             writer.write(card.getValue() + " ");
         }
         writer.newLine();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}