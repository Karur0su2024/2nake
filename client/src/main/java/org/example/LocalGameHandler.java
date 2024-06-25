package org.example;

import org.example.objects.Snake;
import org.example.gui.GamePanel;
import org.example.objects.snake.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Třída zajišťující lokální obsluhu hry, implementuje rozhraní GameHandler a ActionListener.
 * Spravuje inicializaci hry, odesílání akcí hráčů, aktualizaci stavu hry a spouštění časovače.
 */
public class LocalGameHandler implements GameHandler, ActionListener {

    private GameLogicHandler gameLogic;
    private Timer timer;
    private int time = 0;
    private final GamePanel gamePanel;

    /**
     * Konstruktor pro vytvoření lokálního správce hry.
     *
     * @param gamePanel herní panel pro vykreslení grafiky hry
     */
    public LocalGameHandler(GamePanel gamePanel, GameLogicHandler gameLogic) {
        this.gamePanel = gamePanel;
        this.gameLogic = gameLogic;
    }

    public void resetTime(){
        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(GameSettings.DELAY, this);
        timer.start();
    }

    /**
     * Inicializuje hru s daným objektem Game a spustí časovač pro pravidelnou aktualizaci hry.
     *
     */
    @Override
    public void initializeGame() {
        gameLogic.restart(gameLogic.getOGGame());

        resetTime();

    }

    /**
     * Odesílá akci hráče na základě stisku klávesy.
     *
     * @param key    kód stisknuté klávesy
     */
    @Override
    public void sendPlayerAction(int key) {
        Snake snake = gameLogic.getGame().getSnakes().iterator().next();
        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                snake.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                snake.setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                snake.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                snake.setDirection(Direction.DOWN);
                break;
        }
    }

    /**
     * Není používáno pro lokální hru, pouze pro síťovou komunikaci.
     *
     * @param gameState stav hry ve formátu řetězce
     */
    @Override
    public void receiveGameState(String gameState) {
        // Není potřeba pro lokální hru
    }

    /**
     * Aktualizuje stav hry v pravidelných intervalech podle časovače.
     */
    @Override
    public void updateGame() {
        if (gameLogic.isRunning()) {
            time++;
            if (time % 200 == 0) {
                gameLogic.generateAction();
            }

            if (time % 80 == 0) {
                gameLogic.getGame().decreaseTime();
            }

            for (Snake snake : gameLogic.getGame().getSnakes()) {
                if (time % snake.getSpeed() == 0) {
                    snake.move();
                    gameLogic.checkCollisions();
                    gameLogic.checkFood();

                }
                if (gameLogic.getGame().getTime() == 0) {
                    gameLogic.setRunning(false);
                }
            }

            if (!gameLogic.isRunning()) {
                gamePanel.showGameOverDialog();
            }
        }
        gameLogic.updateSidebar();
        gamePanel.repaint();
    }

    /**
     * Metoda volaná časovačem, provádí aktualizaci hry, pokud je hra spuštěna.
     *
     * @param e událost akce
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameLogic.isRunning()) {
            updateGame();
        }
    }
}
