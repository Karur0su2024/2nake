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
    private final GamePanel gamePanel;

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
     * @param game instance hry
     * @param gameClient klient pro hru
     * @param menuFrame hlavní menu aplikace
     * @param player pořadové číslo hráče
     */
    public GameWindow(Game game, GameClient gameClient, MainMenu menuFrame, int player) {
        this.gui = new GuiHandler(); // Opravit
        this.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar(2); // Vytvoření bočního panelu pro 2 hráče
        gamePanel = new GamePanel(gui, game, gameClient, player);
        gameClient.setGamePanel(gamePanel); // Nastavení herního panelu v GameClient
        this.add(gamePanel, BorderLayout.CENTER);

        this.add(sidebar, BorderLayout.EAST);

        this.setTitle("2nake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuFrame.setVisible(true); // Při zavření okna zobrazí hlavní menu
            }
        });
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
