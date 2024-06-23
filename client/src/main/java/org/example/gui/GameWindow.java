package org.example.gui;

import org.example.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Okno aplikace pro zobrazení hry. Obsahuje herní panel a boční panel s informacemi.
 */
public class GameWindow extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private final GuiHandler gui;
    private GamePanel gamePanel;

    public GameWindow(GameSettings gameSettings, GuiHandler gui) {
        log.info("Spouštím hru...");

        this.gui = gui;
        gui.setGameFrame(this);
        gui.setSidebar(new Sidebar(gameSettings.getNoPlayers()));
        this.setLayout(new BorderLayout());

        gamePanel = new GamePanel(gameSettings, gui);

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(gui.getSidebar(), BorderLayout.EAST);
        this.setTitle("2nake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.toggleMainMenu();
            }
        });
    }

    /**
     * Konstruktor pro aktualizaci existující hry.
     *
     */
    public GameWindow(GameLogicHandler gameLogic, GameClient client, GuiHandler gui) {
        this.gui = gui;
        gui.setSidebar(new Sidebar(2));
        this.setLayout(new BorderLayout());


        this.gamePanel = new GamePanel(gui, gameLogic, client);





        this.add(gamePanel, BorderLayout.CENTER);
        this.add(gui.getSidebar(), BorderLayout.EAST);

        this.setTitle("2nake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gui.getMainMenu().setVisible(true);
                client.sendMessage("left " + client.getName());
            }
        });
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
