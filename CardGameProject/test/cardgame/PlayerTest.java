package cardgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class PlayerTest {
    private Player player;
    private CardDeck leftDeck;
    private CardDeck rightDeck;
    private List<Card> handCards;

    @Before
    public void setUp() {
        leftDeck = new CardDeck(1, Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4)));
        rightDeck = new CardDeck(2, new ArrayList<>());
        player = new Player(1, leftDeck, rightDeck);
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals(1, player.getPlayerID());
    }

    @Test
    public void testDrawCard() {
        player.drawCard();
        assertEquals(1, player.getHand().get(0).getValue());
    }

    @Test
    public void testDiscardCard() {
        player.drawCard();
        player.discardCard();
        assertTrue(player.getHand().isEmpty());  // Ensuring card was discarded
    }

    @Test
    public void testWinningCondition() {
        player.getHand().add(new Card(5));
        player.getHand().add(new Card(5));
        player.getHand().add(new Card(5));
        player.getHand().add(new Card(5));
        assertTrue(player.checkForWin()); // Player should win
    }
}