package org.example.gui;

import org.example.*;
import org.example.objects.GamePlan;

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
        this.gameLogic = new GameLogicHandler(new Game(gameSettings), gui);
        this.gameHandler = new LocalGameHandler(this, gameLogic);
        this.gameLogic.setGameHandler(gameHandler);
        this.gameLogic.startGame();
        setPanel(gameSettings);


    }

    public GamePanel(GuiHandler gui, Game game, GameClient gameClient, int player) {
        this.gui = gui;
        this.gameLogic.setGame(game);
        this.gameClient = gameClient;
        this.gameHandler = new RemoteGameHandler(gameClient);
        this.player = player;

        //setPanel(new GameSettings());
    }

    public void updateGame(Game game) {
        this.gameLogic.setGame(game);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameLogic.getGame() != null) {
            gameLogic.getGame().paint(g, this);
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


    public void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> new GameOverFrame(gui).setVisible(true));
    }

    private void setPanel(GameSettings gameSettings){
        this.setPreferredSize(new Dimension(gameSettings.getGamePlan().getWidth() * GameSettings.UNIT_SIZE, gameSettings.getGamePlan().getHeight() * GameSettings.UNIT_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow();
    }

    public GameLogicHandler getGameLogic() {
        return gameLogic;
    }
}
