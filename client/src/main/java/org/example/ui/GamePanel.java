package org.example.ui;

import org.example.*;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.example.Game;


public class GamePanel extends JPanel {
    Game game;
    int players;

    int player;
    SidebarPanel sidebarPanel;
    MainMenuFrame menuFrame;
    GameFrame gameFrame;
    String gameMode;

    GamePanel(int players, int width, int height, int obstacles, int food, int size, int length, SidebarPanel sidebarPanel, MainMenuFrame menuFrame, GameFrame gameFrame, String gameMode){
        this.menuFrame = menuFrame;
        this.gameFrame = gameFrame;
        this.players = players;
        this.setPreferredSize(new Dimension(width*GameSettings.UNIT_SIZE, height*GameSettings.UNIT_SIZE));
        this.setFocusable(true);


        this.gameMode = gameMode;
        if ("Remote".equals(this.gameMode)) {
            GameClient gameClient = new GameClient();
            game = new Game(players, width, height, obstacles, food, size, length, sidebarPanel, new RemoteGameHandler(gameClient));
        } else {
            game = new Game(players, width, height, obstacles, food, size, length, sidebarPanel, new LocalGameHandler(this));
        }

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

            } else {
                if (players == 1) {
                    handleSinglePlayerKey(key);
                } else if (players == 2) {
                    handleMultiPlayerKey(key);
                }

                if (key == KeyEvent.VK_R) {
                    game.restart();
                }
            }

        }

        private void handleSinglePlayerKey(int key) {
            Snake snake = game.getSnakes()[0];

            char direction = snake.getDirection();

            switch (key) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction != 'R') snake.setDirection('L');
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction != 'L') snake.setDirection('R');
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction != 'D'){
                        snake.setDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction != 'U') snake.setDirection('D');
                    break;
            }
        }

        private void handleMultiPlayerKey(int key) {
            Snake snake1 = game.getSnakes()[0];
            Snake snake2 = game.getSnakes()[1];

            switch (key) {
                case KeyEvent.VK_A:
                    if (snake1.getDirection() != 'R') snake1.setDirection('L');
                    break;
                case KeyEvent.VK_D:
                    if (snake1.getDirection() != 'L') snake1.setDirection('R');
                    break;
                case KeyEvent.VK_W:
                    if (snake1.getDirection() != 'D') snake1.setDirection('U');
                    break;
                case KeyEvent.VK_S:
                    if (snake1.getDirection() != 'U') snake1.setDirection('D');
                    break;
                case KeyEvent.VK_LEFT:
                    if (snake2.getDirection() != 'R') snake2.setDirection('L');
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake2.getDirection() != 'L') snake2.setDirection('R');
                    break;
                case KeyEvent.VK_UP:
                    if (snake2.getDirection() != 'D') snake2.setDirection('U');
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake2.getDirection() != 'U') snake2.setDirection('D');
                    break;
            }
        }
    }

    public void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverFrame(this, menuFrame, gameFrame).setVisible(true));
    }
}