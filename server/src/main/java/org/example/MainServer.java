package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Třída GameServer představuje serverovou část hry, která spravuje spojení s klienty,
 * inicializuje a řídí průběh hry a komunikuje s klienty přes síť.
 */
public class MainServer {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final int PORT = 1000;
    private static final Set<ClientHandler> clientHandlers = new HashSet<>();
    private static final Set<GameServer> servers = new HashSet<>();
    private volatile boolean alive = true; // Make alive volatile for thread safety
    private ServerSocket serverSocket;

    /**
     * Konstruktor pro vytvoření instance GameServeru, který naslouchá na specifikovaném portu.
     * Spouští server a čeká na připojení klientů. Po připojení dvou klientů spouští hru.
     */
    public MainServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("Starting server...");

            while (alive) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);

                    clientHandlers.add(clientHandler);
                    new Thread(clientHandler).start();

                    if (clientHandlers.size() % 2 == 0) {
                        List<ClientHandler> ch = new ArrayList<>();
                        for (ClientHandler clientHandler1 : clientHandlers) {
                            ch.add(clientHandler1);
                        }

                        GameServer gm = new GameServer(ch, serverSocket, servers.size());
                        new Thread(gm).start();

                        clientHandlers.clear();
                    }
                } catch (IOException _) {

                }
            }
        } catch (IOException e) {
            log.error("Server socket error: " + e);
        } finally {
            terminate();
        }
    }

    /**
     * Ukončí běh serveru, uzavře socket a ukončí všechna spojení s klienty.
     */
    public void terminate() {
        alive = false;
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

    public Set<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}
