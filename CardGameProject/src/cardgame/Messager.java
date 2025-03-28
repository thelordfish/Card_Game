package cardgame;

public class Messager {
	public static void notifyOtherPlayers(Player player) {
	    for (Thread thread : Thread.getAllStackTraces().keySet()) {
	        if (thread instanceof Player) {
	            Player otherPlayer = (Player) thread;
	            if (otherPlayer.getPlayerID() != player.getPlayerID()) {  // Don't notify self
	                otherPlayer.onGameEnd(player.getPlayerID());
	            }
	        }
	    }
	}
}
