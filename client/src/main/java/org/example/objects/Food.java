package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Food {
    private int x = 0;
    private int y = 0;
    private int points = 0;

    private Color color;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Random r;

    public Food(int X, int Y){
        this.x = X;
        this.y = Y;

        r = new Random();
        if(r.nextInt(30)-5 > 0){
            this.points = 1;
        }
        else {
            this.points = -1;
        }

        setColor();

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Food(){
        super();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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


    public Color getColor() {
        return color;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setColor(Color color) {
        this.color = color;
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



}
