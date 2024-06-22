package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Třída reprezentující překážku ve hře se souřadnicemi x a y.
 */
public class Obstacle {
    private int x;
    private int y;

    /**
     * Konstruktor pro vytvoření překážky se zadanými souřadnicemi x a y.
     *
     * @param x souřadnice x překážky
     * @param y souřadnice y překážky
     */
    public Obstacle(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Vrací souřadnici x překážky.
     *
     * @return souřadnice x
     */
    public int getX() {
        return x;
    }

    /**
     * Vrací souřadnici y překážky.
     *
     * @return souřadnice y
     */
    public int getY() {
        return y;
    }

    /**
     * Nastaví souřadnici x překážky.
     *
     * @param x souřadnice x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Nastaví souřadnici y překážky.
     *
     * @param y souřadnice y
     */
    public void setY(int y) {
        this.y = y;
    }
}
