package org.example.ui;
import org.example.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidebarPanel extends JPanel implements ActionListener {
        private JLabel[] scoreLabel;
        private JLabel timeLabel;
        private Game game;

        public SidebarPanel(int players) {
            setLayout(new GridLayout(10, 1));
            setPreferredSize(new Dimension(200, 600));
            setBackground(Color.LIGHT_GRAY);

            Font labelFont = new Font("Arial", Font.BOLD, 16);
            // Score label
            scoreLabel = new JLabel[players];
            for(int i = 0; i < players; i++){
                scoreLabel[i] = new JLabel("");

                scoreLabel[i].setFont(labelFont);
                add(scoreLabel[i]);
            }
            timeLabel = new JLabel("Time: 0");
            timeLabel.setFont(labelFont);
            add(timeLabel);

        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setGame(Game game){
            this.game = game;
    }

    public void setScores(){
            for(int i = 0; i < game.getSnakes().length; i++){
                scoreLabel[i].setText("Snake " + (int)(i+1) + ": " + game.getSnakes()[i].getBodyParts());
            }
    }

    public void setTime(){
        timeLabel.setText("Time left: " + game.getTime() + "s");
    }
}
