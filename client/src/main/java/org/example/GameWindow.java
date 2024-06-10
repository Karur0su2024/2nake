package org.example;

import javax.swing.*;

public class GameWindow extends JFrame {

    GameWindow(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }





}
