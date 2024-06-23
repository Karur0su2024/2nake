package org.example;

import org.example.objects.Food;
import org.example.objects.Obstacle;
import org.example.objects.Snake;

import javax.swing.*;
import java.util.Random;

public class GameLogicHandler {

    private GuiHandler gui;
    private Game game;
    private Timer timer;
    private Random random = null;
    private int currentFood = 0;
    private boolean running = false;


    private GameHandler gameHandler;

    public GameLogicHandler(Game game, GuiHandler gui){
        this.game = game;
        this.gui = gui;
        this.random = new Random();
    }

    public void initializeGame() {
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

    public void restart() {
        initializeGame();
    }

    public void updateSidebar() {
        gui.getSidebar().setScores(game.getSnakes());
        gui.getSidebar().setTime(game.getTime());
    }


    private void setPlayers() {
        for (int i = 0; i < game.getSnakes().length; i++) {
            game.getSnakes()[i] = new Snake(game.getGamePlan().getWidth() * game.getGamePlan().getHeight(), game.getSize());
            if (i == 0) {
                game.getSnakes()[i].setHead(0, 0);
                game.getSnakes()[i].setDirection('R');
            } else if (i == 1) {
                game.getSnakes()[i].setHead(game.getGamePlan().getWidth() - 1, game.getGamePlan().getHeight() - 1);
                game.getSnakes()[i].setDirection('L');
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
                if (game.getFoods()[i] != null && snake.getX()[0] == game.getFoods()[i].getX() && snake.getY()[0] == game.getFoods()[i].getY()) {
                    snake.eat(game.getFoods()[i].getPoints());
                    newFood(i);
                }
            }
        }
    }

    private boolean isPositionEmpty(int x, int y) {
        for (Snake snake : game.getSnakes()) {
            for (int i = 0; i < snake.getBodyParts(); i++) {
                if (x == snake.getX()[i] && y == snake.getY()[i]) {
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
        for (Obstacle obstacle : game.getObstacles()) {
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
            snake.setHead(game.getGamePlan().getWidth() - 1, snake.getY()[0]);
        } else if (snake.getX()[0] >= game.getGamePlan().getWidth()) {
            snake.setHead(0, snake.getY()[0]);
        }

        if (snake.getY()[0] < 0) {
            snake.setHead(snake.getX()[0], game.getGamePlan().getHeight() - 1);
        } else if (snake.getY()[0] >= game.getGamePlan().getHeight()) {
            snake.setHead(snake.getX()[0], 0);
        }
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
}
