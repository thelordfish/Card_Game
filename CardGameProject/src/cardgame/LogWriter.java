package cardgame;
import cardgame.Player;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
	
    private static final String directory= "./"; // Store in the same directory as the game

	
	public static String handToString(Player player) {
	    StringBuilder sb = new StringBuilder();
	    for (Card card : player.getHand()) {
	        sb.append(card.getValue()).append(" ");
	    }
	    return sb.toString().trim();
		}

	public static void writeToFile(Player player, String action, Card card) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("player" + player.getPlayerID() + "_output.txt", true))) {
	        writer.write("Player " + player.getPlayerID() + " " + action + " a " + card.getValue());
	        writer.newLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	 
	 public static void writeGameEndToFile(Player player, int winnerId) {
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "player" + player.getPlayerID() + "_output.txt", true))) {
		        writer.write("player " + winnerId + " has informed player " + player.getPlayerID() + " that player " + winnerId + " has won");
		        writer.newLine();
		        writer.write("player " + player.getPlayerID() + " exits");
		        writer.newLine();
		        writer.write("player " + player.getPlayerID() + " final hand: " + handToString(player));
		        writer.newLine();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	 public static void writeInitialHandToFile(Player player) {
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "player" + player.getPlayerID() + "_output.txt", true))) {
		        writer.write("player " + player.getPlayerID() + " initial hand: " + handToString(player));
		        writer.newLine();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	 public static void writeFinalHandToFile(Player player, String status) {
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "player" + player.getPlayerID() + "_output.txt", true))) {
		        writer.write("player " + player.getPlayerID() + " " + status);
		        writer.newLine();
		        writer.write("player " + player.getPlayerID() + " final hand: " + handToString(player));
		        writer.newLine();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

}
