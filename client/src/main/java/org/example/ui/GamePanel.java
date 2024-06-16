package org.example.ui;

import org.example.*;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.example.Game;


public class GamePanel extends JPanel {
    public Game game;
    int players;

    int player;
    SidebarPanel sidebarPanel;
    MainMenuFrame menuFrame;
    GameFrame gameFrame;
    String gameMode;

    GameHandler gameHandler;

    GamePanel(int players, int width, int height, int obstacles, int food, int size, int length, SidebarPanel sidebarPanel, MainMenuFrame menuFrame, GameFrame gameFrame, String gameMode, GameClient gameClient){
        this.menuFrame = menuFrame;
        this.gameFrame = gameFrame;
        this.players = players;
        if(gameClient != null){
            gameClient.setGamePanel(this);
        }

        this.setPreferredSize(new Dimension(width*GameSettings.UNIT_SIZE, height*GameSettings.UNIT_SIZE));
        this.setFocusable(true);



        this.gameMode = gameMode;
        if ("remote".equals(this.gameMode)) {
            gameHandler = new RemoteGameHandler(gameClient);
        } else {
            this.gameHandler = new LocalGameHandler(this);

        }
        game = new Game(players, width, height, obstacles, food, size, length, sidebarPanel, gameHandler);
        this.addKeyListener(new MyKeyAdapter());



    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }



    public void draw(Graphics g){
        game.paint(g, this);
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ("Remote".equals(gameMode)) {

                gameHandler.sendPlayerAction(player, key);

            } else {
                gameHandler.sendPlayerAction(0, key);

                if (key == KeyEvent.VK_R) {
                    game.restart();
                }
            }

        }
    }

    public void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverFrame(this, menuFrame, gameFrame).setVisible(true));
    }
}