package org.example.ui;

import javax.swing.*;
import org.example.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Okno zobrazené po ukončení hry. Zobrazuje možnosti pro restart, nastavení a ukončení hry.
 */
public class GameOverFrame extends JFrame implements ActionListener {

    /**
     * Konstruktor pro vytvoření okna po ukončení hry.
     *
     * @param gamePanel herní panel, ve kterém probíhala hra
     * @param mainMenuFrame hlavní menu aplikace
     * @param gameFrame instance hlavního herního okna
     */
    public GameOverFrame(GamePanel gamePanel, MainMenuFrame mainMenuFrame, GameFrame gameFrame) {
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
                gamePanel.getGame().restart(); // Restartovat hru
                dispose(); // Zavřít okno po restartu
            }
        });
        buttonPanel.add(restartButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otevřít okno s nastavením
                new SettingsFrame(mainMenuFrame, gamePanel.getGame().getPlayers());
                gameFrame.dispose(); // Zavřít hlavní herní okno
                dispose(); // Zavřít okno po ukončení
            }
        });
        buttonPanel.add(settingsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.dispose(); // Zavřít hlavní herní okno
                dispose(); // Zavřít okno po ukončení
                mainMenuFrame.setVisible(true); // Zobrazit hlavní menu
            }
        });
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implementace metody z rozhraní ActionListener (není potřeba v této třídě)
    }
}
