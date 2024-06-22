package org.example.gui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        // Set title and default close operation
        setTitle("2nake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Create a panel for the main menu
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(5, 1, 20, 10));

        // Create buttons
        JButton start1player = new JButton("Hra pro jednoho hráče");
        JButton start2players = new JButton("Hra pro dva hráče");
        JButton startServer = new JButton("Servery");
        JButton instructionsButton = new JButton("Nápověda");
        JButton exitButton = new JButton("Konec");

        // Add action listeners
        start1player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                new SetGameWindow(MainMenuFrame.this, 1);
            }
        });

        start2players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                new SetGameWindow(MainMenuFrame.this, 2);
            }
        });

        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                new JoinServerFrame(MainMenuFrame.this);
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show instructions
                JOptionPane.showMessageDialog(null, "Pro pohyb hada použijte klávesy šipek nebo ASDW.\nModré jídlo prodlužuje hada červené ho zkracuje.\nPokud nabouráte do překážky nebo těla hada prohráváte.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the game
                System.exit(0);
            }
        });

        // Add buttons to the panel
        panel.add(start1player);
        panel.add(start2players);
        panel.add(startServer);
        panel.add(instructionsButton);
        panel.add(exitButton);

        // Add panel to the frame
        add(panel);
    }


}
