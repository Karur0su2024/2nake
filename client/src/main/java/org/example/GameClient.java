package org.example;


import com.google.gson.Gson;
import org.example.objects.Snake;
import org.example.ui.GameFrame;
import org.example.ui.GamePanel;
import org.example.ui.MainMenuFrame;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12343;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GamePanel gamePanel;
    private int player;
    public boolean started = false;
    public Game game;
    public MainMenuFrame menuFrame;

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

        Snake snake = gamePanel.game.getSnakes()[player];
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
                if (direction != 'D'){
                    sendMove('U');
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != 'U')
                    sendMove('D');
                break;
        }

        out.println("move " + player + " " + snake.getDirection());
    }

    public void sendMove(char direction) {
        out.println("MOVE " + direction);
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    // Process messages received from the server

                    if(started){
                        updateGameState(message);
                    }

                    if(message.equals("start")){
                        started = true;
                    }



                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGameState(String stateMessage) {
        System.out.println(Game.fromString(stateMessage));
        //updateUI();
    }
}
