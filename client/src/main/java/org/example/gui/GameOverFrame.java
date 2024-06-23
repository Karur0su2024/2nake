package org.example.gui;

import org.example.GuiHandler;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Okno zobrazené po ukončení hry. Zobrazuje možnosti pro restart, nastavení a ukončení hry.
 */
public class GameOverFrame extends JFrame {

    /**
     * Konstruktor pro vytvoření okna po ukončení hry.
     *
     */
    public GameOverFrame(GuiHandler gui) {
        setTitle("Game Over");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Game Over", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getGameFrame().getGamePanel().getGame().restart(); // Restartovat hru
                dispose(); // Zavřít okno po restartu
            }
        });
        buttonPanel.add(restartButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otevřít okno s nastavením
                new SettingsFrame(gui, gui.getGameFrame().getGamePanel().getGame().getPlayers());
                gui.closeGameFrame();
                dispose(); // Zavřít okno po ukončení
            }
        });
        buttonPanel.add(settingsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.closeGameFrame();
                dispose(); // Zavřít okno po ukončení
                gui.toggleMainMenu();
            }
        });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
