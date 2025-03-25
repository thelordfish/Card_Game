package cardgame;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;


//this class ensures that decks being used either side of a player are locked until they BOTH draw and discard as one atomic action
//players can still take turns simultaneously as long as they don't pull from the same deck

public class Turn {
    public static void takeTurn(Player player, CardDeck leftDeck, CardDeck rightDeck) {
    	
    	if(Player.isGameOver()) return; //turn may have started before win was registered, check before locks
    	
        // lock left and right decks in consistent order to avoid deadlocks
        Object firstLock = leftDeck.getDeckId() < rightDeck.getDeckId() ? leftDeck : rightDeck;
        Object secondLock = leftDeck.getDeckId() < rightDeck.getDeckId() ? rightDeck : leftDeck;

        synchronized (firstLock) {
            synchronized (secondLock) {
            	
            	if (Player.isGameOver()) return; //likewise check after locks, but not between draw and discard, to keep 4 cards in final hand
                drawCard(player, rightDeck);
                discardCard(player, leftDeck);
            }
        }
    }
    

private static void discardCard(Player player, CardDeck rightDeck) {	// static means we dont have to create a Turn instance every time a player uses these methods
	    
		List<Card> discardOptions = new ArrayList<>();
	    for (Card card :player.getHand()) {
	    	if (card.getValue() != player.getPlayerID()) {		//collect cards in hand that are not the palyerID
	    		discardOptions.add(card);
	    	}
	    }
	    Card discardedCard = null;
	    if(!discardOptions.isEmpty()) {
	    	Random rand = new Random();
	    	discardedCard = discardOptions.get(rand.nextInt(discardOptions.size()));	//choose a random card from the discard options
	    }
	    // If a valid card is found to discard
	    if (discardedCard != null) {
	        player.getHand().remove(discardedCard);  // Remove the discarded card from the player's hand
	        rightDeck.discardCard(discardedCard);  // Discard the card to the rightDeck
	        System.out.println("Player " + player.getPlayerID() + " discards a " + discardedCard.getValue() + " to deck " + rightDeck.getDeckId());
	        LogWriter.writeToFile(player, "discards", discardedCard);
	    } else {
	        // Handle case where no valid card is found to discard
	        System.out.println("Player " + player.getPlayerID() + " has no card to discard.");
	    }
	}


private static void drawCard(Player player, CardDeck leftDeck) {
	    // Draw a card from the leftDeck
	    Card card = leftDeck.drawCard();
	    if (card == null) {
	        System.out.println("Deck is empty. Cannot draw a card.");
	        return;
	    }
	    // Add the drawn card to the player's hand
	    player.getHand().add(card);
	    System.out.println("Player " + player.getPlayerID() + " draws a " + card.getValue() + " from deck " + leftDeck.getDeckId());
	    LogWriter.writeToFile(player, "draws", card);
	}

}
