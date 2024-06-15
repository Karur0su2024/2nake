package org.example.objects;

import org.example.GameSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.File;
import java.io.IOException;

public class Food {
    private final int x;
    private final int y;
    private final int points;
    private Color color;

    public Food(int X, int Y){
        this.x = X;
        this.y = Y;

        Random r = new Random();
        if(r.nextInt(30)-5 > 0){
            this.points = 1;
        }
        else {
            this.points = -1;
        }

        setColor();

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void paint(Graphics g, JPanel panel){
        g.setColor(this.color);
        if(this.points < 1){
            this.color = Color.red;
        }
        else {
            this.color = Color.cyan;
        }

        g.fillOval(x*GameSettings.UNIT_SIZE + 3, y*GameSettings.UNIT_SIZE + 3, GameSettings.UNIT_SIZE - 6, GameSettings.UNIT_SIZE - 6);
    }

    private void setColor(){
        if(this.points < 1){
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
