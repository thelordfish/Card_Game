package cardgame;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class CardGameIntegrationTest {

    @Test
    public void testGameCompletesWithOneWinner() throws InterruptedException {
        int numPlayers = 4;

        // Create decks
        List<CardDeck> decks = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new CardDeck(i + 1, new ArrayList<>()));
        }

        // Create players
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player(i + 1);
            p.setLeftDeck(decks.get(i));
            p.setRightDeck(decks.get((i + 1) % numPlayers));
            players.add(p);
        }

        // Give each player a winning hand (to simulate fast win)
        for (Player p : players) {
            p.getHand().clear();
            p.getHand().add(new Card(p.getPlayerID()));
            p.getHand().add(new Card(p.getPlayerID()));
            p.getHand().add(new Card(p.getPlayerID()));
            p.getHand().add(new Card(p.getPlayerID()));
        }

        // Start threads
        for (Player p : players) {
            p.start();
        }

        // Wait for threads to finish
        for (Player p : players) {
            p.join();
        }

        // Verify exactly one player won
        long winners = players.stream().filter(Player::checkForWin).count();
        assertEquals(1, winners);
    }
}
