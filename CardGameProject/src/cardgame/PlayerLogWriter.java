package cardgame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerLogWriter implements LogWriterInterface {
    private static final String directory = "./";
    private final Player player;

    public PlayerLogWriter(Player player) {
        this.player = player;
    }
    
    
  

    @Override
    public void writeLog(String stage, int winnerId) {
        String filename = directory + "player" + player.getPlayerID() + "_output.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            switch (stage) {
                case "initial":
                    writer.write("player " + player.getPlayerID() + " initial hand: " + handToString());
                    break;
                case "win":
                    writer.write("player " + player.getPlayerID() + " wins");
                    writer.newLine();
                    writer.write("player " + player.getPlayerID() + " final hand: " + handToString());
                    break;
                case "loss":
                    writer.write("player " + winnerId + " has informed player " + player.getPlayerID() + " that player " + winnerId + " has won");
                    writer.newLine();
                    writer.write("player " + player.getPlayerID() + " exits");
                    writer.newLine();
                    writer.write("player " + player.getPlayerID() + " final hand: " + handToString());
                    break;
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void writeCardAction(String action, Card card) {
        String filename = directory + "player" + player.getPlayerID() + "_output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Player " + player.getPlayerID() + " " + action + " a " + card.getValue());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handToString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : player.getHand()) {
            sb.append(card.getValue()).append(" ");
        }
        return sb.toString().trim();
    }
}
