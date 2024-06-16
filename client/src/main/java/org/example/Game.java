package org.example;

import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.ui.GameOverFrame;
import org.example.ui.SidebarPanel;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Game {
    private final Snake[] snakes;
    private final Food[] foods;
    private final Obstacle[] obstacles;
    private final GamePlan gamePlan;
    private final Random random;
    private final Timer timer;
    private final SidebarPanel sidebarPanel;
    private final int size;

    private boolean running = false;
    private int currentFood = 0;
    private final int players;
    private final int width;
    private final int height;
    private int time;

    private GameHandler gameHandler;

    public Game(Timer timer, int players, int width, int height, int obstacles, int food, int size, int time, SidebarPanel sidebarPanel) {
        this.timer = timer;
        this.players = players;
        this.width = width;
        this.height = height;
        this.time = time;
        this.sidebarPanel = sidebarPanel;
        this.random = new Random();
        this.gamePlan = new GamePlan(width, height);
        this.obstacles = new Obstacle[obstacles];
        this.foods = new Food[food];
        this.snakes = new Snake[players];
        this.size = size;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void startGame() {
        gameHandler.initializeGame(this);
    }

    public void playerAction(String action) {
        gameHandler.sendPlayerAction(action);
    }

    public void processGameState(String gameState) {
        // Update local game state based on the received game state
    }

    public void updateGame() {
        gameHandler.updateGame();
    }

    public void initializeGame() {
        setPlayers();
        initializeObstacles();
        clearFoods();
        running = true;
        updateSidebar();
        sidebarPanel.setTime();
    }

    private void updateSidebar() {
        sidebarPanel.setGame(this);
        sidebarPanel.setScores();
    }

    private void clearFoods() {
        for (int i = 0; i < foods.length; i++) {
            foods[i] = null;
        }
    }

    private void initializeObstacles() {
        for (int i = 0; i < obstacles.length; i++) {
            newObstacle(i);
        }
    }

    private void newFood(int index) {
        int x = 0, y = 0;
        boolean empty = false;
        while (!empty) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            empty = isPositionEmpty(x, y);
        }
        foods[index] = new Food(x, y);
    }

    private void newObstacle(int index) {
        obstacles[index] = new Obstacle(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
    }

    public void checkFood() {
        for (Snake snake : snakes) {
            for (int i = 0; i < foods.length; i++) {
                if (foods[i] != null && snake.getX(0) == foods[i].getX() && snake.getY(0) == foods[i].getY()) {
                    snake.eat(foods[i].getPoints());
                    newFood(i);
                }
            }
        }
    }

    public void checkCollisions() {
        for (Snake snake : snakes) {
            checkSnakeCollisions(snake);
            checkObstacleCollisions(snake);
            checkBorderCollisions(snake);

            if (!running) {
                timer.stop();
            }
        }
    }

    private void checkSnakeCollisions(Snake snake) {
        for (Snake otherSnake : snakes) {
            for (int i = otherSnake.getBodyParts(); i > 0; i--) {
                if (snake.getX(0) == otherSnake.getX(i) && snake.getY(0) == otherSnake.getY(i)) {
                    running = false;
                    break;
                }
            }
        }
    }

    private void checkObstacleCollisions(Snake snake) {
        for (Obstacle obstacle : obstacles) {
            if (snake.getX(0) == obstacle.getX() && snake.getY(0) == obstacle.getY()) {
                running = false;
                break;
            }
        }
    }

    private void checkBorderCollisions(Snake snake) {
        if (snake.getX(0) < 0) {
            snake.setX(width - 1);
        } else if (snake.getX(0) >= width) {
            snake.setX(0);
        }

        if (snake.getY(0) < 0) {
            snake.setY(height - 1);
        } else if (snake.getY(0) >= height) {
            snake.setY(0);
        }
    }

    public void paint(Graphics g, JPanel panel) {
        gamePlan.paint(g);
        for (Food food : foods) {
            if (food != null) {
                food.paint(g, panel);
            }
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.paint(g);
        }
        for (Snake snake : snakes) {
            snake.paint(g, panel);
        }
    }

    private boolean isPositionEmpty(int x, int y) {
        for (Snake snake : snakes) {
            for (int i = 0; i < snake.getBodyParts(); i++) {
                if (x == snake.getX(i) && y == snake.getY(i)) {
                    return false;
                }
            }
        }

        for (Obstacle obstacle : obstacles) {
            if (x == obstacle.getX() && y == obstacle.getY()) {
                return false;
            }
        }

        return true;
    }

    public void restart() {
        initializeGame();
    }

    private void setPlayers() {
        for (int i = 0; i < players; i++) {
            snakes[i] = new Snake(width * height, size, sidebarPanel);
            if (i == 0) {
                snakes[i].setX(0);
                snakes[i].setY(0);
                snakes[i].setDirection('R');
            } else if (i == 1) {
                snakes[i].setX(width - 1);
                snakes[i].setY(height - 1);
                snakes[i].setDirection('L');
            }
        }
    }

    public void generateAction() {
        currentFood++;
        if (currentFood == foods.length) {
            currentFood = 0;
        }
        newFood(currentFood);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Snake[] getSnakes() {
        return snakes;
    }

    public int getTime() {
        return time;
    }

    public void decreaseTime() {
        time--;
    }

    public int getPlayers() {
        return players;
    }
}
