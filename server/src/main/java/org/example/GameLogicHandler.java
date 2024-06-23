package org.example;

import org.example.objects.Food;
import org.example.objects.Obstacle;
import org.example.objects.Snake;

import java.util.Random;

public class GameLogicHandler {

    private Game game;
    private Game OGGame;
    private int initialSize;
    private int players;

    private Random random;
    private int currentFood = 0;
    private boolean running = false;

    public GameLogicHandler(Game game, int initialSize, int players){
        //OGGame = new Game(game);
        this.game = game;
        this.random = new Random();
        this.initialSize = initialSize;
        this.players = players;
    }

    public GameLogicHandler(Game game){
        //OGGame = new Game(game);
        this.game = game;
    }

    public void initializeGame() {
        initializeObstacles();
        clearFoods();
        running = true;
    }

    public void startGame(){
        initializeGame();
    }


    public void restart(Game game) {
        //this.game = new Game(game);
        initializeGame();

    }


    public void addSnake(Snake snake){
        if (game.getSnakes().isEmpty()) {
            snake.setX(new int[game.getGamePlan().getWidth() * game.getGamePlan().getHeight()]);
            snake.setY(new int[game.getGamePlan().getWidth() * game.getGamePlan().getHeight()]);
            snake.setBodyParts(initialSize);
            snake.setHead(0, 0);
            snake.setDirection('L');
            snake.setSpeed();
        } else {
            snake.setX(new int[game.getGamePlan().getWidth() * game.getGamePlan().getHeight()]);
            snake.setY(new int[game.getGamePlan().getWidth() * game.getGamePlan().getHeight()]);
            snake.setBodyParts(initialSize);
            snake.setHead(game.getGamePlan().getWidth() - 1, game.getGamePlan().getHeight() - 1);
            snake.setDirection('R');
            snake.setSpeed();
        }
        game.getSnakes().add(snake);
    }


    private void setPlayers() {
        for(int i = 0; i < players; i++){
            if (i == 0) {
                game.getSnakes().add(new Snake(game.getGamePlan().getWidth() * game.getGamePlan().getHeight(), initialSize, 0, 0, 'R', "Snake 1"));
            } else if (i == 1) {
                game.getSnakes().add(new Snake(game.getGamePlan().getWidth() * game.getGamePlan().getHeight(), initialSize, game.getGamePlan().getWidth() - 1, game.getGamePlan().getHeight() - 1, 'L', "Snake 2"));
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getOGGame() {
        return OGGame;
    }
}
