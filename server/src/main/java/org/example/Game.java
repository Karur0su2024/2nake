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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Třída Game reprezentuje hlavní logiku a stav hry hadů.
 * Implementuje funkce pro inicializaci hry, zpracování stavu hadů a detekci kolizí.
 */
public class Game {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
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

    /**
     * Vytvoří instanci hry s výchozími hodnotami.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Game(){

    }

    /**
     * Vytvoří instanci hry se zadanými parametry.
     *
     * @param players počet hráčů
     * @param width šířka herního pole
     * @param height výška herního pole
     * @param obstacles pole překážek
     * @param food počet potravin
     * @param size velikost hada
     * @param time čas do konce hry
     */
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

    /**
     * Konstruktor pro vytvoření instance hry s explicitně zadanými vlastnostmi.
     */
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

    /**
     * Spustí hru a inicializuje její první stav.
     */
    public void startGame() {
        initializeGame();
    }

    /**
     * Zpracuje stav hadů na základě přijatého stavu hry.
     *
     * @param snakes pole hadů
     */
    public void processGameState(Snake[] snakes) {
        // Update local game state based on the received game state
    }

    /**
     * Inicializuje hru s výchozími hodnotami a nastavením hráčů.
     */
    public void initializeGame() {
        setPlayers();
        initializeObstacles();
        clearFoods();
        running = true;
    }

    /**
     * Aktualizuje boční panel s herními informacemi.
     */
    private void updateSidebar() {
        // Update sidebar panel with game information
    }

    /**
     * Vyčistí pole s potravinami.
     */
    private void clearFoods() {
        for (int i = 0; i < foods.length; i++) {
            foods[i] = null;
        }
    }

    /**
     * Inicializuje pole překážek s náhodnými překážkami.
     */
    private void initializeObstacles() {
        for (int i = 0; i < obstacles.length; i++) {
            newObstacle(i);
        }
    }

    /**
     * Vytvoří novou potravinu na zadaném indexu s náhodnými souřadnicemi.
     *
     * @param index index potraviny
     */
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

    /**
     * Vytvoří novou překážku na zadaném indexu s náhodnými souřadnicemi.
     *
     * @param index index překážky
     */
    private void newObstacle(int index) {
        obstacles[index] = new Obstacle(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
    }

    /**
     * Zkontroluje kolize hadů s potravinami.
     */
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

    /**
     * Zkontroluje všechny typy kolizí (s hady, překážkami, okrajem) pro všechny hady.
     */
    public void checkCollisions() {
        for (Snake snake : snakes) {
            checkSnakeCollisions(snake);
            checkObstacleCollisions(snake);
            checkBorderCollisions(snake);
        }
    }

    /**
     * Zkontroluje kolize hada s ostatními hady.
     *
     * @param snake had, u kterého se kontrolují kolize
     */
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

    /**
     * Zkontroluje kolize hada s překážkami.
     *
     * @param snake had, u kterého se kontrolují kolize
     */
    private void checkObstacleCollisions(Snake snake) {
        for (Obstacle obstacle : obstacles) {
            if (snake.getX()[0] == obstacle.getX() && snake.getY()[0] == obstacle.getY()) {
                running = false;
                break;
            }
        }
    }

    /**
     * Zkontroluje kolize hada s okrajem herního pole.
     *
     * @param snake had, u kterého se kontrolují kolize
     */
    private void checkBorderCollisions(Snake snake) {
        if (snake.getX()[0] < 0) {
            snake.setHead(width-1, snake.getY()[0]);
        } else if (snake.getX()[0] >= width) {
            snake.setHead(0, snake.getY()[0]);
        }

        if (snake.getY()[0] < 0) {
            snake.setHead(snake.getX()[0], height-1);
        } else if (snake.getY()[0] >= height) {
            snake.setHead(snake.getX()[0], 0);
        }
    }

    /**
     * Kontroluje, zda je na daných souřadnicích prázdné místo (bez hadů ani překážek).
     *
     * @param x x-ová souřadnice
     * @param y y-ová souřadnice
     * @return true, pokud je místo prázdné; jinak false
     */
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

    /**
     * Restartuje hru do výchozího stavu.
     */
    public void restart() {
        initializeGame();
    }

    /**
     * Nastaví hráče do počátečních pozic a směrů.
     */
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

    /**
     * Generuje akci pro hru (např. změna aktuální potraviny).
     */
    public void generateAction() {
        currentFood++;
        if (currentFood == foods.length) {
            currentFood = 0;
        }
        newFood(currentFood);
    }

    /**
     * Vrací informaci o běhu hry.
     *
     * @return true, pokud hra běží; jinak false
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Nastaví stav běhu hry.
     *
     * @param running true, pokud má být hra spuštěna; jinak false
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Vrací pole hadů ve hře.
     *
     * @return pole hadů
     */
    public Snake[] getSnakes() {
        return snakes;
    }

    /**
     * Vrací aktuální čas hry.
     *
     * @return aktuální čas hry
     */
    public int getTime() {
        return time;
    }

    /**
     * Sníží zbývající čas hry o jednu jednotku.
     */
    public void decreaseTime() {
        time--;
    }

    /**
     * Vrací počet hráčů ve hře.
     *
     * @return počet hráčů
     */
    public int getPlayers() {
        return players;
    }

    /**
     * Vrací šířku herního pole.
     *
     * @return šířka herního pole
     */
    public int getWidth() {
        return width;
    }

    /**
     * Vrací výšku herního pole.
     *
     * @return výška herního pole
     */
    public int getHeight() {
        return height;
    }

    /**
     * Vrací pole překážek ve hře.
     *
     * @return pole překážek
     */
    public Obstacle[] getObstacles() {
        return obstacles;
    }

    /**
     * Vrací pole potravin ve hře.
     *
     * @return pole potravin
     */
    public Food[] getFoods() {
        return foods;
    }

    /**
     * Vrací herní plán.
     *
     * @return herní plán
     */
    public GamePlan getGamePlan() {
        return gamePlan;
    }

    /**
     * Nastaví hady ve hře.
     *
     * @param snakes pole hadů
     */
    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }

