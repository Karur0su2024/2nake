package org.example.ui;

import org.example.GameSettings;
import org.example.objects.Apple;
import org.example.objects.Obstacle;
import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener {

    public Image backgroundImage;
    Snake snake;
    Apple[] apples = new Apple[GameSettings.APPLES];
    Obstacle[] obstacles = new Obstacle[GameSettings.OBSTACLES];
    boolean running = false;
    Timer timer;
    Random random;

    Game(){
        random = new Random();

        initialization();
    }

    public void setGame(){
        this.setPreferredSize(new Dimension(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT+100));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        if(timer != null){
            timer.stop();
        }
        this.addKeyListener(new MyKeyAdapter());
    }


    public void initialization(){
        setGame();
        snake = new Snake(GameSettings.GAME_UNITS);

        for(int i = 0; i < obstacles.length; i++){
            newObstacle(i);
        }

        for(int i = 0; i < apples.length; i++){
            newApple(i);
        }




        startGame();
    }

    public void startGame(){
        for(int i = 0; i < apples.length; i++){
            newApple(i);
        }
        running = true;
        timer = new Timer(GameSettings.DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
            g.setColor(Color.gray);
            g.fillRect(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
            g.drawImage(backgroundImage, 0, 0, this);


            for (Apple apple : apples) {
                apple.paint(g, GameSettings.UNIT_SIZE);
            }

            for(int i = 0; i < obstacles.length; i++){
                obstacles[i].paint(g);
            }

            for(int i = 0; i < snake.getBodyParts(); i++) {
                if(i == 0){
                    g.setColor(Color.WHITE);

                }
                else {
                    g.setColor(new Color(200, 200, 200));
                }
                g.fillRect(snake.getX(i), snake.getY(i), GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
            }


                displayScore(g);

        }
        else {
            gameOver(g);
        }

    }

    public void newApple(int i){
        int x = 0;
        int y = 0;
        boolean empty = false;
        while(!empty){
            x = random.nextInt((int)(GameSettings.SCREEN_WIDTH/GameSettings.UNIT_SIZE))*GameSettings.UNIT_SIZE;
            y = random.nextInt((int)(GameSettings.SCREEN_HEIGHT/GameSettings.UNIT_SIZE))*GameSettings.UNIT_SIZE;
            empty = checkIfEmpty(x, y);
        }
        apples[i] = new Apple(x,y);
    }

    public void newObstacle(int i){
        obstacles[i] = new Obstacle(random.nextInt((int)(GameSettings.SCREEN_WIDTH/GameSettings.UNIT_SIZE))*GameSettings.UNIT_SIZE, random.nextInt((int)(GameSettings.SCREEN_HEIGHT/GameSettings.UNIT_SIZE))*GameSettings.UNIT_SIZE);
    }

    public void move(){
        for(int i = snake.getBodyParts(); i> 0; i--){
            snake.setX(i, snake.getX(i-1));
            snake.setY(i, snake.getY(i-1));
        }

    }

    public void checkApple() {
        for(int i = 0; i < apples.length; i++){
            if((snake.getX(0) == apples[i].getX()) && (snake.getY(0) == apples[i].getY())) {
                snake.eat();
                newApple(i);
            }
        }

    }

    public void checkCollisions() {
        // check if head collides with body
        for(int i = snake.getBodyParts(); i>0; i--){
            if((snake.getX(0) == snake.getX(i)) && (snake.getY(0) == snake.getY(i))){
                running = false;
            }
        }

        for(int i = 0; i < obstacles.length; i++){
            if((snake.getX(0) == obstacles[i].getX()) && (snake.getY(0) == obstacles[i].getY())){
                running = false;
            }
        }

        if(snake.getX(0) < 0) {
            running = false;
        }

        if(snake.getX(0) >= GameSettings.SCREEN_WIDTH){
            running = false;
        }

        if(snake.getY(0) < 0) {
            running = false;
        }

        if(snake.getY(0) >= GameSettings.SCREEN_HEIGHT){
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
        g.drawString("Game over", (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, GameSettings.SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            snake.move();
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
        g.drawString("Game: " + snake.getApplesEaten(), (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, GameSettings.SCREEN_HEIGHT+50);
    }


    public boolean checkIfEmpty(int x, int y){
        boolean empty = false;
        for(int i = 0; i < snake.getBodyParts(); i++){
            if(x == snake.getX(i) && y == snake.getY(i)){
                return false;
            }
        }
        for(Obstacle obstacle : obstacles){
            if(x == obstacle.getX() && y == obstacle.getY()){
                return false;
            }
        }

        return true;


    }

}
