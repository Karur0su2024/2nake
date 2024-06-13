package org.example.ui;

import org.example.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GameScore extends JPanel {

    public GameScore(int width, int height){
        this.setPreferredSize(new Dimension(width, 200));
        this.setLocation(0, 500);
        this.setFocusable(false);
    }


}
