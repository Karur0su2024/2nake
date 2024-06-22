package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.CustomColorDeserializer;
import org.example.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


/**
 * Třída reprezentující jídlo ve hře.
 */
public class Food {
    private int x = 0;
    private int y = 0;
    private int points = 0;

    @JsonDeserialize(using = CustomColorDeserializer.class)
    private Color color;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Random r;

    /**
     * Konstruktor pro vytvoření nové instance jídla na zadaných souřadnicích.
     *
     * @param X X souřadnice jídla
     * @param Y Y souřadnice jídla
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
     * Prázdný konstruktor pro serializaci pomocí Jackson ObjectMapper.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Food(){
        super();
    }

    /**
     * Metoda pro získání X souřadnice jídla.
     *
     * @return X souřadnice jídla
     */
    public int getX() {
        return x;
    }

    /**
     * Metoda pro získání Y souřadnice jídla.
     *
     * @return Y souřadnice jídla
     */
    public int getY() {
        return y;
    }

    /**
     * Metoda pro získání bodů, které jídlo poskytuje.
     *
     * @return počet bodů, které jídlo poskytuje
     */
    public int getPoints() {
        return points;
    }

    /**
     * Metoda pro získání barvy jídla.
     *
     * @return barva jídla
     */
    public Color getColor() {
        return color;
    }

    /**
     * Metoda pro nastavení X souřadnice jídla.
     *
     * @param x nová X souřadnice jídla
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Metoda pro nastavení Y-ové souřadnice jídla.
     *
     * @param y nová Y-ová souřadnice jídla
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Metoda pro nastavení bodů, které jídlo poskytuje.
     *
     * @param points nový počet bodů, které jídlo poskytuje
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Metoda pro nastavení barvy jídla.
     *
     * @param color nová barva jídla
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Metoda pro nastavení barvy jídla podle hodnoty.
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
     * Metoda pro vykreslení jídla na zadaném grafickém kontextu.
     *
     * @param g grafický kontext, na který se má jídlo vykreslit
     * @param panel panel, na kterém se má jídlo vykreslit
     */
    public void paint(Graphics g, JPanel panel){
        g.setColor(this.color);
        g.fillOval(x * GameSettings.UNIT_SIZE + 3, y * GameSettings.UNIT_SIZE + 3, GameSettings.UNIT_SIZE - 6, GameSettings.UNIT_SIZE - 6);
    }
}
