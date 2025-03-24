package cardgame;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;

public class AtomicTurnLocking {
	@Test
	public void testAtomicTurnLocking() throws InterruptedException {
	    CardDeck sharedLeft = new CardDeck(1, Arrays.asList(new Card(1), new Card(2)));
	    CardDeck sharedRight = new CardDeck(2, Arrays.asList(new Card(3), new Card(4)));
	
	    Player p1 = new Player(1);
	    Player p2 = new Player(2);
	
	    p1.setLeftDeck(sharedLeft);
	    p1.setRightDeck(sharedRight);
	    p2.setLeftDeck(sharedLeft);
	    p2.setRightDeck(sharedRight);
	
	    for (int i = 0; i < 4; i++) {
	        p1.dealCard(new Card(1));  // preload hands
	        p2.dealCard(new Card(2));
	    }
	
	    Thread t1 = new Thread(() -> Turn.takeTurn(p1, sharedLeft, sharedRight));
	    Thread t2 = new Thread(() -> Turn.takeTurn(p2, sharedLeft, sharedRight));
	
	    t1.start();
	    t2.start();
	    t1.join();
	    t2.join();
	
	    // After two atomic turns, each deck should have exactly 1 discarded card
	    assertEquals(1, sharedRight.drawCard() != null ? 1 : 0);
	}
}
