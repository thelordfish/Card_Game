package cardgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class CardDeckTest {
    private Deck deck;
    private List<Card> cards;

    @Before
    public void setUp() {
        cards = new ArrayList<>(Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4)));
        deck = new Deck(1, cards);
    }

    @Test
    public void testDeckInitialization() {
        assertEquals(1, deck.getDeckId());
    }

    @Test
    public void testDrawCard() {
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(1, drawnCard.getValue());
    }

    
    //if you discard a new card (5) to the deck, will it come out after, in FIFO
    @Test
    public void testDiscardCard() {
    	
        Card card = new Card(5);
        deck.discardCard(card);
        
        assertEquals(1, deck.drawCard().getValue()); // first
        assertEquals(2, deck.drawCard().getValue()); // second
        assertEquals(3, deck.drawCard().getValue()); // third
        assertEquals(4, deck.drawCard().getValue()); // fourth
        assertEquals(5, deck.drawCard().getValue()); // card(5) should appear here
    }

    @Test
    public void testDeckBecomesEmpty() {
        deck.drawCard();
        deck.drawCard();
        deck.drawCard();
        deck.drawCard();
        assertNull(deck.drawCard());  // Deck should be empty now
    }
}