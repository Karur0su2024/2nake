package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.GameSettings;
import org.example.ui.SidebarPanel;

import javax.swing.*;
import java.awt.*;

public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private int x[];
    private int y[];

    @JsonIgnore
    private int maxSize;

    private int speed;

    @JsonIgnore
    SidebarPanel sidebarPanel;

    public Snake(int maxSize, int bodySize){
        //this.sidebarPanel = sidebarPanel;
        this.maxSize = maxSize;
        x = new int[maxSize];
        y = new int[maxSize];

        bodyParts = bodySize;

        setSpeed();

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Snake(){
        x = new int[maxSize];
        y = new int[maxSize];
    }

    public Snake(int[] x, int[] y) {
        this.x = x;
        this.y = y;
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

        //this.sidebarPanel.setScores();
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
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
        if (this.x != null && this.y != null && x.length > 0 && y.length > 0) {
            for(int i = 0; i < bodyParts; i++) {
                if(i == 0){
                    g.setColor(Color.yellow);
                }
                else {
                    g.setColor(new Color(221, 221, 119));
                }

                g.fillRect(x[i]*GameSettings.UNIT_SIZE, y[i]*GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
            }
        } else {
            // Handle the case when x or y is null (optional based on your logic)
        }

    }


    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed() {
        this.speed = (22 - (bodyParts / 7));
    }

    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSidebarPanel(SidebarPanel sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public void setHead(int x, int y){
        this.x[0] = x;
        this.y[0] = y;
    }

}
