package org.example.objects;

import org.example.GameSettings;

public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private int applesEaten = 0;
    private int x[];
    private int y[];
    private int score = 0;


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
        this.score++;
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

    public int getScore() {
        return score;
    }


    public void move(){
        for(int i = bodyParts; i> 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0]-GameSettings.UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0]+GameSettings.UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0]-GameSettings.UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+GameSettings.UNIT_SIZE;
                break;
        }
    }




}
