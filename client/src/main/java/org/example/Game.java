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

    /**
     * Konstruktor pro vytvoření instance hry s explicitně zadanými vlastnostmi.
     *
     * @param players počet hráčů ve hře
     * @param width šířka herní plochy
     * @param height výška herní plochy
     * @param obstacles pole překážek ve hře
     * @param size počáteční velikost hada
     * @param time čas na hru
     * @param snakes pole hadů v hře
     * @param foods pole jídel v hře
     * @param gamePlan herní plán hry
     * @param random instance generátoru náhodných čísel
     * @param running stav hry (true pokud běží, jinak false)
     * @param currentFood index aktuálního jídla
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
        this.sidebarPanel = sidebarPanel;
        this.random = new Random();
        this.gamePlan = new GamePlan(width, height);
        this.obstacles = new Obstacle[obstacles];
        this.foods = new Food[food];
        this.snakes = new Snake[players];
        this.size = size;
        this.gameHandler = gameHandler;
    }

    public void setSidebarPanel(){
        this.sidebarPanel = sidebarPanel;
    }

    /**
     * Spustí hru, inicializuje ji a volá metodu GameHandler.initializeGame(Game)
     */
    public void startGame() {
        gameHandler.initializeGame(this);
    }

    /**
     * Zpracuje stav hry na základě přijatých hadů.
     *
     * @param snakes pole hadů, které reprezentuje stav hry
     */
    public void processGameState(Snake[] snakes) {
        // Aktualizuje lokální stav hry na základě přijatého stavu hry
    }

    /**
     * Inicializuje hru nastavením hráčů, překážek a vyčištěním jídel.
     */
    public void initializeGame() {
        setPlayers();
        initializeObstacles();
        clearFoods();
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
     * Vyčistí pole jídel (foods), nastaví všechny prvky na null.
     */
    private void clearFoods() {
        for (int i = 0; i < foods.length; i++) {
            foods[i] = null;
        }
    }

    /**
     * Inicializuje překážky na herní ploše.
     */
    private void initializeObstacles() {
        for (int i = 0; i < obstacles.length; i++) {
            newObstacle(i);
        }
    }

    /**
     * Vytvoří nové jídlo na základě indexu a umístí jej na náhodnou volnou pozici.
     *
     * @param index index v poli jídel (foods)
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
     * Vytvoří novou překážku na základě indexu a umístí ji na náhodnou volnou pozici.
     *
     * @param index index v poli překážek (obstacles)
     */
    private void newObstacle(int index) {
        obstacles[index] = new Obstacle(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
    }

    /**
     * Zkontroluje, zda hadi snědli nějaké jídlo a aktualizuje stav hry podle toho.
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
     * Zkontroluje kolize hadů s překážkami, sebou navzájem a s okrajem herní plochy.
     */
    public void checkCollisions() {
        for (Snake snake : snakes) {
            checkSnakeCollisions(snake);
            checkObstacleCollisions(snake);
            checkBorderCollisions(snake);
        }
    }
    /**
     * Zkontroluje kolize hada s ostatními hady (sebou navzájem).
     *
     * @param snake had, jehož kolize se kontrolují
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
     * Zkontroluje kolize hada s překážkami na herní ploše.
     *
     * @param snake had, jehož kolize s překážkami se kontrolují
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
     * Zkontroluje kolize hada s okrajem herní plochy.
     *
     * @param snake had, jehož kolize s okrajem se kontrolují
     */
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
     * Generuje akci v rámci hry.
     * Inkrementuje index aktuálního jídla a v případě dosažení maximálního indexu, nastaví na začátek.
     * Následně generuje nové jídlo na základě aktuálního indexu.
     */
    public void generateAction() {
        currentFood++;
        if (currentFood == foods.length) {
            currentFood = 0;
        }
        newFood(currentFood);
    }

    /**
     * Vrací stav hry.
     *
     * @return true, pokud hra běží; false jinak
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Nastaví stav běhu hry.
     *
     * @param running true, pokud má hra běžet; false jinak
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
     * Vrací čas na hru.
     *
     * @return čas na hru
     */
    public int getTime() {
        return time;
    }

    /**
     * Sníží čas na hru o jednu jednotku.
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
     * Vrací šířku herní plochy.
     *
     * @return šířka herní plochy
     */
    public int getWidth() {
        return width;
    }

    /**
     * Vrací výšku herní plochy.
     *
     * @return výška herní plochy
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
     * Vrací pole jídel ve hře.
     *
     * @return pole jídel
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
     * Nastaví pole hadů ve hře.
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
     * Nastaví pole jídel ve hře.
     *
     * @param foods pole jídel
     */
    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    /**
     * Nastaví velikost hada.
     *
     * @param size velikost hada
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Nastaví aktuální jídlo.
     *
     * @param currentFood aktuální jídlo
     */
    public void setCurrentFood(int currentFood) {
        this.currentFood = currentFood;
    }

    /**
     * Vrací instanci generátoru náhodných čísel.
     *
     * @return instance generátoru náhodných čísel
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Vrací boční panel (SidebarPanel) hry.
     *
     * @return boční panel (SidebarPanel)
     */
    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    /**
     * Vrací počáteční velikost hada.
     *
     * @return počáteční velikost hada
     */
    public int getSize() {
        return size;
    }

     /** @return aktuální jídlo
     **/
    public int getCurrentFood() {
        return currentFood;
    }

    /**
     * Vrací herního handleru (GameHandler).
     *
     * @return herní handler (GameHandler)
     */
    public GameHandler getGameHandler() {
        return gameHandler;
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

    /**
     * Nastaví herní plán.
     *
     * @param gamePlan herní plán
     */
    public void setGamePlan(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }

    /**
     * Nastaví instanci generátoru náhodných čísel.
     *
     * @param random instance generátoru náhodných čísel
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Nastaví boční panel (SidebarPanel) hry.
     *
     * @param sidebarPanel boční panel (SidebarPanel)
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
     * Nastaví šířku herní plochy.
     *
     * @param width šířka herní plochy
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Nastaví výšku herní plochy.
     *
     * @param height výška herní plochy
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Nastaví čas na hru.
     *
     * @param time čas na hru
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Nastaví herní handler (GameHandler).
     *
     * @param gameHandler herní handler (GameHandler)
     */
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}
