package org.example.ui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        // Set title and default close operation
        setTitle("Snake Game Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the frame

        // Create a panel for the main menu
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(5, 1, 20, 10));

        // Create buttons
        JButton start1player = new JButton("1 player game");
        JButton start2players = new JButton("2 players local Game");
        JButton start2playersServer = new JButton("2 players game over server");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        start1player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                //setVisible(false);
                new SettingsFrame(MainMenuFrame.this, 1);
                //new GameFrame(1, MainMenuFrame.this, 45, 30); // Open the game frame
            }
        });

        start2players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                new SettingsFrame(MainMenuFrame.this, 2);
                //new GameFrame(2, MainMenuFrame.this, 45, 30);  // Open the game frame
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show instructions
                JOptionPane.showMessageDialog(null, "Use arrow keys to move the snake.\nEat the food to grow.\nAvoid hitting the walls or the snake's own body.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
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
        panel.add(start2playersServer);
        panel.add(instructionsButton);
        panel.add(exitButton);


        // Add panel to the frame
        add(panel);
    }


}
