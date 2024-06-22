package org.example.ui;

import org.example.Game;
import org.example.GameClient;
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

    private final MainMenuFrame menuFrame; // Hlavní menu aplikace

    /**
     * Konstruktor pro spuštění nové hry.
     *
     * @param players počet hráčů ve hře
     * @param menuFrame hlavní menu aplikace
     * @param width šířka herního plánu
     * @param height výška herního plánu
     * @param obstacles počet překážek ve hře
     * @param food počet jídel ve hře
     * @param size počáteční délka hada
     * @param length délka hry
     * @param gameMode herní režim (lokální nebo vzdálený)
     * @param gameClient klient pro vzdálenou hru
     */
    public GameFrame(int players, MainMenuFrame menuFrame, int width, int height, int obstacles, int food, int size, int length, String gameMode, GameClient gameClient) {
        this.menuFrame = menuFrame;
        this.setLayout(new BorderLayout());
        log.info("Spouštím hru");

        SidebarPanel sidebarPanel = new SidebarPanel(players);
        if ("remote".equals(gameMode)) {

        } else {
            GamePanel gamePanel = new GamePanel(players, width, height, obstacles, food, size, length, sidebarPanel, menuFrame, this, gameMode, gameClient);
            this.add(gamePanel, BorderLayout.CENTER);
        }

        this.add(sidebarPanel, BorderLayout.EAST);

        this.setTitle("2nake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuFrame.setVisible(true);
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
    public GameFrame(Game game, GameClient gameClient, MainMenuFrame menuFrame, int player) {
        this.menuFrame = menuFrame;
        this.setLayout(new BorderLayout());

        SidebarPanel sidebarPanel = new SidebarPanel(2); // Vytvoření bočního panelu pro 2 hráče
        GamePanel gamePanel = new GamePanel(menuFrame, sidebarPanel, this, game, gameClient, player);
        gameClient.setGamePanel(gamePanel); // Nastavení herního panelu v GameClient
        this.add(gamePanel, BorderLayout.CENTER);

        this.add(sidebarPanel, BorderLayout.EAST);

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
}
