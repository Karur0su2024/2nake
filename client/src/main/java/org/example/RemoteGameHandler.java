package org.example;

public class RemoteGameHandler implements GameHandler {

    GameClient gameClient;
    Game game;

    public RemoteGameHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void initializeGame(Game game) {
        this.game = game;
    }

    @Override
    public void sendPlayerAction(int player, int key) {

    }


    @Override
    public void receiveGameState(String message) {
        String[] parts = message.split(" ", 3);
        String command = parts[0];
        int player = Integer.getInteger(parts[1]);
        char parameter = 'A';
        if(parts[2] != null){
            parameter = parts[2].charAt(0);
        }

        if (command.equals("move")) {
            // Example: "MOVE playerId direction"
            // Parse message and update player position or perform related actions
            // Example implementation:
            // handleMoveCommand(message);
            game.getSnakes()[player].setDirection(parameter);
        }
    }

    @Override
    public void updateGame() {

    }
}
