package org.example;

import org.example.objects.Snake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The GameServer class represents the server part of the game, managing client connections,
 * initializing and controlling the game flow, and communicating with clients over the network.
 */
public class GameServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(GameServer.class);
    private final ClientHandler[] clientHandlers;
    private volatile boolean alive = true;
    private int time;
    private Game game;
    private ServerSocket serverSocket;
    private final ScheduledExecutorService scheduler;

    /**
     * Constructor to create an instance of GameServer that listens on the specified port.
     * Starts the server and waits for client connections. After two clients are connected, starts the game.
     *
     * @param clientHandlers array of ClientHandler instances representing connected clients
     */
    public GameServer(ClientHandler[] clientHandlers) {
        this.clientHandlers = clientHandlers;
        this.scheduler = Executors.newScheduledThreadPool(1);

        startGame();
        initializeClients();
    }

    /**
     * Initializes the game and sets up clients with their respective game and player index.
     */
    private void initializeClients() {
        for (int i = 0; i < clientHandlers.length; i++) {
            clientHandlers[i].setGame(game);
            clientHandlers[i].setPlayer(i);
        }
    }

    /**
     * Starts the game with predefined parameters and initializes the game state.
     * Informs clients about the start of the game and schedules the game updates.
     */
    private void startGame() {
        time = 0;
        game = new Game(2, 60, 50, 20, 10, 6, 240); // Predefined game parameters
        broadcastMessage("start");
        log.info("Game started");
        game.startGame();
        scheduler.scheduleAtFixedRate(this, 5, 25, TimeUnit.MILLISECONDS);
        broadcastGame();
    }

    /**
     * Sends a message to all clients connected to the server.
     *
     * @param message message to send
     */
    private synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clientHandlers) {
            client.out.println(message);
        }
    }

    /**
     * Sends the current game state to all clients connected to the server.
     */
    private synchronized void broadcastGame() {
        String gameState = game.toString();
        for (ClientHandler client : clientHandlers) {
            client.out.println(gameState);
        }
    }

    /**
     * Handles the game update logic, updating the game state at regular intervals and
     * sending the updated state to all clients.
     */
    @Override
    public void run() {
        if (!alive) {
            return;
        }

        time++;
        synchronized (game) {
            if (game.isRunning()) {
                if (time % (100 * game.getPlayers()) == 0) {
                    game.generateAction();
                }

                if (time % (40 * game.getPlayers()) == 0) {
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
                        log.info("Game ended");
                    }
                }
            }
            broadcastGame();
        }

        // Check if any client handlers are not alive
        for (ClientHandler ch : clientHandlers) {
            if (!ch.isAlive()) {
                alive = false;
                scheduler.shutdown();
                log.info("Shutting down game server due to client disconnection.");
                break;
            }
        }
    }

    /**
     * Terminates the server, closes the server socket, and ends all client connections.
     */
    public void terminate() {
        alive = false;
        scheduler.shutdown();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.closeConnection();
            }
        } catch (IOException e) {
            log.error("Error during server termination: ", e);
        }
        log.info("Server stopped.");
    }
}
