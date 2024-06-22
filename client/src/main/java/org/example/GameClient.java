package org.example;

import org.example.objects.Snake;
import org.example.gui.GameFrame;
import org.example.gui.GamePanel;
import org.example.gui.MainMenuFrame;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1000;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GamePanel gamePanel;
    private int player = 99;
    private boolean started = false;
    private Game game; // Hold a reference to the game state
    private MainMenuFrame menuFrame;
    private GameFrame gameFrame;

    public GameClient(MainMenuFrame menuFrame) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new ServerListener()).start();
            this.menuFrame = menuFrame;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGamePanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void sendMessage(int player, int key) {
        Snake snake = gamePanel.getGame().getSnakes()[player];
        char direction = snake.getDirection();

        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (direction != 'R') sendMove('L');
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (direction != 'L') sendMove('R');
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (direction != 'D') sendMove('U');
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != 'U') sendMove('D');
                break;
        }
    }

    public void sendMove(char direction) {

        out.println("move " + player + " " + direction);
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {


                    if (started && gameFrame != null) {
                        // Update game state on receiving new state message from server


                        updateGameState(message);

                    }
                    if(started && gameFrame == null && player != 99){
                        game = Game.fromString(message);
                        gameFrame = new GameFrame(game, GameClient.this, menuFrame, player);
                    }


                    if (message.equals("start")) {
                        started = true;



                        // You might want to initialize the GameFrame after receiving "start"
                        // But this should be done in a way that integrates with your UI setup
                    }

                    String[] parts = message.split(" ", 3);
                    if(parts.length > 1){
                        String command = parts[0];
                        int playerId = Integer.parseInt(parts[1]); // Corrected from getInteger to parseInt

                        if (command.equals("player")){
                            player = playerId;

                        }
                    }




                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGameState(String stateMessage) {
        // Parse the stateMessage into a Game object using your Game.fromString method
        Game updatedGame = Game.fromString(stateMessage);
        this.game = updatedGame;
        // Update the local reference to the game state


        // Update the GamePanel with the updated game state
        if (gamePanel != null) {
            gamePanel.updateGame(this.game);
        }
    }

}
