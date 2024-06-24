package org.example;

import org.example.gui.*;
import org.example.objects.Snake;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

            gui.getJoinServerFrame().addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    sendMessage("left " + name);
                }
            });

            //log.info("Client connected to server at {}:{}", SERVER_ADDRESS, SERVER_PORT);
        } catch (IOException e) {
            gui.getJoinServerFrame().setMessage("Server není dostupný");
            //log.error("Failed to connect to server", e);
        }
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
                        socket.close();
                        gui.closeGameWindowToMainMenu();
                        return;
                    }

                    if(command.equals("end")){
                        gui.setGameOverScreen(new GameOverScreen(gui, GameClient.this));
                        gui.getGameOverScreen().setVisible(true);

                    }


                    if (started && gui.getGameFrame() == null && player != 99) {
                        gameLogic.setGame(Game.fromString(message));
                        //gui.setGameFrame(new GameWindow(gameLogic, GameClient.this, gui, player));
                    }



                    if (started && gui.getGameFrame() != null) {
                        updateGameState(message);
                    }


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

    public void stop(){
        gui.getGameFrame().dispose();
        gui.toggleMainMenu();
    }
}
