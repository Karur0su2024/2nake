package org.example;

import org.example.objects.Snake;
import org.example.ui.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LocalGameHandler implements GameHandler, ActionListener {
    private Game game;
    Timer timer;
    int time = 0;
    Random random;


    GamePanel gamePanel;

    public LocalGameHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        random = new Random();
    }

    @Override
    public void initializeGame(Game game) {
        this.game = game;
        game.initializeGame();


        if(timer != null){
            timer.stop();
        }
        timer = new Timer(GameSettings.DELAY, this);
        timer.start();

    }

    @Override
    public void sendPlayerAction(String action) {
        // Directly handle player action locally

    }

    @Override
    public void receiveGameState(String gameState) {
        // Not needed for local game
    }

    @Override
    public void updateGame() {
        if(game.isRunning()){

            time++;
            if(time % 200 == 0){
                game.generateAction();
            }

            if(time % 80 == 0){
                game.decreaseTime();
            }
            for(Snake snake : game.getSnakes()){

                if(time % snake.getSpeed() == 0){
                    snake.move();
                    game.checkCollisions();
                    game.checkFood();
                }
                if(game.getTime() == 0){
                    game.setRunning(false);
                }
            }

            if(!game.isRunning()) {
                gamePanel.showGameOverDialog();
            }

        }
        gamePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
    }
}