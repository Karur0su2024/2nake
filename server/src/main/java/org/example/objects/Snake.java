package org.example.objects;

import org.example.GameSettings;
import org.example.ui.SidebarPanel;

import javax.swing.*;
import java.awt.*;

public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private char[] bodyPartsDirection;
    private int x[];
    private int y[];

    private int speed;
    SidebarPanel sidebarPanel;

    public Snake(int maxSize, int bodySize){
        this.sidebarPanel = sidebarPanel;
        x = new int[maxSize];
        y = new int[maxSize];
        bodyParts = bodySize;
        bodyPartsDirection = new char[maxSize];
        bodyPartsDirection[0] = 'R';

        setSpeed();

    }
    public int getBodyParts() {
        return bodyParts;
    }

    public void eat(int points){
        if(!(bodyParts == 1 && points < 0)){
            this.bodyParts = this.bodyParts+points;
        }
        if(bodyParts < 1){
            bodyParts = 1;
        }

        setSpeed();

        if(speed < 1){
            speed = 1;
        }

        this.sidebarPanel.setScores();
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

    public void setX(int x) {
        this.x[0] = x;
    }

    public void setY(int y) {
        this.y[0] = y;
    }

    public void move(){


        for(int i = bodyParts; i> 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0]-1;
                break;
            case 'D':
                y[0] = y[0]+1;
                break;
            case 'L':
                x[0] = x[0]-1;
                break;
            case 'R':
                x[0] = x[0]+1;
                break;
        }
    }

    public void paint(Graphics g, JPanel panel){
        for(int i = 0; i < bodyParts; i++) {
            if(i == 0){
                g.setColor(Color.yellow);
            }
            else {
                g.setColor(new Color(221, 221, 119));
            }

            g.fillRect(x[i]*GameSettings.UNIT_SIZE, y[i]*GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
        }
    }


    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed() {
        this.speed = (22 - (bodyParts / 7));
    }
}
