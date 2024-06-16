package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private Socket socket;
    public PrintWriter out;
    private BufferedReader in;
    private Game game;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = in.readLine()) != null) {
                // Process messages received from clients
                System.out.println(message);
                String[] parts = message.split(" ", 3);
                String command = parts[0];
                int playerId = Integer.parseInt(parts[1]);
                char parameter = 'A'; // Default value, if none provided
                if (parts.length > 2) {
                    parameter = parts[2].charAt(0);
                }

                synchronized (game) {
                    if (command.equals("move")) {
                        log.info(message);
                        game.getSnakes()[playerId].setDirection(parameter);
                    }
                }

                System.out.println("Received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(int player){
        out.println("player " + player);
    }
}
