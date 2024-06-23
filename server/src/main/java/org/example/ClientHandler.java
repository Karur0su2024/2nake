package org.example;

import org.example.objects.Snake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

/**
 * Třída ClientHandler zpracovává komunikaci s jedním klientem na serverové straně hry.
 * Implementuje rozhraní Runnable pro možnost běhu v samostatném vlákně.
 */
class ClientHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private final Socket socket;
    public PrintWriter out;
    private BufferedReader in;

    private GameServer server;
    private boolean alive;
    private String name;
    private Snake OGsnake;
    private Snake snake;

    /**
     * Konstruktor pro vytvoření instance ClientHandler.
     *
     * @param socket     socket pro komunikaci s klientem
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.snake = new Snake("newSnake");
        this.alive = true;
    }

    /**
     * Metoda pro spuštění vlákna pro zpracování komunikace s klientem.
     * Přijímá zprávy od klienta, zpracovává je a předává je herní logice.
     * V případě chyby ukončuje spojení s klientem a informuje GameServer o ukončení hry.
     */
    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = in.readLine()) != null) {
                // Zpracování zpráv přijatých od klienta
                String[] parts = message.split(" ", 3);
                String command = parts[0];
                String player = parts[1];
                String parameter;

                if(command.equals("join")){
                    name = player;
                    snake.setName(name);

                    //snake = new Snake(OGsnake);
                    log.info(name + " joined the game");

                }

                if(command.equals("move")){
                    log.info(message);
                    parameter = parts[2];

                    char dir = parameter.charAt(0);


                    switch (dir) {
                        case 'R':
                            if(snake.getDirection() != 'L') snake.setDirection('R');
                            break;
                        case 'L':
                            if(snake.getDirection() != 'R') snake.setDirection('L');
                            break;
                        case 'U':
                            if(snake.getDirection() != 'D') snake.setDirection('U');
                            break;
                        case 'D':
                            if(snake.getDirection() != 'U') snake.setDirection('D');
                            break;
                    }

                    System.out.println(snake.toString());
                }

                if(command.equals("left")){
                    server.terminate();
                }






                /*synchronized (game) {
                    if (command.equals("move")) {
                        //game.getSnakes()[playerId].setDirection(parameter);
                    }
                }*/




            }
        } catch (IOException e) {
            log.error("Chyba: " + e);
            //game.setRunning(false);
            server.terminate();
        } finally {
            closeConnection();
        }
    }

    public void sendGame(Game game){
        sendMessage("game " + game);
    }

    public void sendMessage(String message){
        out.println(message);
    }


    /**
     * Uzavře spojení s klientem.
     */
    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                alive = false;
                socket.close();

            }
        } catch (IOException e) {
            log.error("Chyba při zavírání socketu: " + e);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setServer(GameServer server) {
        this.server = server;
    }

    public Snake getSnake() {
        return snake;
    }

    public void resetSnake(){
        this.snake = new Snake(OGsnake);
    }

    public String getName() {
        return name;
    }
}
