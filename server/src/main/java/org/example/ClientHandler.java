package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Game game;
    private final GameServer gameServer;

    /**
     * Konstruktor pro vytvoření instance ClientHandler.
     *
     * @param socket     socket pro komunikaci s klientem
     * @param gameServer instance GameServeru pro správu hry
     */
    public ClientHandler(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;
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
                System.out.println(message);
                String[] parts = message.split(" ", 3);
                String command = parts[0];
                int playerId = Integer.parseInt(parts[1]);
                char parameter = 'A'; // Výchozí hodnota, pokud není poskytnuta žádná

                if (parts.length > 2) {
                    parameter = parts[2].charAt(0);
                }

                synchronized (game) {
                    if (command.equals("move")) {
                        log.info(message);
                        game.getSnakes()[playerId].setDirection(parameter);
                    }
                }

                System.out.println("Přijato: " + message);
            }
        } catch (IOException e) {
            log.error("Chyba: " + e);
            game.setRunning(false);
            gameServer.terminate();
        } finally {
            closeConnection();
        }
    }

    /**
     * Nastaví instanci hry, se kterou bude tento handler pracovat.
     *
     * @param game instance hry
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Nastaví číslo hráče a odešle tuto informaci klientovi.
     *
     * @param player číslo hráče
     */
    public void setPlayer(int player) {
        log.info("Nastavení hráče: " + player);
        out.println("player " + player);
    }

    /**
     * Uzavře spojení s klientem.
     */
    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            log.error("Chyba při zavírání socketu: " + e);
        }
    }
}
