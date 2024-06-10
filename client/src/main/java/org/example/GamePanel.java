package org.example;

import org.example.objects.Snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    public Image backgroundImage;
    Snake snake;
    int appleX;
    int appleY;

    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        try {
            setBackgroundImage();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        initialization();



    }

    public void setGame(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT+100));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        if(timer != null){
            timer.stop();
        }
        this.addKeyListener(new MyKeyAdapter());
    }

    public void setBackgroundImage() throws IOException {
        this.backgroundImage = ImageIO.read(new File("images/background.jpg"));
    }

    public void initialization(){
        setGame();
        snake = new Snake(GAME_UNITS);
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
            g.setColor(Color.gray);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.drawImage(backgroundImage, 0, 0, this);
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for(int i = 0; i < snake.getBodyParts(); i++) {
                if(i == 0){
                    g.setColor(Color.green);

                }
                else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(snake.getX(i), snake.getY(i), UNIT_SIZE, UNIT_SIZE);
            }


                displayScore(g);

        }
        else {
            gameOver(g);
        }

    }

    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        for(int i = snake.getBodyParts(); i> 0; i--){
            snake.setX(i, snake.getX(i-1));
            snake.setY(i, snake.getY(i-1));
        }
        switch (snake.getDirection()){
            case 'U':
                snake.setY(0, snake.getY(0)-UNIT_SIZE);
                break;
            case 'D':
                snake.setY(0, snake.getY(0)+UNIT_SIZE);
                break;
            case 'L':
                snake.setX(0, snake.getX(0)-UNIT_SIZE);
                break;
            case 'R':
                snake.setX(0, snake.getX(0)+UNIT_SIZE);
                break;
        }
    }

    public void checkApple() {
        if((snake.getX(0) == appleX) && (snake.getY(0) == appleY)) {
            snake.eat();
            newApple();
        }
    }

    public void checkCollisions() {
        // check if head collides with body
        for(int i = snake.getBodyParts(); i>0; i--){
            if((snake.getX(0) == snake.getX(i)) && (snake.getY(0) == snake.getY(i))){
                running = false;
            }
        }
        if(snake.getX(0) < 0) {
            running = false;
        }

        if(snake.getX(0) >= SCREEN_WIDTH){
            running = false;
        }

        if(snake.getY(0) < 0) {
            running = false;
        }

        if(snake.getY(0) > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();;
        }
    }

    public void gameOver(Graphics g){
        //Game Over textg.setColor(Color.blue);
        displayScore(g);

        FontMetrics metrics = getFontMetrics(g.getFont());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Game over", (SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public void restart(){
        initialization();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(snake.getDirection() != 'R'){
                        snake.setDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(snake.getDirection() != 'L'){
                        snake.setDirection('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(snake.getDirection() != 'D'){
                        snake.setDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(snake.getDirection() != 'U'){
                        snake.setDirection('D');
                    }
                    break;
                case KeyEvent.VK_R:
                    restart();
            }
        }
    }

    public void displayScore(Graphics g){
        g.setColor(Color.CYAN);
        g.setFont(new Font("Roboto", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game: " + snake.getApplesEaten(), (SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, SCREEN_HEIGHT+50);
    }


}
