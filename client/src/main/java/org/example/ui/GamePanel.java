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


    int width;
    int height;
    int seconds;

    GamePanel(int players, int width, int height){
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width*GameSettings.UNIT_SIZE, height*GameSettings.UNIT_SIZE+200));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        random = new Random();
        setGame();
        this.players = players;
        player = 0;
        game = new Game(timer, this.players, width, height);
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
        g.setColor(Color.black);
        g.fillRect(0, height*GameSettings.UNIT_SIZE, width*GameSettings.UNIT_SIZE, 200);
        g.setColor(Color.CYAN);
        g.setFont(new Font("Roboto", Font.BOLD, 40));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Time: " + seconds, (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Time"))/2, height*GameSettings.UNIT_SIZE+100);
        g.drawString("Rychlost: " + (20 / (20 - (game.snakes[0].getBodyParts() / 3))), (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Time"))/2, height*GameSettings.UNIT_SIZE+150);

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
            if(time % 250 == 0){
            }

            if(time % 90 == 0){
                seconds++;
            }
            if(time % (20 - (int)(game.snakes[0].getBodyParts() / 3)) == 0){
                game.move();
                game.checkCollisions();
                game.checkFood();
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
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                    if(game.snakes[player].getDirection() != 'L'){
                        game.snakes[player].setDirection('R');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                    if(game.snakes[player].getDirection() != 'D'){
                        game.snakes[player].setDirection('U');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                    if(game.snakes[player].getDirection() != 'U'){
                        game.snakes[player].setDirection('D');
                    }
                }
            }
            if(players == 2){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    if(game.snakes[0].getDirection() != 'R'){
                        game.snakes[0].setDirection('L');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    if(game.snakes[0].getDirection() != 'L'){
                        game.snakes[0].setDirection('R');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_W){
                    if(game.snakes[0].getDirection() != 'D'){
                        game.snakes[0].setDirection('U');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    if(game.snakes[0].getDirection() != 'U'){
                        game.snakes[0].setDirection('D');
                    }
                }

                //Player 2
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    if(game.snakes[1].getDirection() != 'R'){
                        game.snakes[1].setDirection('L');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    if(game.snakes[1].getDirection() != 'L'){
                        game.snakes[1].setDirection('R');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    if(game.snakes[1].getDirection() != 'D'){
                        game.snakes[1].setDirection('U');
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    if(game.snakes[1].getDirection() != 'U'){
                        game.snakes[1].setDirection('D');
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
