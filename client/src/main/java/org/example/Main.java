package org.example;

import org.example.ui.MainMenuFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        log.info("Spouštím program");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuFrame().setVisible(true);
            }
        });
    }

}