package cardgame;

public interface GameObserver {
    void onGameEnd(int winnerId);
}