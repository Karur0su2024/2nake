package org.example;

import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The GameServer class represents the server part of the game, managing client connections,
 * initializing and controlling the game flow, and communicating with clients over the network.
 */
public class GameServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(GameServer.class);
    private final List<ClientHandler> clientHandlers;
    private boolean alive = true;
    private int id;
    private int time;

    private int reset;

    private GameLogicHandler gameLogic;
    private ServerSocket serverSocket;
    private final ScheduledExecutorService scheduler;

    /**
     * Constructor to create an instance of GameServer that listens on the specified port.
     * Starts the server and waits for client connections. After two clients are connected, starts the game.
     *
     * @param clientHandlers array of ClientHandler instances representing connected clients
     */
    public GameServer(List<ClientHandler> clientHandlers, ServerSocket serverSocket, int id) {
        this.alive = true;
        this.reset = 0;
        this.clientHandlers = clientHandlers;
        this.serverSocket = serverSocket;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.id = id;

        for(ClientHandler ch : clientHandlers){
            ch.setServer(this);
            ch.setMainServer(null);
        }

        startGame();

    }

    /**
     * Initializes the game and sets up clients with their respective game and player index.
     */
    private void initializeClients() {
        for (ClientHandler clientHandler : clientHandlers) {

            clientHandler.out.println("start " + gameLogic.getGame().toString());

            //clientHandlers[i].sendGame(game);

        }
    }

    /**
     * Starts the game with predefined parameters and initializes the game state.
     * Informs clients about the start of the game and schedules the game updates.
     */
    private void startGame() {
        time = 0;
        gameLogic = new GameLogicHandler(new Game(new GamePlan(45, 30), new Obstacle[60], new Food[6], 180), 6, 2);

        for(ClientHandler ch : clientHandlers){
            gameLogic.addSnake(ch.getSnake());
            System.out.println(ch.getSnake().toString());
        }


        gameLogic.startGame();

        initializeClients();

        scheduler.scheduleAtFixedRate(this, 0, 200, TimeUnit.MILLISECONDS);
        broadcastGame();



        log.info("Game started");
    }

    /**
     * Sends the current game state to all clients connected to the server.
     */
    private synchronized void broadcastGame() {
        String gameState = gameLogic.getGame().toString();
        for (ClientHandler client : clientHandlers) {
            client.out.println("game " + gameState);
        }
    }

    /**
     * Sends the current game state to all clients connected to the server.
     */
    private synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clientHandlers) {
            if(client.isAlive()){
                client.out.println(message);
            }
        }
    }

    /**
     * Handles the game update logic, updating the game state at regular intervals and
     * sending the updated state to all clients.
     */
    @Override
    public void run() {

            while(alive){
                synchronized (gameLogic.getGame()) {
                while(gameLogic.isRunning()){
                    time++;

                    if (time % 10000 == 0) {
                        gameLogic.generateAction();
                    }

                    if (time % 4000 == 0) {
                        gameLogic.getGame().decreaseTime();
                    }

                    for (Snake snake : gameLogic.getGame().getSnakes()) {
                        if (time % (snake.getSpeed()*10) == 0) {
                            snake.move();
                            gameLogic.checkCollisions();
                            gameLogic.checkFood();

                        }
                        if (gameLogic.getGame().getTime() == 0) {
                            gameLogic.setRunning(false);
                            gameLogic.setEnded(true);
                        }
                    }
                    broadcastGame();
                }
                if(gameLogic.isEnded()){
                    broadcastMessage("end");
                    gameLogic.setEnded(false);
                }

            }
        }

    }

    public void reset(){
        reset++;
        if(reset == clientHandlers.size()){
            gameLogic.restart();
            reset = 0;
        }
    }

    /**
     * Terminates the server, closes the server socket, and ends all client connections.
     */
    public void terminate() {
        alive = false;
        scheduler.shutdown();
        broadcastMessage("stop");
        log.info("Server stopped.");
    }


    public GameLogicHandler getGameLogic() {
        return gameLogic;
    }

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}
