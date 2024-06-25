package org.example.gui;

import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Panel pro zobrazení bočního panelu s informacemi o skóre hráčů a zbývajícím čase hry.
 */
public class Sidebar extends JPanel {
    private final JLabel[] scoreLabel;
    private final JLabel timeLabel;
    /**
     * Konstruktor pro vytvoření bočního panelu s informacemi o skóre hráčů a času.
     *
     * @param players Počet hráčů ve hře
     */
    public Sidebar(int players) {
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


    /**
     * Metoda pro aktualizaci zobrazení skóre hráčů v bočním panelu.
     */
    public void setScores(List<Snake> snakes) {
        int i = 0;
        for (Snake snake : snakes) {
            scoreLabel[i].setText(snake.getName().replaceAll("_", " ") + ": " + snake.getBodyParts().size());
            i++;
        }
    }

    /**
     * Metoda pro aktualizaci zobrazení zbývajícího času hry v bočním panelu.
     */
    public void setTime(int time) {
        timeLabel.setText("Time left: " + time + "s");
    }
}
