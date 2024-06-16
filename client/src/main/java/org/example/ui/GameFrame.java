package org.example.ui;

import org.example.Game;
import org.example.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    private MainMenuFrame menuFrame;

    // Constructor for starting a new game
    public GameFrame(int players, MainMenuFrame menuFrame, int width, int height, int obstacles, int food, int size, int length, String gameMode, GameClient gameClient) {
        this.menuFrame = menuFrame;
        this.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel(players);
        if ("remote".equals(gameMode)) {
            // Add remote game initialization logic if needed
        } else {
            GamePanel gamePanel = new GamePanel(players, width, height, obstacles, food, size, length, sidebarPanel, menuFrame, this, gameMode, gameClient);
            this.add(gamePanel, BorderLayout.CENTER);
        }

        this.add(sidebarPanel, BorderLayout.EAST);

        this.setTitle("2nake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuFrame.setVisible(true);
            }
        });
    }

    // Constructor for updating an existing game
    public GameFrame(Game game, GameClient gameClient, MainMenuFrame menuFrame, int player) {
        this.menuFrame = menuFrame;
        this.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel(2);
        GamePanel gamePanel = new GamePanel(menuFrame, sidebarPanel, this, game, gameClient, player);
        gameClient.setGamePanel(gamePanel); // Set the game panel in GameClient
        this.add(gamePanel, BorderLayout.CENTER);

        this.add(sidebarPanel, BorderLayout.EAST);

        this.setTitle("2nake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuFrame.setVisible(true);
            }
        });
    }
}
