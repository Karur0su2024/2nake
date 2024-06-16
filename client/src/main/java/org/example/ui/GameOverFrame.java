package org.example.ui;

import javax.swing.*;
import org.example.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverFrame extends JFrame implements ActionListener {

    public GameOverFrame(GamePanel gamePanel, MainMenuFrame mainMenuFrame, GameFrame gameFrame) {
        setTitle("Game Over");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Game Over", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.game.restart();
                dispose();
            }
        });
        buttonPanel.add(restartButton);

        JButton settingsButton = new JButton("Settings");

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new SettingsFrame(mainMenuFrame, gamePanel.game.getPlayers());
                gameFrame.dispose();
                dispose();

            }
        });

        buttonPanel.add(settingsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.exit(0);  // Exit the application
                gameFrame.dispose();
                dispose();
                mainMenuFrame.setVisible(true);
            }
        });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
