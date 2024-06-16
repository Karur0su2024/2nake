package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.GameSettings;

import java.awt.*;

public class Obstacle {
    int x;
    int y;

    public Obstacle(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Obstacle(){

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(this.x * GameSettings.UNIT_SIZE, this.y * GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


}
