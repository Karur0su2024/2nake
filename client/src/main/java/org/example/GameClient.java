package org.example;

import org.example.objects.Snake;
import org.example.gui.GameWindow;
import org.example.gui.GamePanel;
import org.example.gui.JoinServerFrame;
import org.example.gui.MainMenu;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Třída reprezentující klienta hry, který komunikuje se serverem.
 * Zpracovává připojení k serveru, odesílání a příjem zpráv, aktualizaci stavu hry a UI manipulaci.
 */
public class GameClient {
    private static final Logger log = LoggerFactory.getLogger(GameClient.class);


    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1000;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GamePanel gamePanel;
    private int player = 99;
    private boolean started = false;
    private GuiHandler gui;

    private String name;

    private GameLogicHandler gameLogic;

    /**
     * Konstruktor pro inicializaci klienta hry.
     *
     */
    public GameClient(GuiHandler gui, String name) {
        this.gui = gui;
        this.name = name;
        this.gameLogic = new GameLogicHandler(gui);

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new ServerListener()).start();
            gui.getJoinServerFrame().setMessage("Počet hráčů: 1/2");
            gui.getJoinServerFrame().hideJoinButton();
            sendMessage("join " + name);

            //log.info("Client connected to server at {}:{}", SERVER_ADDRESS, SERVER_PORT);
        } catch (IOException e) {
            gui.getJoinServerFrame().setMessage("Server není dostupný");
            log.error("Failed to connect to server", e);
        }
    }

    public void startServer(){

    }

    /**
     * Nastaví herní panel.
     *
     * @param gamePanel herní panel
     */
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Odesílá zprávu o pohybu hada na server.
     *
     * @param direction směr pohybu hada
     */
    public void sendMove(char direction) {
        out.println("move " + player + " " + direction);
        log.info("Sent move command to server: move {} {}", player, direction);
    }

    public void sendMessage(String message){
        out.println(message);
    }


    /**
     * Odesílá zprávu o hráčově akci na server.
     *
     * @param player číslo hráče
     * @param key    klíčová událost klávesy
     */
    public void sendPMessage(int player, int key) {


        //Snake snake = gamePanel.getGameLogic().getGame().getSnakes()[player];
        /*char direction = snake.getDirection();

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
        }*/
    }

    /**
     * Aktualizuje stav hry na základě přijaté zprávy.
     *
     */
    public void updateGameState(String game) {
        this.gameLogic.refreshGame(Game.fromString(game));

        gui.getGameFrame().getGamePanel().repaint();
        //this.gameLogic.updateSidebar();
    }

    /**
     * Vnitřní třída pro naslouchání serverovým zprávám.
     */
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                String[] parts;
                String command;

                while ((message = in.readLine()) != null) {
                    parts = message.split(" ", 3);
                    command = parts[0];

                    if (command.equals("start")) {
                        //started = true;
                        gui.getJoinServerFrame().dispose();
                        gui.toggleMainMenu();


                        Game game = Game.fromString(parts[1]);

                        gameLogic.setGame(game);


                        gui.setGameFrame(new GameWindow(gameLogic, GameClient.this, gui));

                    }

                    if (command.equals("game")){
                        updateGameState(parts[1]);
                    }

                    if(command.equals("message")){
                        System.out.println(message);
                    }

                    if(command.equals("stop")){
                        gui.getGameFrame().dispose();
                        gui.toggleMainMenu();
                    }


                    if (started && gui.getGameFrame() == null && player != 99) {
                        gameLogic.setGame(Game.fromString(message));
                        //gui.setGameFrame(new GameWindow(gameLogic, GameClient.this, gui, player));
                    }



                    if (started && gui.getGameFrame() != null) {
                        updateGameState(message);
                    }


                    /*String[] parts = message.split(" ", 3);
                    if (parts.length > 1) {
                        String command = parts[0];
                        int playerId = Integer.parseInt(parts[1]);

                        if (command.equals("player")) {
                            player = playerId;
                            log.info("Assigned player ID: {}", player);
                        }
                    }*/
                }
            } catch (IOException e) {
                log.error("Error reading server message", e);
            }
        }
    }

    public GameLogicHandler getGameLogic() {
        return gameLogic;
    }

    public String getName() {
        return name;
    }
}
