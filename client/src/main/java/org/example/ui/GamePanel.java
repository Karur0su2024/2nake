package org.example.ui;

import org.example.*;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private Game game;
    private GameClient gameClient;
    private int players;
    private SidebarPanel sidebarPanel;
    private MainMenuFrame menuFrame;
    private GameFrame gameFrame;
    private String gameMode;
    private GameHandler gameHandler;

    private int player;

    public GamePanel(int players, int width, int height, int obstacles, int food, int size, int length, SidebarPanel sidebarPanel, MainMenuFrame menuFrame, GameFrame gameFrame, String gameMode, GameClient gameClient) {
        this.players = players;
        this.sidebarPanel = sidebarPanel;
        this.menuFrame = menuFrame;
        this.gameFrame = gameFrame;
        this.gameClient = gameClient;
        this.gameMode = gameMode;

        this.setPreferredSize(new Dimension(width * GameSettings.UNIT_SIZE, height * GameSettings.UNIT_SIZE));
        this.setFocusable(true);

        // Initialize the game
        this.gameHandler = new LocalGameHandler(this);
        this.game = new Game(players, width, height, obstacles, food, size, length);
        this.game.setGameHandler(gameHandler);
        this.game.setSidebarPanel(sidebarPanel);

        // Start the game
        this.game.startGame();

        // Add key listener for player input
        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);  // Ensure that focus traversal keys are not processed

        // Request focus for key events
        this.requestFocusInWindow();

        // Debug print the initial game state
        System.out.println(game.toString());
    }

    public GamePanel(MainMenuFrame menuFrame, SidebarPanel sidebarPanel, GameFrame gameFrame, Game game, GameClient gameClient, int player) {
        this.menuFrame = menuFrame;
        this.sidebarPanel = sidebarPanel;
        this.gameFrame = gameFrame;
        this.game = game;
        this.gameClient = gameClient;
        this.gameHandler = new RemoteGameHandler(gameClient);
        this.player = player;

        this.game.setGameHandler(gameHandler);
        this.game.setSidebarPanel(sidebarPanel);

        this.setPreferredSize(new Dimension(game.getWidth() * GameSettings.UNIT_SIZE, game.getHeight() * GameSettings.UNIT_SIZE));
        this.setFocusable(true);

        // Add key listener for player input
        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);  // Ensure that focus traversal keys are not processed

        // Request focus for key events
        this.requestFocusInWindow();

        // Debug print the initial game state
        System.out.println(game.toString());
    }

    public void updateGame(Game game) {
        this.game = game;
        for(Snake snake : game.getSnakes())
        System.out.println(snake.getX()[0] + " " + snake.getY()[0]);
        repaint();  // Request repaint to update the game graphics
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the game components using game state
        if (game != null) {
            game.paint(g, this);
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ("remote".equals(gameMode)) {
                // Handle remote player actions
                gameHandler.sendPlayerAction(player, key);  // Send player actions to server
            } else {
                // Handle local player actions
                gameHandler.sendPlayerAction(0, key);  // Send player actions to local game
                if (key == KeyEvent.VK_R) {
                    game.restart();  // Restart game on 'R' key press
                }
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverFrame(this, menuFrame, gameFrame).setVisible(true));
    }
}
