package org.example;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.ui.GameOverFrame;
import org.example.ui.SidebarPanel;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Třída {@code Game} reprezentuje stav hry ve hře Snake.
 * Obsahuje informace o hráčích, velikosti hry, překážkách, jídle a dalších prvcích hry.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private static final Logger log = LoggerFactory.getLogger(Game.class);
    private Snake[] snakes = null;
    private Food[] foods = null;
    private Obstacle[] obstacles = null;
    private GamePlan gamePlan = null;
    @JsonIgnore
    private boolean deprecated;

    @JsonIgnore
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

    /**
     * Konstruktor pro vytvoření instance hry s implicitně zadanými parametry.
     *
     * @param players počet hráčů ve hře
     * @param width šířka herní plochy
     * @param height výška herní plochy
     * @param obstacles počet překážek ve hře
     * @param food počet jídel ve hře
     * @param size počáteční velikost hada
     * @param time čas na hru
     */
    public Game(int players, int width, int height, int obstacles, int food, int size, int time) {
        this.players = players;
        this.width = width;
        this.height = height;
        this.time = time;
        this.random = new Random();
        this.gamePlan = new GamePlan(width, height);
        this.obstacles = new Obstacle[obstacles];
        this.foods = new Food[food];
        this.snakes = new Snake[players];
        this.size = size;
        this.gameHandler = gameHandler;
    }

    /**
     * Spustí hru, inicializuje ji a volá metodu GameHandler.initializeGame(Game)
     */
    public void startGame() {
        gameHandler.initializeGame(this);
    }


    /**
     * Inicializuje hru nastavením hráčů, překážek a vyčištěním jídel.
     */
    public void initializeGame() {
        setPlayers();
        running = true;
        updateSidebar();
        sidebarPanel.setTime();
    }

    /**
     * Aktualizuje boční panel (SidebarPanel) hry zobrazující skóre a čas.
     */
    private void updateSidebar() {
        sidebarPanel.setGame(this);
        sidebarPanel.setScores();
    }





    /**
     * Vykreslí herní stav do daného grafického kontextu (Graphics).
     *
     * @param g grafický kontext pro vykreslení
     * @param panel panel, na který se má vykreslit
     */
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

    /**
     * Zjistí, zda je daná pozice (x, y) na herní ploše prázdná (není na ní had ani překážka).
     *
     * @param x x-ová souřadnice pozice
     * @param y y-ová souřadnice pozice
     * @return true, pokud je pozice prázdná; false jinak
     */


    /**
     * Restartuje hru, inicializuje ji do počátečního stavu.
     */
    public void restart() {
        initializeGame();
    }

    /**
     * Nastaví počet hráčů v hře.
     **/

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


    /**
     * Sníží čas na hru o jednu jednotku.
     */
    public void decreaseTime() {
        time--;
    }


    /**
     * Vrací JSON reprezentaci aktuální instance hry.
     *
     * @return JSON reprezentace aktuální instance hry
     */
    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(""+ e);
            return null;
        }
    }

    /**
     * Vytváří instanci hry na základě JSON řetězce.
     *
     * @param jsonString JSON reprezentace instance hry
     * @return instance hry vytvořená z JSON řetězce
     */
    public static Game fromString(String jsonString) {
        try {
            return mapper.readValue(jsonString, Game.class);
        } catch (JsonProcessingException e) {
            log.error(""+ e);
            return null;
        }
    }


    public Snake[] getSnakes() {
        return snakes;
    }

    public Food[] getFoods() {
        return foods;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public GamePlan getGamePlan() {
        return gamePlan;
    }

    public boolean isDeprecated() {
        return deprecated;
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

    public boolean isRunning() {
        return running;
    }

    public int getCurrentFood() {
        return currentFood;
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

    public int getTime() {
        return time;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }

    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    public void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void setGamePlan(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setSidebarPanel(SidebarPanel sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCurrentFood(int currentFood) {
        this.currentFood = currentFood;
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
