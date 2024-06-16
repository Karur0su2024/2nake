package org.example;

public interface GameHandler {
    void initializeGame(Game game);
    void sendPlayerAction(String action);
    void receiveGameState(String gameState);
    void updateGame();
}
