package org.example;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.gui.Sidebar;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private Snake[] snakes = null;
    private Food[] foods = null;
    private Obstacle[] obstacles = null;
    private GamePlan gamePlan = null;
    @JsonIgnore
    private boolean deprecated;

    @JsonIgnore
    private Random random = null;

    @JsonIgnore
    private Sidebar sidebar = null;
    private int size = 6;

    private boolean running = false;
    private int currentFood = 0;
    private int players = 1;
    private int width = 45;
    private int height = 30;
    private int time;

    @JsonIgnore
    private GameHandler gameHandler;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Game(){

    }

    @JsonCreator
    public Game(@JsonProperty("players") int players,
                @JsonProperty("width") int width,
                @JsonProperty("height") int height,
                @JsonProperty("obstacles") Obstacle[] obstacles,
                @JsonProperty("size") int size,
                @JsonProperty("time") int time,
                @JsonProperty("snakes") Snake[] snakes,
                @JsonProperty("foods") Food[] foods,
                @JsonProperty("gamePlan") GamePlan gamePlan,
                @JsonProperty("random") Random random,
                @JsonProperty("running") boolean running,
                @JsonProperty("currentFood") int currentFood) {
        this.players = players;
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
        this.size = size;
        this.time = time;
        this.snakes = snakes;
        this.foods = foods;
        this.gamePlan = gamePlan;
        this.random = random;
        this.running = running;
        this.currentFood = currentFood;
    }

    public Game(int players, int width, int height, int obstacles, int food, int size, int time) {
        this.players = players;
        this.width = width;
        this.height = height;
        this.time = time;
        this.sidebar = sidebar;
        this.random = new Random();
        this.gamePlan = new GamePlan(width, height);
        this.obstacles = new Obstacle[obstacles];
        this.foods = new Food[food];
        this.snakes = new Snake[players];
        this.size = size;
        this.gameHandler = gameHandler;
    }

    public void startGame() {
        gameHandler.initializeGame(this);
    }


    public void initializeGame() {
        setPlayers();
        initializeObstacles();
        clearFoods();
        running = true;
        updateSidebar();
        sidebar.setTime();
    }

    private void updateSidebar() {
        sidebar.setGame(this);
        sidebar.setScores();
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
                if (foods[i] != null && snake.getX()[0] == foods[i].getX() && snake.getY()[0] == foods[i].getY()) {
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
        }
    }

    private void checkSnakeCollisions(Snake snake) {
        for (Snake otherSnake : snakes) {
            for (int i = otherSnake.getBodyParts(); i > 0; i--) {
                if (snake.getX()[0] == otherSnake.getX()[i] && snake.getY()[0] == otherSnake.getY()[i]) {
                    running = false;
                    break;
                }
            }
        }
    }

    private void checkObstacleCollisions(Snake snake) {
        for (Obstacle obstacle : obstacles) {
            if (snake.getX()[0] == obstacle.getX() && snake.getY()[0] == obstacle.getY()) {
                running = false;
                break;
            }
        }
    }

    private void checkBorderCollisions(Snake snake) {
        if (snake.getX()[0] < 0) {
            snake.setHead(width - 1, snake.getY()[0]);
        } else if (snake.getX()[0] >= width) {
            snake.setHead(0, snake.getY()[0]);
        }

        if (snake.getY()[0] < 0) {
            snake.setHead(snake.getX()[0], height - 1);
        } else if (snake.getY()[0] >= height) {
            snake.setHead(snake.getX()[0], 0);
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
                if (x == snake.getX()[i] && y == snake.getY()[i]) {
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
            snakes[i] = new Snake(width * height, size);
            if (i == 0) {
                snakes[i].setHead(0, 0);
                snakes[i].setDirection('R');
            } else if (i == 1) {
                snakes[i].setHead(width - 1, height - 1);
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


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Game fromString(String jsonString) {
        try {
            return mapper.readValue(jsonString, Game.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSidebarPanel(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}

