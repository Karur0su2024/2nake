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
    private int x;
    private int y;
    private int points;
    private Color color;
    private Image image;

    public Food(int X, int Y){
        this.x = X;
        this.y = Y;
        try {
            File f = new File("client/src/images/food.png");
            System.out.println(f.getAbsoluteFile());
            //l.a(f.getCanonicalPath());
            image = ImageIO.read(f);
            image = image.getScaledInstance(GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            System.out.println("test" + ex);
        }


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
            //g.drawImage(image, x*GameSettings.UNIT_SIZE, x*GameSettings.UNIT_SIZE, panel);
        }

        //g.fillOval(x*);
        g.drawImage(image, x*GameSettings.UNIT_SIZE, y*GameSettings.UNIT_SIZE, panel);

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
