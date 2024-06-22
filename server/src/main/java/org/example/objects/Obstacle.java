package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


}
