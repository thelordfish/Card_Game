package cardgame;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class TurnConcurrencyTest {

    @Test
    public void testDeckDrawConcurrency() throws InterruptedException {
        CardDeck sharedDeck = new CardDeck(1, new ArrayList<>(
                Arrays.asList(new Card(1), new Card(2), new Card(3), new Card(4))
        ));

        List<Card> drawnCards = Collections.synchronizedList(new ArrayList<>());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                Card card = sharedDeck.drawCard();
                if (card != null) drawnCards.add(card);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                Card card = sharedDeck.drawCard();
                if (card != null) drawnCards.add(card);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Should have exactly 4 distinct draws
        assertEquals(4, drawnCards.size());
    }
}
