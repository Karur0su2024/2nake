package org.example.objects;

import org.example.GameSettings;

import java.awt.*;

public class Apple {
    private int X;
    private int Y;

    public Apple(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void paint(Graphics g, int UNIT_SIZE){
        g.setColor(Color.red);
        g.fillOval(this.X, this.Y, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
    }


}
