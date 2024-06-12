package org.example.objects;

import org.example.GameSettings;

import java.awt.*;
import java.util.Random;

public class Food {
    private int X;
    private int Y;
    private int points;
    private Color color;

    public Food(int X, int Y){
        this.X = X;
        this.Y = Y;
        Random r = new Random();
        this.points = r.nextInt(6)-3;
        if(this.points == 0){
            this.points++;
        }

        setColor();



    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void paint(Graphics g, int UNIT_SIZE){
        g.setColor(this.color);
        g.fillOval(this.X, this.Y, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
    }

    private void setColor(){
        if(this.points < -1){
            this.color = Color.red;
        }
        else {
            this.color = Color.cyan;
        }
    }

    public int getPoints() {
        return points;
    }
}
