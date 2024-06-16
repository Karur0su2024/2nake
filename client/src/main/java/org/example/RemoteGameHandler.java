package org.example;

public class RemoteGameHandler implements GameHandler {
    private Game game;
    private GameClient gameClient;

    public RemoteGameHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void initializeGame(Game game) {
        this.game = game;
        /*gameClient.setGame(game);
        gameClient.connectToServer();*/
    }

    @Override
    public void sendPlayerAction(String action) {
        //gameClient.sendAction(action);
    }

    @Override
    public void receiveGameState(String gameState) {
        game.processGameState(gameState);
    }

    @Override
    public void updateGame() {
        // Update game state based on server communication
        //gameClient.requestGameState();
    }
}
