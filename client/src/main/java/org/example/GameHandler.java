package org.example;

public interface GameHandler {
    void initializeGame(Game game);

    void sendPlayerAction(int player, int key);

    void receiveGameState(String gameState);
    void updateGame();
}
