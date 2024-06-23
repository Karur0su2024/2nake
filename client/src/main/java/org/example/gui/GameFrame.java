package org.example.gui;

import org.example.Game;
import org.example.GameClient;
import org.example.GuiHandler;
import org.example.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Okno aplikace pro zobrazení hry. Obsahuje herní panel a boční panel s informacemi.
 */
public class GameFrame extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private final GuiHandler gui;
    private final GamePanel gamePanel;


    /**
     * Konstruktor pro spuštění nové hry.
     *
     * @param players počet hráčů ve hře
     * @param width šířka herního plánu
     * @param height výška herního plánu
     * @param obstacles počet překážek ve hře
     * @param food počet jídel ve hře
     * @param size počáteční délka hada
     * @param length délka hry
     * @param gameMode herní režim (lokální nebo vzdálený)
     * @param gameClient klient pro vzdálenou hru
     */
    public GameFrame(int players, GuiHandler gui, int width, int height, int obstacles, int food, int size, int length, String gameMode, GameClient gameClient) {
        this.gui = gui;
        gui.setGameFrame(this);
        this.setLayout(new BorderLayout());
        log.info("Spouštím hru");

        gui.setSidebar(new Sidebar(players));

        gamePanel = new GamePanel(players, width, height, obstacles, food, size, length, gui, gameMode, gameClient);
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
    public GameFrame(Game game, GameClient gameClient, MainMenu menuFrame, int player) {
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
