package cardgame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DeckLogWriter implements LogWriterInterface {
    private static final String directory = "./";
    private final Deck deck;

    public DeckLogWriter(Deck deck) {
        this.deck = deck;
    }

   
    @Override
    public void writeLog(String mode, int winnerId) {
        if (!"final".equals(mode)) return;  // only final works for deck

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "deck" + deck.getDeckId() + "_output.txt"))) {
            writer.write("deck" + deck.getDeckId() + " contents: ");
            for (Card card : deck.getCardQueue()) {
                writer.write(card.getValue() + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
