package org.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Spouštím server");
        GameServer server = new GameServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::terminate));

    }
}