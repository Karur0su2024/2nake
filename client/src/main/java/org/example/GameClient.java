package org.example;


import org.example.objects.Snake;
import org.example.ui.GameFrame;
import org.example.ui.GamePanel;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12344;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GamePanel gamePanel;
    private int player;

    public GameClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new ServerListener()).start();
            System.out.println("Připojeno");

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
                if (direction != 'R') snake.setDirection('L');
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (direction != 'L') snake.setDirection('R');
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (direction != 'D'){
                    snake.setDirection('U');
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != 'U') snake.setDirection('D');
                break;
        }

        out.println("move " + player + " " + snake.getDirection());
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    // Process messages received from the server
                    System.out.println(message);
                    /*if(message.equals("start")){
                        new GameFrame(2, null, 50, 20, 5, 6, 20, 60, "remote", GameClient.this);

                    }*/


                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