    /**
     * Nastaví pole překážek ve hře.
     *
     * @param obstacles pole překážek
     */
    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * Nastaví pole potravin ve hře.
     *
     * @param foods pole potravin
     */
    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    /**
     * Nastaví velikost hadů ve hře.
     *
     * @param size velikost hadů
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Nastaví aktuální potravinu ve hře.
     *
     * @param currentFood aktuální potravina
     */
    public void setCurrentFood(int currentFood) {
        this.currentFood = currentFood;
    }

    /**
     * Vrací instanci generátoru náhodných čísel ve hře.
     *
     * @return generátor náhodných čísel
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Vrací boční panel ve hře s herními informacemi.
     *
     * @return boční panel
     */
    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    /**
     * Vrací velikost hadů ve hře.
     *
     * @return velikost hadů
     */
    public int getSize() {
        return size;
    }

    /**
     * Vrací index aktuální potraviny ve hře.
     *
     * @return index aktuální potraviny
     */
    public int getCurrentFood() {
        return currentFood;
    }

    /**
     * Vrací objekt pro manipulaci s herní logikou.
     *
     * @return handler pro hru
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    /**
     * Vrací textovou reprezentaci aktuálního stavu hry.
     *
     * @return textová reprezentace stavu hry
     */
    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Error converting game to JSON: " + e);
            return null;
        }
    }

    /**
     * Převádí JSON řetězec zpět na objekt hry.
     *
     * @param jsonString JSON řetězec
     * @return objekt hry na základě JSON řetězce
     */
    public static Game fromString(String jsonString) {
        try {
            return mapper.readValue(jsonString, Game.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing game from JSON: " + e);
            return null;
        }
    }

    /**
     * Nastaví herní plán.
     *
     * @param gamePlan herní plán
     */
    public void setGamePlan(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }

    /**
     * Nastaví generátor náhodných čísel.
     *
     * @param random generátor náhodných čísel
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Nastaví boční panel ve hře s herními informacemi.
     *
     * @param sidebarPanel boční panel
     */
    public void setSidebarPanel(SidebarPanel sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    /**
     * Nastaví počet hráčů ve hře.
     *
     * @param players počet hráčů
     */
    public void setPlayers(int players) {
        this.players = players;
    }

    /**
     * Nastaví šířku herního pole.
     *
     * @param width šířka herního pole
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Nastaví výšku herního pole.
     *
     * @param height výška herního pole
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Nastaví zbývající čas hry.
     *
     * @param time zbývající čas hry
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Nastaví handler pro hru.
     *
     * @param gameHandler handler pro hru
     */
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}


