package org.example;

import org.example.objects.Food;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.objects.snake.Direction;
import org.example.objects.snake.SnakePart;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameLogicHandler {

    private GuiHandler gui;
    private Game game;
    private Game OGGame;
    private int initialSize;
    private Random random = null;
    private int currentFood = 0;
    private boolean running = false;
    private int players;

    private GameHandler gameHandler;

    public GameLogicHandler(GameSettings gameSettings, GuiHandler gui, int players){
        OGGame = new Game(gameSettings);
        game = OGGame;

        this.gui = gui;
        this.random = new Random();
        this.initialSize = gameSettings.getInitialSnakeSize();
        this.players = players;
    }

    public GameLogicHandler(GuiHandler gui){
        this.gui = gui;
    }

    public void initializeGame() {
        game.getSnakes().clear();
        setPlayers();
        initializeObstacles();
        clearFoods();
        running = true;
        updateSidebar();
    }

    public void setHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void startGame(){
        this.gameHandler.initializeGame();
    }


    public void restart(Game game) {
        this.game = new Game(game);
        initializeGame();

    }

    public void updateSidebar() {
        gui.getSidebar().setScores(game.getSnakes());
        gui.getSidebar().setTime(game.getTime());
    }


    private void setPlayers() {
        for(int i = 0; i < players; i++){
            if (i == 0) {
                game.getSnakes().add(new Snake(initialSize, 0, 0, Direction.RIGHT, "Snake 1"));
            } else if (i == 1) {
                //game.getSnakes().add(new Snake(game.getGamePlan().getWidth() * game.getGamePlan().getHeight(), initialSize, game.getGamePlan().getWidth() - 1, game.getGamePlan().getHeight() - 1, 'L', "Snake 2"));
            }
        }
    }

    public void generateAction() {
        currentFood++;
        if (currentFood == game.getFoods().length) {
            currentFood = 0;
        }
        newFood(currentFood);
    }

    private void newFood(int index) {
        int x = 0, y = 0;
        boolean empty = false;
        while (!empty) {
            x = random.nextInt(game.getGamePlan().getWidth());
            y = random.nextInt(game.getGamePlan().getHeight());
            empty = isPositionEmpty(x, y);
        }
        game.getFoods()[index] = new Food(x, y, random.nextInt(30)-5);
    }

    /**
     * Zkontroluje, zda hadi snědli nějaké jídlo a aktualizuje stav hry podle toho.
     */
    public void checkFood() {
        for (Snake snake : game.getSnakes()) {
            for (int i = 0; i < game.getFoods().length; i++) {
                if (game.getFoods()[i] != null && snake.getBodyParts().getFirst().getX() == game.getFoods()[i].getX() && snake.getBodyParts().getFirst().getY() == game.getFoods()[i].getY()) {
                    snake.eat(game.getFoods()[i]);
                    newFood(i);
                }
            }
        }
    }

    private boolean isPositionEmpty(int x, int y) {
        for (Snake snake : game.getSnakes()) {
            for (SnakePart part : snake.getBodyParts()) {
                if (x == part.getX() && y == part.getY()) {
                    return false;
                }
            }
        }

        for (Obstacle obstacle : game.getObstacles()) {
            if (x == obstacle.getX() && y == obstacle.getY()) {
                return false;
            }
        }

        return true;
    }


    /**
     * Zkontroluje kolize hadů s překážkami, sebou navzájem a s okrajem herní plochy.
     */
    public void checkCollisions() {
        for (Snake snake : game.getSnakes()) {
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
        for (Snake otherSnake : game.getSnakes()) {
            for(SnakePart part : otherSnake.getBodyParts()){
                if(snake.getBodyParts().getFirst().getX() == part.getX() && snake.getBodyParts().getFirst().getY() == part.getY()){
                    //running = false;
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
        for (Obstacle obstacle : game.getObstacles()) {
            if (snake.getBodyParts().getFirst().getX() == obstacle.getX() && snake.getBodyParts().getFirst().getY() == obstacle.getY()) {
                //running = false;
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
        //snake.move();
    }


    /**
     * Inicializuje překážky na herní ploše.
     */
    private void initializeObstacles() {
        for (int i = 0; i < game.getObstacles().length; i++) {
            newObstacle(i);
        }
    }

    /**
     * Vytvoří novou překážku na základě indexu a umístí ji na náhodnou volnou pozici.
     *
     * @param index index v poli překážek (obstacles)
     */
    private void newObstacle(int index) {
        game.getObstacles()[index] = new Obstacle(random.nextInt(game.getGamePlan().getWidth() - 2) + 1, random.nextInt(game.getGamePlan().getHeight() - 2) + 1);
    }


    /**
     * Vyčistí pole jídel (foods), nastaví všechny prvky na null.
     */
    private void clearFoods() {
        for (int i = 0; i < game.getFoods().length; i++) {
            game.getFoods()[i] = null;
        }
    }


    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getOGGame() {
        return OGGame;
    }

    public void refreshGame(Game game){
        this.game = game;
        updateSidebar();

    }

    public int getPlayers() {
        return players;
    }

    public void paint(Graphics g, JPanel panel) {
        game.getGamePlan().paint(g);
        for (Food food : game.getFoods()) {
            if (food != null) {
                food.paint(g, panel);
            }
        }
        for (Obstacle obstacle : game.getObstacles()) {
            obstacle.paint(g);
        }
        for (Snake snake : getGame().getSnakes()) {
            snake.paint(g);
        }
    }
}
