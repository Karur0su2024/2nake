package org.example.objects;

import org.example.GameSettings;

import java.awt.*;

public class Obstacle {
    int X;
    int Y;

    public Obstacle(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(this.X, this.Y, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
    }
}
