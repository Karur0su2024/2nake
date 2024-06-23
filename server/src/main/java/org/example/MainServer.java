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
            log.info("Spouštím server");
            while (alive) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);


                    log.info("Hráč " + clientHandlers.size() + " se připojil do hry");
                    clientHandlers.add(clientHandler);
                    new Thread(clientHandler).start();

                    System.out.println(clientHandlers.size());
                    if (clientHandlers.size() == 2) {
                        int i = 0;
                        ClientHandler[] ch = new ClientHandler[2];
                        for (ClientHandler clientHandler1 : clientHandlers) {
                            ch[i] = clientHandler1;
                            i++;
                        }

                        GameServer gm = new GameServer(ch);
                        new Thread(gm).start();
                        servers.add(gm);

                        clientHandlers.clear();

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
}
