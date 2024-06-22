package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.gui.Sidebar;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private int[] x;
    private int[] y;


    private int speed;

    @JsonIgnore
    private Sidebar sidebarPanel;

    public Snake() {
        // Default constructor needed for Jackson
    }



    public Snake(int[] x, int[] y) {
        this.x = x;
        this.y = y;
    }

    public Snake(int maxSize, int bodySize) {
        this.x = new int[maxSize/2];
        this.y = new int[maxSize/2];


        bodyParts = bodySize;
        setSpeed();
    }
    public void setHead(int x, int y){
        this.x[0] = x;
        this.y[0] = y;
    }

    // Getters and setters

    public int getBodyParts() {
        return bodyParts;
    }

    public void eat(int points) {
        if (!(bodyParts == 1 && points < 0)) {
            this.bodyParts += points;
        }
        if (bodyParts < 1) {
            bodyParts = 1;
        }

        setSpeed();

        if (speed < 1) {
            speed = 1;
        }

        if (sidebar != null) {
            sidebar.setScores();
        }
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public void move() {
        for (int i = bodyParts - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0]--;
                break;
            case 'D':
                y[0]++;
                break;
            case 'L':
                x[0]--;
                break;
            case 'R':
                x[0]++;
                break;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed() {
        this.speed = (22 - (bodyParts / 7));
    }

    public Sidebar getSidebarPanel() {
        return sidebar;
    }

    public void setSidebarPanel(Sidebar sidebarPanel) {
        this.sidebar = sidebarPanel;
    }

}
