package org.example.gui;

import org.example.*;
import org.example.objects.GamePlan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Panel zobrazující hlavní herní plochu a interakci hráčů s hrou.
 */
public class GamePanel extends JPanel {

    private Game game;
    private GameLogicHandler gameLogic;


    private final GameClient gameClient;
    private String gameMode;
    private final GameHandler gameHandler;
    private final GuiHandler gui;

    private int player;

    /**
     * Konstruktor pro vytvoření nové hry.
     *
     * @param gameMode režim hry ("local" nebo "remote")
     * @param gameClient instance klienta pro komunikaci se serverem (pouze pro režim "remote")
     */
    public GamePanel(GameSettings gameSettings, GuiHandler gui, String gameMode, GameClient gameClient) {
        this.gui = gui;
        this.gameClient = gameClient;
        this.gameMode = gameMode;

        this.setPreferredSize(new Dimension(gameSettings.getGamePlan().getWidth() * GameSettings.UNIT_SIZE, gameSettings.getGamePlan().getHeight() * GameSettings.UNIT_SIZE));
        this.setFocusable(true);
        this.game = new Game(gameSettings);

        this.gameLogic = new GameLogicHandler(game);
        this.gameHandler = new LocalGameHandler(this, gameLogic);
        this.gameLogic.setGameHandler(gameHandler);

        this.gameLogic.startGame();
        //this.game.setSidebarPanel(gui.getSidebar());

        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);

        this.requestFocusInWindow();
    }

    /**
     * Konstruktor pro aktualizaci existující hry.
     *
     * @param game instance aktuální hry
     * @param gameClient instance klienta pro komunikaci se serverem
     * @param player ID hráče
     */
    public GamePanel(GuiHandler gui, Game game, GameClient gameClient, int player) {
        this.gui = gui;
        this.game = game;
        this.gameClient = gameClient;
        this.gameHandler = new RemoteGameHandler(gameClient);
        this.player = player;

        this.game.setGameHandler(gameHandler);
        //this.game.setSidebarPanel(gui.getSidebar());

        this.setPreferredSize(new Dimension(game.getGamePlan().getWidth() * GameSettings.UNIT_SIZE, game.getGamePlan().getHeight() * GameSettings.UNIT_SIZE));
        this.setFocusable(true);

        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);

        this.requestFocusInWindow();
    }

    /**
     * Metoda pro aktualizaci stavu hry.
     *
     * @param game instance aktualizované hry
     */
    public void updateGame(Game game) {
        this.game = game;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null) {
            game.paint(g, this);
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ("remote".equals(gameMode)) {
                gameHandler.sendPlayerAction(player, key);
            } else {
                gameHandler.sendPlayerAction(0, key);
                if (key == KeyEvent.VK_R) {
                    //game.restart();
                }
            }
        }
    }

    public Game getGame() {
        return game;
    }

    /**
     * Zobrazí dialogové okno s informací o konci hry.
     */
    public void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverFrame(gui).setVisible(true));
    }
}
