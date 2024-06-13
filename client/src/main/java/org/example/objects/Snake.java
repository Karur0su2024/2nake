package org.example.objects;

import org.example.GameSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private int applesEaten = 0;
    private int x[];
    private int y[];
    private int score = 0;

    private Image head;
    private Image body;
    private Image tail;

    public Snake(int maxSize){
        x = new int[maxSize];
        y = new int[maxSize];

        try {
            head = ImageIO.read(new File("client/src/images/snake/head.png")).getScaledInstance(GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, Image.SCALE_DEFAULT);
            body = ImageIO.read(new File("client/src/images/snake/body.png")).getScaledInstance(GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, Image.SCALE_DEFAULT);
            tail = ImageIO.read(new File("client/src/images/snake/tail.png")).getScaledInstance(GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, Image.SCALE_DEFAULT);

        } catch (IOException ex) {
            System.out.println("test" + ex);
        }



    }
    public int getBodyParts() {
        return bodyParts;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public void eat(int points){
        if(!(bodyParts == 1 && points < 0)){
            this.bodyParts = this.bodyParts+points;
            this.score = this.score + points;
        }
        if(bodyParts < 1){
            bodyParts = 1;
        }

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

    public void setX(int x) {
        this.x[0] = x;
    }

    public void setY(int y) {
        this.y[0] = y;
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
                //g.setColor(Color.WHITE);
                g.drawImage(head, x[i]*GameSettings.UNIT_SIZE, y[i]*GameSettings.UNIT_SIZE, panel);
            }
            else {
                if(i == bodyParts-1){
                    g.drawImage(tail, x[i]*GameSettings.UNIT_SIZE, y[i]*GameSettings.UNIT_SIZE, panel);
                }
                else {
                    g.drawImage(body, x[i]*GameSettings.UNIT_SIZE, y[i]*GameSettings.UNIT_SIZE, panel);
                }
            }



            //g.fillRect(x[i]*GameSettings.UNIT_SIZE, y[i]*GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
        }
    }

    public Image rotatesnake(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        //g2.rotate(Math.toRadians(90), width / 2, height / 2);
        // g2.drawImage(img, null, 0, 0);

        // Return rotated buffer image
        return newImage;
    }


}
