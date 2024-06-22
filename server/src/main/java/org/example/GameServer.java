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

/**
 * Třída GameServer představuje serverovou část hry, která spravuje spojení s klienty,
 * inicializuje a řídí průběh hry a komunikuje s klienty přes síť.
 */
public class GameServer implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final int PORT = 1000;
    private static final Set<ClientHandler> clientHandlers = new HashSet<>();
    private int time;
    private Timer timer;
    private volatile boolean alive = true; // Make alive volatile for thread safety

    private Game game;
    private ServerSocket serverSocket;

    /**
     * Konstruktor pro vytvoření instance GameServeru, který naslouchá na specifikovaném portu.
     * Spouští server a čeká na připojení klientů. Po připojení dvou klientů spouští hru.
     */
    public GameServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("Spouštím server");
            while (alive) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    log.info("Hráč " + clientHandlers.size() + " se připojil do hry");
                    clientHandlers.add(clientHandler);
                    new Thread(clientHandler).start();

                    if (clientHandlers.size() == 2) {
                        startGame();
                        int player = 0;
                        for (ClientHandler clientHandler1 : clientHandlers) {
                            clientHandler1.setGame(game);
                            clientHandler1.setPlayer(player);
                            player++;
                        }
                    }
                } catch (IOException e) {
                    if (alive) {
                        log.error("Server accept error: " + e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Server socket error: " + e);
        } finally {
            terminate();
        }
    }

    /**
     * Spouští hru s přednastavenými parametry a inicializuje herní stav.
     * Informuje klienty o začátku hry a spouští herní časovač pro pravidelnou aktualizaci hry.
     */
    private void startGame() {
        time = 0;
        timer = new Timer(5, this);
        game = new Game(2, 60, 50, 20, 10, 6, 240); // Přednastavené parametry pro hru

        broadcastMessage("start");
        log.info("Začátek hry");
        game.startGame();
        timer.start();
        broadcastGame();
    }

    /**
     * Odesílá zprávu všem klientům připojeným k serveru.
     *
     * @param message zpráva k odeslání
     */
    private synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clientHandlers) {
            client.out.println(message);
        }
    }

    /**
     * Odesílá aktuální stav hry všem klientům připojeným k serveru.
     */
    private synchronized void broadcastGame() {
        String gameState = game.toString();
        for (ClientHandler client : clientHandlers) {
            client.out.println(gameState);
        }
    }

    /**
     * Metoda actionPerformed pro obsluhu události akce časovače.
     * Aktualizuje stav hry v pravidelných intervalech a odesílá aktualizovaný stav všem klientům.
     *
     * @param e událost akce
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        time++;
        synchronized (game) {
            if (game.isRunning()) {
                if (time % 100 * game.getPlayers() == 0) {
                    game.generateAction();
                }

                if (time % 40 * game.getPlayers() == 0) {
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
                        log.info("Konec hry");
                    }
                }
            }
            broadcastGame();
        }
    }

    /**
     * Ukončí běh serveru, uzavře socket a ukončí všechna spojení s klienty.
     */
    public void terminate() {
        alive = false;
        timer.stop();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.closeConnection();
            }
        } catch (IOException e) {
            log.error("Error during server termination: " + e);
        }
        log.info("Server stopped.");
    }

    /**
     * Metoda main pro spuštění instance GameServeru.
     *
     * @param args argumenty příkazové řádky (nejsou použity)
     */
    public static void main(String[] args) {
        GameServer server = new GameServer();

        // Přidání hooku pro zachycení ukončení aplikace
        Runtime.getRuntime().addShutdownHook(new Thread(server::terminate));
    }
}
