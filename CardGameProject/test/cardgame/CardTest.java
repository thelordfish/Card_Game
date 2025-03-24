package cardgame;

import static org.junit.Assert.*;
import org.junit.Test;

public class CardTest {

    @Test
    public void testCardInitialization() {
        Card card = new Card(5);
        assertEquals(5, card.getValue());
    }

    @Test
    public void testCardToString() {
        Card card = new Card(10);
        assertEquals("10", card.toString());
    }

    @Test
    public void testCardEquality() {
        Card card1 = new Card(7);
        Card card2 = new Card(7);
        assertEquals(card1.getValue(), card2.getValue());
    }

    @Test
    public void testCardDifferentValues() {
        Card card1 = new Card(3);
        Card card2 = new Card(4);
        assertNotEquals(card1.getValue(), card2.getValue());
    }
}