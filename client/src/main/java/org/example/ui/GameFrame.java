package org.example.ui;

import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        this.add(new Game());
        this.setTitle("Snake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
