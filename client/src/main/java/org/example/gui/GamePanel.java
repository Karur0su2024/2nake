package org.example.gui;

import org.example.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private GameLogicHandler gameLogic;


    private GameClient gameClient;
    private String gameMode;
    private final GameHandler gameHandler;
    private final GuiHandler gui;

    private int player;

    public GamePanel(GameSettings gameSettings, GuiHandler gui) {
        this.gui = gui;
        this.gameLogic = new GameLogicHandler(gameSettings, gui, gameSettings.getNoPlayers());
        this.gameHandler = new LocalGameHandler(this, gameLogic);
        this.gameLogic.setGameHandler(gameHandler);
        this.gameLogic.startGame();
        setPanel(gameSettings);
    }



    public GamePanel(GuiHandler gui, GameLogicHandler gameLogic, GameClient gameClient) {
        this.gui = gui;
        this.gameLogic = gameLogic;
        this.gameClient = gameClient;
        this.gameHandler = new RemoteGameHandler(gameClient);
        setPanel(gameLogic);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameLogic.getGame() != null) {
            gameLogic.paint(g, this);
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ("remote".equals(gameMode)) {
                gameHandler.sendPlayerAction(key);
            } else {
                gameHandler.sendPlayerAction(key);
                if (key == KeyEvent.VK_R) {
                    //game.restart();
                }
            }
        }
    }


    public void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverScreen(gui).setVisible(true));
    }

    private void setPanel(GameSettings gameSettings){
        this.setPreferredSize(new Dimension(gameSettings.getGamePlan().getWidth() * GameSettings.UNIT_SIZE, gameSettings.getGamePlan().getHeight() * GameSettings.UNIT_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow();
    }

    private void setPanel(GameLogicHandler gameLogic){
        this.setPreferredSize(new Dimension(gameLogic.getGame().getGamePlan().getWidth() * GameSettings.UNIT_SIZE, gameLogic.getGame().getGamePlan().getHeight() * GameSettings.UNIT_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow();
    }

    public GameLogicHandler getGameLogic() {
        return gameLogic;
    }
}
