package cardgame;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
public class OnlyFirstWinnerCounts{
	

@Test
public void testOnlyFirstWinnerCounts() throws InterruptedException {
    int numPlayers = 4;
    List<CardDeck> decks = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
        decks.add(new CardDeck(i + 1, new ArrayList<>()));
    }

    List<Player> players = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
        Player p = new Player(i + 1);
        p.setLeftDeck(decks.get(i));
        p.setRightDeck(decks.get((i + 1) % numPlayers));
        players.add(p);

        // All players get winning hands
        p.getHand().add(new Card(p.getPlayerID()));
        p.getHand().add(new Card(p.getPlayerID()));
        p.getHand().add(new Card(p.getPlayerID()));
        p.getHand().add(new Card(p.getPlayerID()));
    }

    // Delay players just slightly to increase thread overlap
    List<Thread> threads = new ArrayList<>();
    for (Player p : players) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(new Random().nextInt(20));  // Simulated start jitter
                p.start();
                p.join();  // Wait for player to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        threads.add(t);
    }

    // Start all player threads
    for (Thread t : threads) {
        t.start();
    }

    // Wait for all threads to finish
    for (Thread t : threads) {
        t.join();
    }

    // Reflectively count how many still say they "won"
    int winnerCount = 0;
    try {
        java.lang.reflect.Method method = Player.class.getDeclaredMethod("checkForWin");
        method.setAccessible(true);
        for (Player p : players) {
            if ((boolean) method.invoke(p)) {
                winnerCount++;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        fail("Reflection failed: " + e.getMessage());
    }

    // ❗ This is the REAL test:
    assertEquals(1, winnerCount);
}
}
