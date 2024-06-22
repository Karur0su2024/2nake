package org.example;

import com.google.gson.Gson;
import org.example.objects.Snake;
import org.example.ui.GameFrame;
import org.example.ui.GamePanel;
import org.example.ui.JoinServerFrame;
import org.example.ui.MainMenuFrame;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Game game; // Reference na stav hry
    public MainMenuFrame menuFrame;
    private GameFrame gameFrame;
    public JoinServerFrame joinServerFrame;

    /**
     * Konstruktor pro inicializaci klienta hry.
     *
     * @param menuFrame       hlavní menu hry
     * @param joinServerFrame okno pro připojení k serveru
     */
    public GameClient(MainMenuFrame menuFrame, JoinServerFrame joinServerFrame) {
        this.menuFrame = menuFrame;
        this.joinServerFrame = joinServerFrame;
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new ServerListener()).start();
            joinServerFrame.setMessage("Počet hráčů: 1/2");
            joinServerFrame.hideJoinButton();

            log.info("Client connected to server at {}:{}", SERVER_ADDRESS, SERVER_PORT);
        } catch (IOException e) {
            joinServerFrame.setMessage("Server není dostupný");
            log.error("Failed to connect to server", e);
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

    /**
     * Odesílá zprávu o hráčově akci na server.
     *
     * @param player číslo hráče
     * @param key    klíčová událost klávesy
     */
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

    /**
     * Aktualizuje stav hry na základě přijaté zprávy.
     *
     * @param stateMessage přijatá zpráva se stavem hry
     */
    public void updateGameState(String stateMessage) {
        Game updatedGame = Game.fromString(stateMessage);
        this.game = updatedGame;

        if (gamePanel != null) {
            gamePanel.updateGame(this.game);
        }
    }

    /**
     * Vnitřní třída pro naslouchání serverovým zprávám.
     */
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    if (started && gameFrame != null) {
                        updateGameState(message);
                    }
                    if (started && gameFrame == null && player != 99) {
                        game = Game.fromString(message);
                        gameFrame = new GameFrame(game, GameClient.this, menuFrame, player);
                    }
                    if (message.equals("start")) {
                        started = true;
                        joinServerFrame.dispose();
                        menuFrame.setVisible(false);
                    }
                    String[] parts = message.split(" ", 3);
                    if (parts.length > 1) {
                        String command = parts[0];
                        int playerId = Integer.parseInt(parts[1]);

                        if (command.equals("player")) {
                            player = playerId;
                            log.info("Assigned player ID: {}", player);
                        }
                    }
                }
            } catch (IOException e) {
                log.error("Error reading server message", e);
            }
        }
    }
}
