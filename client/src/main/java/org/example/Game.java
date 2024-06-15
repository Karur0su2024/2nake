package org.example;

import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.ui.SidebarPanel;

import java.awt.*;
import java.util.Random;
import javax.swing.*;


public class Game {
    public Snake[] snakes;
    public Food[] foods;
    int currentFood = 0;
    int size;

    public Obstacle[] obstacles;
    public int players;
    public boolean running = false;
    public GamePlan gamePlan;

    Random random;
    Timer timer;

    int timeLeft;
    public int width;
    public int height;
    SidebarPanel sidebarPanel;
    public int length;

    public Game(Timer timer, int players, int width, int height, int obsactles, int food, int size, int length, SidebarPanel sidebarPanel){
        random = new Random();
        this.length = length;
        this.sidebarPanel = sidebarPanel;
        timeLeft = 20;
        this.width = width;
        this.height = height;
        this.gamePlan = new GamePlan(this.width, this.height);
        this.players = players;
        obstacles = new Obstacle[obsactles];
        foods = new Food[food];
        snakes = new Snake[players];
        this.timer = timer;
        this.size = size;
        setGame();
    }


    private void setGame(){

        setPlayers();
        for(int i = 0; i < obstacles.length; i++){
            newObstacle(i);
        }
        for(int i = 0; i < foods.length; i++) {
            foods[i] = null;
        }
        running = true;

        setSidebar();

        sidebarPanel.setTime();
    }

    private void setSidebar(){
        this.sidebarPanel.setGame(this);
        this.sidebarPanel.setScores();
    }

    public void newApple(int i){
        int x = 0;
        int y = 0;
        boolean empty = false;
        while(!empty){
            x = random.nextInt((this.width));
            y = random.nextInt((this.height));
            empty = checkIfEmpty(x, y);
        }
        foods[i] = new Food(x,y);
    }

    public void newObstacle(int i){
        obstacles[i] = new Obstacle(random.nextInt(this.width-2)+1, random.nextInt(this.height-2)+1);
    }

    public void checkFood() {
        for(Snake snake : snakes){
            for(int i = 0; i < foods.length; i++){
                if(foods[i] != null){
                    if((snake.getX(0) == foods[i].getX()) && (snake.getY(0) == foods[i].getY())) {
                        snake.eat(foods[i].getPoints());
                        newApple(i);
                    }
                }
            }
        }
    }

    public void checkCollisions() {
        // check if head collides with body
        for(Snake snake : snakes){
            for(Snake snake2 : snakes){
                for(int i = snake2.getBodyParts(); i>0; i--){

                    if((snake.getX(0) == snake2.getX(i)) && (snake.getY(0) == snake2.getY(i))){
                        running = false;
                    }
                }
            }


            for(int i = 0; i < obstacles.length; i++){
                if((snake.getX(0) == obstacles[i].getX()) && (snake.getY(0) == obstacles[i].getY())){
                    running = false;
                }
            }

            checkBorders(snake);

            if(!running){
                timer.stop();;
            }
        }
    }

    private void checkBorders(Snake snake){
        if(snake.getX(0) < 0) {
            snake.setX(width-1);
        }

        if(snake.getX(0) >= width){
            snake.setX(0);
        }

        if(snake.getY(0) < 0) {
            snake.setY(height-1);
        }

        if(snake.getY(0) >= height){
            snake.setY(0);
        }
    }

    public void paint(Graphics g, JPanel panel){
        if(running){
            gamePlan.paint(g);

            for (Food food : foods) {
                if(food != null){
                    food.paint(g, panel);
                }
            }

            for(Obstacle obstacle : obstacles){
                obstacle.paint(g);
            }

            for(Snake snake : snakes){
                snake.paint(g, panel);
            }
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

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("Game over", (GameSettings.SCREEN_WIDTH- metrics.stringWidth("Game over"))/2, GameSettings.SCREEN_HEIGHT/2);
    }




    public void setPlayers(){
        for(int i = 0; i < players; i++){

            if(i == 0){
                snakes[i] = new Snake(width*height, this.size, this.sidebarPanel);
                snakes[i].setX(0);
                snakes[i].setY(0);
                snakes[i].setDirection('R');
            }
            if(i == 1){
                snakes[i] = new Snake(width*height, this.size, this.sidebarPanel);
                snakes[i].setX(width-1);
                snakes[i].setY(height-1);
                snakes[i].setDirection('L');
            }
        }

    }

    public void generateAction(){
        currentFood++;
        if(currentFood == foods.length){
            currentFood = 0;
        }
        newApple(currentFood);
    }

}
