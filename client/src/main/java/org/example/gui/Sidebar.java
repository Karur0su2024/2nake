package org.example.gui;

import org.example.objects.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel for displaying the sidebar with player scores and remaining game time information.
 */
public class Sidebar extends JPanel {
    private final JLabel[] scoreLabels;
    private final JLabel timeLabel;

    /**
     * Constructor to create the sidebar with player scores and time information.
     *
     * @param players The number of players in the game
     */
    public Sidebar(int players) {
        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(200, 600));
        setBackground(Color.LIGHT_GRAY);

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Player scores
        scoreLabels = new JLabel[players];
        for (int i = 0; i < players; i++) {
            scoreLabels[i] = new JLabel("");
            scoreLabels[i].setFont(labelFont);
            add(scoreLabels[i]);
        }

        // Game time
        timeLabel = new JLabel("Time: 0");
        timeLabel.setFont(labelFont);
        add(timeLabel);
    }

    /**
     * Method to update the display of player scores in the sidebar.
     */
    public void setScores(List<Snake> snakes) {
        int i = 0;
        for (Snake snake : snakes) {
            scoreLabels[i].setText(snake.getName().replace("_", " ") + ": " + snake.getBodyParts().size());
            i++;
        }
    }

    /**
     * Method to update the display of the remaining game time in the sidebar.
     */
    public void setTime(int time) {
        timeLabel.setText("Time left: " + time + "s");
    }
}
