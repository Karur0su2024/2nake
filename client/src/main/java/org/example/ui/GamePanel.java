package org.example.ui;

import org.example.GameSettings;
import org.example.Game;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {

    public Image backgroundImage;

    Timer timer;
    Random random;
    Game game;
    int player;
    int players;

    GamePanel(int players){
        random = new Random();
        setGame();
        this.players = players;
        player = 0;
        game = new Game(timer, 2);
    }

    public void setGame(){
        this.setPreferredSize(new Dimension(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT+100));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        if(timer != null){
            timer.stop();
        }
        timer = new Timer(GameSettings.DELAY, this);
        timer.start();
        this.addKeyListener(new MyKeyAdapter());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g){
        game.paint(g);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(game.running){
            game.move();
            game.checkFood();
            game.checkCollisions();
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
