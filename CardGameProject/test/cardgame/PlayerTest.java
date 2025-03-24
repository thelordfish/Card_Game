package cardgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class PlayerTest {
    private Player player;
    private CardDeck leftDeck;
    private CardDeck rightDeck;

    @Before
    public void setUp() {
        leftDeck = new CardDeck(1, Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4)));
        rightDeck = new CardDeck(2, new ArrayList<>());
        
        //make player and use player method to assign decks
        player = new Player(1);
        player.setLeftDeck(leftDeck);
        player.setRightDeck(rightDeck);
        
        //deal player all 1s
        for (int i = 0; i< 4; i++) {
        	player.dealCard(new Card(1));

        }
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals(1, player.getPlayerID());
        assertEquals(4, player.getHand().size());
    }

    @Test
    public void testTakeTurnKeepsHandSizeAtFour() {
        Turn.takeTurn(player, leftDeck, rightDeck);
        assertEquals(4, player.getHand().size());
    }
    

    public void testDiscardCardNotPreferred() {
        // add one non-preferred card so discard will happen
        player.getHand().clear();
        player.dealCard(new Card(1));
        player.dealCard(new Card(2));
        player.dealCard(new Card(1));
        player.dealCard(new Card(1));

        Turn.takeTurn(player, leftDeck, rightDeck);

        boolean stillHasNonPreferred = player.getHand().stream()
            .anyMatch(card -> card.getValue() != player.getPlayerID());

        assertFalse(stillHasNonPreferred); // should have discarded the non-preferred card/not its playerID
    }

    @Test
    public void testWinningCondition() {
        player.getHand().add(new Card(5));
        player.getHand().add(new Card(5));
        player.getHand().add(new Card(5));
        player.getHand().add(new Card(5));
        
        //check for win is a private method, using reflection to access it in a try-catch block
        try {
	        java.lang.reflect.Method checkForWin = Player.class.getDeclaredMethod("checkForWin"); //method is a class from java.lang.reflect 
	        checkForWin.setAccessible(true); //make an exception for the private method
	        
	        boolean result = (boolean) checkForWin.invoke(player); //runs the method on the player object
	        
	        assertTrue(result); // Player should win
	        
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Reflection failed " + e.getMessage()); //if the reflection doesnt work
        }
    }
}