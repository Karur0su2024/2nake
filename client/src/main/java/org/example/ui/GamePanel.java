package org.example.ui;

import org.example.GameSettings;
import org.example.Game;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {

    Timer timer;
    Random random;
    Game game;
    int player;
    int players;
    int time;
    int length;

    int obstacles;
    int food;
    int size;
    int width;
    int height;
    int seconds;

    SidebarPanel sidebarPanel;
    MainMenuFrame menuFrame;
    GameFrame gameFrame;

    GamePanel(int players, int width, int height, int obstacles, int food, int size, int length, SidebarPanel sidebarPanel, MainMenuFrame menuFrame, GameFrame gameFrame){
        this.width = width;
        this.height = height;
        this.length = length;
        this.sidebarPanel = sidebarPanel;
        this.gameFrame = gameFrame;
        this.obstacles = obstacles;
        this.food = food;
        this.size = size;
        this.menuFrame = menuFrame;
        this.setPreferredSize(new Dimension(width*GameSettings.UNIT_SIZE, height*GameSettings.UNIT_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.players = players;
        random = new Random();
        setGame();

        player = 0;

    }

    public void setGame(){
        if(timer != null){
            timer.stop();
        }
        seconds = 0;
        timer = new Timer(GameSettings.DELAY, this);
        timer.start();
        game = new Game(timer, this.players, width, height, obstacles, food, size, length, sidebarPanel);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }



    public void draw(Graphics g){
        game.paint(g, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.isRunning()){



            time++;
            if(time % 200 == 0){
                game.generateAction();
            }

            if(time % 80 == 0){
                game.decreaseTime();
                sidebarPanel.setTime();
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
                showGameOverDialog();
            }

        }
        repaint();


    }
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (players == 1) {
                handleSinglePlayerKey(key);
            } else if (players == 2) {
                handleMultiPlayerKey(key);
            }

            if (key == KeyEvent.VK_R) {
                setGame();
                game.restart();
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
                    if (direction != 'D') snake.setDirection('U');
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

    private void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverFrame(this, menuFrame, gameFrame).setVisible(true));
    }

}
