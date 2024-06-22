package org.example.ui;

import org.example.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel pro zobrazení bočního panelu s informacemi o skóre hráčů a zbývajícím čase hry.
 */
public class SidebarPanel extends JPanel implements ActionListener {
    private final JLabel[] scoreLabel;
    private final JLabel timeLabel;
    private Game game;

    /**
     * Konstruktor pro vytvoření bočního panelu s informacemi o skóre hráčů a času.
     *
     * @param players Počet hráčů ve hře
     */
    public SidebarPanel(int players) {
        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(200, 600));
        setBackground(Color.LIGHT_GRAY);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        // Skóre hráčů
        scoreLabel = new JLabel[players];
        for (int i = 0; i < players; i++) {
            scoreLabel[i] = new JLabel("");

            scoreLabel[i].setFont(labelFont);
            add(scoreLabel[i]);
        }
        // Čas hry
        timeLabel = new JLabel("Time: 0");
        timeLabel.setFont(labelFont);
        add(timeLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Prázdná metoda pro implementaci rozhraní ActionListener
    }

    /**
     * Metoda pro nastavení instance hry, která poskytuje informace o skóre hráčů.
     *
     * @param game Instance hry, která obsahuje informace o hráčích
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Metoda pro aktualizaci zobrazení skóre hráčů v bočním panelu.
     */
    public void setScores() {
        for (int i = 0; i < game.getSnakes().length; i++) {
            scoreLabel[i].setText("Snake " + (i + 1) + ": " + game.getSnakes()[i].getBodyParts());
        }
    }

    /**
     * Metoda pro aktualizaci zobrazení zbývajícího času hry v bočním panelu.
     */
    public void setTime() {
        timeLabel.setText("Time left: " + game.getTime() + "s");
    }
}
