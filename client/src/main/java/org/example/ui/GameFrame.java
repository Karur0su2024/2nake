package org.example.ui;

import org.example.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    MainMenuFrame menuFrame;


    GameFrame(int players, MainMenuFrame menuFrame, int width, int height){

        GamePanel gamePanel = new GamePanel(players, width, height);
        this.add(gamePanel);

        this.menuFrame = menuFrame;
        this.setTitle("Snake");
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
}
