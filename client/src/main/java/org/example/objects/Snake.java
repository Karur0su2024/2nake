package org.example.objects;

public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private int applesEaten = 0;
    private int x[];
    private int y[];



    public Snake(int gameUnits){
        x = new int[gameUnits];
        y = new int[gameUnits];
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public void eat(){
        this.bodyParts++;
        this.applesEaten++;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public int getX(int i) {
        return x[i];
    }

    public int getY(int i) {
        return y[i];
    }

    public void setX(int i, int x) {
        this.x[i] = x;
    }

    public void setY(int i, int y) {
        this.y[i] = y;
    }




}
