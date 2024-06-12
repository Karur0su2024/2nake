package org.example;

import org.example.objects.Food;
import org.example.objects.Obstacle;
import org.example.objects.Snake;

import java.awt.*;
import java.util.Random;
import javax.swing.Timer;


public class Game {
    public Snake[] snakes;
    public Food[] foods = new Food[GameSettings.APPLES];
    public Obstacle[] obstacles = new Obstacle[GameSettings.OBSTACLES];
    public int players;
    public boolean running = false;
    Random random;
    Timer timer;

    public Game(Timer timer, int players){
        random = new Random();
        this.players = players;

        snakes = new Snake[players];
        this.timer = timer;
        setGame();
    }



    private void setGame(){

        setPlayers();



        for(int i = 0; i < obstacles.length; i++){
            newObstacle(i);
        }
        for(int i = 0; i < foods.length; i++) {
            newApple(i);
        }
        running = true;
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
        foods[i] = new Food(x,y);
    }

    public void newObstacle(int i){
        obstacles[i] = new Obstacle(random.nextInt((int)(GameSettings.SCREEN_WIDTH/GameSettings.UNIT_SIZE))*GameSettings.UNIT_SIZE, random.nextInt((int)(GameSettings.SCREEN_HEIGHT/GameSettings.UNIT_SIZE))*GameSettings.UNIT_SIZE);
    }

    public void move(){
        for(Snake snake : snakes){
            for(int i = snake.getBodyParts(); i> 0; i--){
                snake.setX(i, snake.getX(i-1));
                snake.setY(i, snake.getY(i-1));

            }
            snake.move();
        }


    }

    public void checkFood() {
        for(Snake snake : snakes){
            for(int i = 0; i < foods.length; i++){
                if((snake.getX(0) == foods[i].getX()) && (snake.getY(0) == foods[i].getY())) {
                    snake.eat(foods[i].getPoints());
                    newApple(i);
                }
            }
        }

    }

    public void checkCollisions() {
        // check if head collides with body
        for(Snake snake : snakes){
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

    }


    public void paint(Graphics g){
        if(running){
            g.setColor(Color.black);
            g.fillRect(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);


            for (Food food : foods) {
                food.paint(g, GameSettings.UNIT_SIZE);
            }

            for(Obstacle obstacle : obstacles){
                obstacle.paint(g);
            }

            for(Snake snake : snakes){
                snake.paint(g);
            }



            displayScore(g);

        }
        else {
            gameOver(g);
        }
    }



    public boolean checkIfEmpty(int x, int y){
        for(Snake snake : snakes){
            for(int i = 0; i < snake.getBodyParts(); i++){
                if(x == snake.getX(i) && y == snake.getY(i)){
                    return false;
                }
            }
        }

        for(Obstacle obstacle : obstacles){
            if(x == obstacle.getX() && y == obstacle.getY()){
                return false;
            }
        }

        return true;


    }

    public void restart(){
        setGame();
    }

    public void gameOver(Graphics g){
        displayScore(g);

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("Game over", (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, GameSettings.SCREEN_HEIGHT/2);
    }

    public void displayScore(Graphics g){
        g.setColor(Color.CYAN);
        g.setFont(new Font("Roboto", Font.BOLD, 40));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        //g.drawString("Score: " + snakes.getApplesEaten(), (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, GameSettings.SCREEN_HEIGHT+50);
    }


    public void setPlayers(){
        for(int i = 0; i < players; i++){
            snakes[i] = new Snake(GameSettings.GAME_UNITS);
            if(i == 0){
                snakes[i].setX(0, 0);
                snakes[i].setY(0, 0);
                snakes[i].setDirection('R');
            }
            if(i == 1){
                snakes[i].setX(0, GameSettings.SCREEN_WIDTH-GameSettings.UNIT_SIZE);
                snakes[i].setY(0, GameSettings.SCREEN_HEIGHT-GameSettings.UNIT_SIZE);
                snakes[i].setDirection('L');
            }
        }

    }

}
