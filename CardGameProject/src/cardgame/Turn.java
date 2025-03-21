package cardgame;

public class Turn {
    public static void takeTurn(Player player, CardDeck leftDeck, CardDeck rightDeck) {
        // Lock decks in consistent order to avoid deadlocks
        Object firstLock = leftDeck.getDeckId() < rightDeck.getDeckId() ? leftDeck : rightDeck;
        Object secondLock = leftDeck.getDeckId() < rightDeck.getDeckId() ? rightDeck : leftDeck;

        synchronized (firstLock) {
            synchronized (secondLock) {
                player.drawCard();
                player.discardCard();
            }
        }
    }
}
