package org.example;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.ui.SidebarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
public class Game {
    private Snake[] snakes = null;
    private Food[] foods = null;
    private Obstacle[] obstacles = null;
    private GamePlan gamePlan = null;
    private Random random = null;

    @JsonIgnore
    private SidebarPanel sidebarPanel = null;
    private int size = 6;

    private boolean running = false;
    private int currentFood = 0;
    private int players = 1;
    private int width = 45;
    private int height = 30;
    private int time;

    @JsonIgnore
    private boolean deprecated;

    @JsonIgnore
    private GameHandler gameHandler;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Game(){

    }

    public Game(int players, int width, int height, int obstacles, int food, int size, int time) {
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
        this.gameHandler = gameHandler;
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

    public void setSidebarPanel(){
        this.sidebarPanel = sidebarPanel;
    }

    public void startGame() {
        initializeGame();
    }


    public void processGameState(Snake[] snakes) {
        // Update local game state based on the received game state
    }


    public void initializeGame() {
        setPlayers();
        initializeObstacles();
        clearFoods();
        running = true;

    }

    private void updateSidebar() {

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
            snake.setHead(width-1, snake.getY(0));
        } else if (snake.getX(0) >= width) {
            snake.setHead(0, snake.getY(0));
        }

        if (snake.getY(0) < 0) {
            snake.setHead(snake.getX(0), height-1);
        } else if (snake.getY(0) >= height) {
            snake.setHead(snake.getX(0), 0);
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
            snakes[i] = new Snake(width * height, size);
            if (i == 0) {
                snakes[i].setHead(0,0);
                snakes[i].setDirection('R');
            } else if (i == 1) {
                snakes[i].setHead(width-1, height-1);
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

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public Food[] getFoods() {
        return foods;
    }

    public GamePlan getGamePlan() {
        return gamePlan;
    }

    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }

    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCurrentFood(int currentFood) {
        this.currentFood = currentFood;
    }

    public Random getRandom() {
        return random;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public int getSize() {
        return size;
    }

    public int getCurrentFood() {
        return currentFood;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
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


    public void setGamePlan(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setSidebarPanel(SidebarPanel sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}

