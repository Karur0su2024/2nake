package org.example;

public class LocalGameHandler implements GameHandler {
    private Game game;

    @Override
    public void initializeGame(Game game) {
        this.game = game;
        game.initializeGame();
    }

    @Override
    public void sendPlayerAction(String action) {
        // Directly handle player action locally
        game.playerAction(action);
    }

    @Override
    public void receiveGameState(String gameState) {
        // Not needed for local game
    }

    @Override
    public void updateGame() {
        game.checkFood();
        game.checkCollisions();
        game.generateAction();
    }
}