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
    private static final int PORT = 12343;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    int time;
    private Timer timer;
    boolean alive = true;
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    Game game;

    public GameServer(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Startuji server...");
            System.out.println(alive);
            while (alive) {

                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();

                System.out.println(clientHandlers.size());
                if(clientHandlers.size() == 2){
                    startGame();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame(){

        time = 0;

        timer = new Timer(5, this);

        game = new Game(1, 60, 50, 20, 5, 6, 60);

        broadcastMessage("start");

        broadcastGame();

        game.startGame();
        timer.start();




    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clientHandlers) {
            client.out.println(message);

        }
    }

    private void broadcastGame(){
        String gameState = game.toString();
        for (ClientHandler client : clientHandlers) {
            client.out.println(gameState);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time++;

        if(game.isRunning()){

            time++;
            if(time % 200*game.getPlayers()*2 == 0){
                game.generateAction();
                System.out.println("generuji jablko");
            }

            if(time % 80*game.getPlayers()*2 == 0){
                game.decreaseTime();
            }
            for(Snake snake : game.getSnakes()){

                if(time/game.getPlayers() % snake.getSpeed() == 0){
                    snake.move();
                    System.out.println("Snake se pohnul");
                    game.checkCollisions();
                    game.checkFood();
                }
                if(game.getTime() == 0){
                    game.setRunning(false);
                }
            }
        }

        broadcastGame();
    }
}
