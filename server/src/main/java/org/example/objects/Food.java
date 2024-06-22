package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Třída reprezentující potravinu ve hře.
 */
public class Food {
    private int x = 0;
    private int y = 0;
    private int points = 0;

    private Color color;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Random r;

    /**
     * Konstruktor pro vytvoření instance potraviny se zadanými souřadnicemi.
     *
     * @param X souřadnice X potraviny
     * @param Y souřadnice Y potraviny
     */
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

    /**
     * Vrací souřadnici X potraviny.
     *
     * @return souřadnice X potraviny
     */
    public int getX() {
        return x;
    }

    /**
     * Vrací souřadnici Y potraviny.
     *
     * @return souřadnice Y potraviny
     */
    public int getY() {
        return y;
    }

    /**
     * Nastaví souřadnici X potraviny.
     *
     * @param x souřadnice X potraviny
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Nastaví souřadnici Y potraviny.
     *
     * @param y souřadnice Y potraviny
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Vrací bodovou hodnotu potraviny.
     *
     * @return bodová hodnota potraviny
     */
    public int getPoints() {
        return points;
    }

    /**
     * Nastaví bodovou hodnotu potraviny.
     *
     * @param points bodová hodnota potraviny
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Vrací barvu potraviny.
     *
     * @return barva potraviny
     */
    public Color getColor() {
        return color;
    }

    /**
     * Nastaví barvu potraviny podle její bodové hodnoty.
     */
    private void setColor(){
        if(this.points < 1){
            this.color = Color.red;
        }
        else {
            this.color = Color.cyan;
        }
    }

    /**
     * Nastaví barvu potraviny.
     *
     * @param color barva potraviny
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Nastaví barvu potraviny podle její bodové hodnoty.
     *
     * @param r instance generátoru náhodných čísel
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setR(Random r) {
        this.r = r;
    }

    /**
     * Vrací instance generátoru náhodných čísel.
     *
     * @return instance generátoru náhodných čísel
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Random getR() {
        return r;
    }

    /**
     * Vrací textovou reprezentaci potraviny ve formátu: "Food[X,Y,Points]".
     *
     * @return textová reprezentace potraviny
     */
    @Override
    public String toString() {
        return "Food[" +
                "X=" + x +
                ", Y=" + y +
                ", Points=" + points +
                ']';
    }
}
