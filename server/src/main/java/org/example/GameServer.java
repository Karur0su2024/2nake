package org.example;

import org.example.objects.Snake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public class GameServer implements ActionListener {
    private static final int PORT = 1000;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private int time;
    private Timer timer;
    private boolean alive = true;
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private Game game;

    public GameServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Starting server...");
            System.out.println(alive);
            while (alive) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                log.info("Hráč " + clientHandlers.size() + " se připojil do hry");
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();

                if (clientHandlers.size() == 2) {

                    startGame();

                    int player = 0;
                    for(ClientHandler clientHandler1 : clientHandlers){
                        clientHandler1.setGame(game);
                        clientHandler1.setPlayer(player);
                        player++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        time = 0;
        // Increase the timer interval for smoother gameplay
        timer = new Timer(50, this);

        game = new Game(2, 60, 50, 20, 5, 6, 180);

        broadcastMessage("start");

        game.startGame();
        timer.start();

        broadcastGame();
    }

    private synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clientHandlers) {
            client.out.println(message);
        }
    }

    private synchronized void broadcastGame() {
        String gameState = game.toString();
        for (ClientHandler client : clientHandlers) {
            client.out.println(gameState);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time++;

        synchronized (game) {
            if (game.isRunning()) {
                time++;
                if (time % 200 * game.getPlayers()  == 0) {
                    game.generateAction();
                }

                if (time % 80 * game.getPlayers() == 0) {
                    game.decreaseTime();
                }

                for (Snake snake : game.getSnakes()) {
                    if (time % snake.getSpeed() == 0) {
                        snake.move();
                        game.checkCollisions();
                        game.checkFood();
                        System.out.println(game.toString());
                    }
                    if (game.getTime() == 0) {
                        game.setRunning(false);
                    }
                }
            }

            broadcastGame();
        }
    }
}
