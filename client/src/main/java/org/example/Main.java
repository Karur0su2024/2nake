package org.example;

import org.example.ui.MainMenuFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Create and display the main menu
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuFrame().setVisible(true);
            }
        });
    }

}