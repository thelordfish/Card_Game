package cardgame;

public class Messager {
    public static void notifyOtherPlayers(Player winner) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread instanceof Player otherPlayer && otherPlayer != winner) {
                if (otherPlayer.isAlive()) {
                    otherPlayer.onGameEnd(winner.getPlayerID());
                } else {
                    System.out.println("DEBUG: Player " + otherPlayer.getPlayerID() + " already exited before win.");
                }
            }
        }
    }
}
