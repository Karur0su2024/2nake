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

    int width;
    int height;
    int seconds;

    SidebarPanel sidebarPanel;

    GamePanel(int players, int width, int height, int obsactles, int food, int size, int length, SidebarPanel sidebarPanel){
        this.width = width;
        this.height = height;
        this.length = length;
        this.sidebarPanel = sidebarPanel;
        this.setPreferredSize(new Dimension(width*GameSettings.UNIT_SIZE, height*GameSettings.UNIT_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        random = new Random();
        setGame();
        this.players = players;
        player = 0;
        game = new Game(timer, this.players, width, height, obsactles, food, size, length, sidebarPanel);
    }

    public void setGame(){
        if(timer != null){
            timer.stop();
        }
        seconds = 0;
        timer = new Timer(GameSettings.DELAY, this);
        timer.start();
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
        if(game.running){



            time++;
            if(time % 200 == 0){
                game.generateAction();
            }

            if(time % 80 == 0){
                game.length--;
                sidebarPanel.setTime();
            }
            for(Snake snake : game.snakes){

                if(time % snake.getSpeed() == 0){
                    snake.move();
                    game.checkCollisions();
                    game.checkFood();
                }
                if(game.length == 0){
                    game.running = false;
                }
            }

        }
        repaint();


    }
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            if(players == 1){
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                    if(game.snakes[player].getDirection() != 'R'){
                        game.snakes[player].setDirection('L');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                    if(game.snakes[player].getDirection() != 'L'){
                        game.snakes[player].setDirection('R');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                    if(game.snakes[player].getDirection() != 'D'){
                        game.snakes[player].setDirection('U');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                    if(game.snakes[player].getDirection() != 'U'){
                        game.snakes[player].setDirection('D');
                        return;
                    }
                }
            }
            if(players == 2){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    if(game.snakes[0].getDirection() != 'R'){
                        game.snakes[0].setDirection('L');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    if(game.snakes[0].getDirection() != 'L'){
                        game.snakes[0].setDirection('R');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_W){
                    if(game.snakes[0].getDirection() != 'D'){
                        game.snakes[0].setDirection('U');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    if(game.snakes[0].getDirection() != 'U'){
                        game.snakes[0].setDirection('D');
                        return;
                    }
                }

                //Player 2
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    if(game.snakes[1].getDirection() != 'R'){
                        game.snakes[1].setDirection('L');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    if(game.snakes[1].getDirection() != 'L'){
                        game.snakes[1].setDirection('R');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    if(game.snakes[1].getDirection() != 'D'){
                        game.snakes[1].setDirection('U');
                        return;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    if(game.snakes[1].getDirection() != 'U'){
                        game.snakes[1].setDirection('D');
                        return;
                    }
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_R){
                setGame();
                game.restart();

            }
        }
    }

}
