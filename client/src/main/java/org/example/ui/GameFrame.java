package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    MainMenuFrame menuFrame;

    GameFrame(int players, MainMenuFrame menuFrame, int width, int height, int obstacles, int food, int size, int length, String gameMode) {
        this.setLayout(new BorderLayout());
        SidebarPanel sidebarPanel = new SidebarPanel(players);
        this.add(new GamePanel(players, width, height, obstacles, food, size, length, sidebarPanel, menuFrame, this, gameMode), BorderLayout.CENTER);

        this.add(sidebarPanel, BorderLayout.EAST);

        this.menuFrame = menuFrame;
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
}
