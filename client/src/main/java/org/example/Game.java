package org.example;

import javax.swing.*;

public class Game extends JFrame {

    Game(){
        this.add(new Panel());
        this.setTitle("Snake");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }



}
