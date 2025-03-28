package cardgame;

//Deck.java
import java.util.*;
import java.io.*;

public class Deck {
 private int deckId; 
 private Queue<Card> cards;  


 
 public Deck(int deckId, List<Card> cards) {
     this.deckId = deckId;
     this.cards = new LinkedList<>(cards);
 }

 public int getDeckId() {
     return deckId;
 }

 public Queue<Card> getCardQueue() {
	    return cards;
	}
 
 public synchronized Card drawCard() {
     return cards.poll();  // draw the top card from the deck
 }

 public synchronized void discardCard(Card card) {
     cards.offer(card);  // add the card to the bottom of the deck
 }
}

