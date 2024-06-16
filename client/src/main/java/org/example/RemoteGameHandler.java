package org.example;

import org.example.objects.Snake;

public class RemoteGameHandler implements GameHandler {

    private final GameClient gameClient;
    private Game game;

    public RemoteGameHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void initializeGame(Game game) {
        this.game = game;
    }

    @Override
    public void sendPlayerAction(int player, int key) {
        // Example: Send player action to server
        gameClient.sendMessage(player, key);


    }

    @Override
    public void receiveGameState(String message) {
        // Parse the incoming message and update the game state accordingly
        String[] parts = message.split(" ", 3);
        String command = parts[0];
        int playerId = Integer.parseInt(parts[1]); // Corrected from getInteger to parseInt
        char parameter = 'A'; // Default value, if none provided
        if (parts.length > 2) {
            parameter = parts[2].charAt(0);
        }

        // Handle different types of commands from the server
        switch (command) {
            case "move":
                // Example: "move playerId direction"
                handleMoveCommand(playerId, parameter);
                break;
            // Add more cases for other commands as needed
            default:
                // Handle unrecognized commands or log them
                break;
        }
    }

    private void handleMoveCommand(int playerId, char direction) {
        // Update the direction of the player's snake in the game state
        if (game != null && game.getSnakes().length > playerId) {
            Snake[] snakes = game.getSnakes();
            snakes[playerId].setDirection(direction);
            game.setSnakes(snakes); // Update the snakes array in the game
        }
    }

    @Override
    public void updateGame() {
        // Update the UI or perform other actions based on the updated game state
        // This method could be used to trigger UI updates after receiving and processing a new game state
    }
}
