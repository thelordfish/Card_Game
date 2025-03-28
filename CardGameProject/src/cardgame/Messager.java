package cardgame;

public class Messager {
    public static void notifyOtherPlayers(Player winner) {
        for (Player player : GameDriver.players) {
            if (player != winner) {
                player.onGameEnd(winner.getPlayerID());
            }
        }
    }
}
