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
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame

        // Create a panel for the main menu
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create buttons
        JButton startButton = new JButton("Start Game");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                dispose(); // Close the main menu
                new GameFrame(); // Open the game frame
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
        panel.add(startButton);
        panel.add(instructionsButton);
        panel.add(exitButton);

        // Add panel to the frame
        add(panel);
    }


}
