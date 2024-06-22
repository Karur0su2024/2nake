package org.example;

import org.example.objects.Snake;
import org.example.gui.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class LocalGameHandler implements GameHandler, ActionListener {

    private Game game;
    private Timer timer;
    private int time = 0;
    private Random random;
    private GamePanel gamePanel;

    public LocalGameHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        random = new Random();
    }

    @Override
    public void initializeGame(Game game) {
        this.game = game;
        game.initializeGame();

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(GameSettings.DELAY, this);
        timer.start();
    }

    @Override
    public void sendPlayerAction(int player, int key) {
        Snake snake = game.getSnakes()[player];
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
                if (direction != 'D') snake.setDirection('U');
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != 'U') snake.setDirection('D');
                break;
        }
    }

    @Override
    public void receiveGameState(String gameState) {
        // Not needed for local game
    }

    @Override
    public void updateGame() {
        if (game.isRunning()) {
            time++;
            if (time % 200 == 0) {
                game.generateAction();
            }

            if (time % 80 == 0) {
                game.decreaseTime();
            }

            for (Snake snake : game.getSnakes()) {
                if (time % snake.getSpeed() == 0) {
                    snake.move();
                    game.checkCollisions();
                    game.checkFood();
                }
                if (game.getTime() == 0) {
                    game.setRunning(false);
                }
            }

            if (!game.isRunning()) {
                gamePanel.showGameOverDialog();
            }
        }
        gamePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.isRunning()) {
            updateGame();
        }
    }
}
