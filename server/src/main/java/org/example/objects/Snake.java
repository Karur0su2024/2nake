package org.example.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.GameSettings;
import org.example.gui.Sidebar;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Třída reprezentující hada v herním prostředí.
 */
public class Snake {
    private char direction = 'R'; // Směr hada ('U' pro nahoru, 'D' pro dolů, 'L' pro doleva, 'R' pro doprava)
    private int bodyParts = 6; // Počet částí hada
    private int[] x; // Pole obsahující x-ové souřadnice jednotlivých částí hada
    private int[] y; // Pole obsahující y-ové souřadnice jednotlivých částí hada
    private String name;

    @JsonIgnore
    private int maxSize; // Maximální velikost hada

    private int speed; // Rychlost hada


    /**
     * Konstruktor pro vytvoření hada s určitou maximální velikostí a počátečním počtem částí.
     *
     * @param maxSize  maximální velikost hada
     * @param bodySize počáteční počet částí hada
     */


    public Snake(int maxSize, int bodySize, int x, int y, char dir, String name) {
        this.maxSize = maxSize;
        this.x = new int[maxSize];
        this.y = new int[maxSize];
        direction = dir;
        setHead(x, y);
        bodyParts = bodySize;
        setSpeed();
        this.name = name;
    }

    @JsonCreator
    public Snake(
            @JsonProperty("direction") char direction,
            @JsonProperty("bodyParts") int bodyParts,
            @JsonProperty("x") int[] x,
            @JsonProperty("y") int[] y,
            @JsonProperty("name") String name,
            @JsonProperty("speed") int speed) {
        this.direction = direction;
        this.bodyParts = bodyParts;
        this.x = x;
        this.y = y;
        this.name = name;
        this.speed = speed;
        this.maxSize = x.length*y.length;
    }

    public Snake(String name){
        this.name = name;
    }


    /**
     * Prázdný konstruktor pro serializaci pomocí Jackson ObjectMapper.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Snake() {
        x = new int[maxSize];
        y = new int[maxSize];
    }

    public Snake(Snake snake){
        this.name = snake.name;
    }

    /**
     * Konstruktor pro vytvoření hada s určitými x-ovými a y-ovými souřadnicemi.
     *
     * @param x pole x-ových souřadnic částí hada
     * @param y pole y-ových souřadnic částí hada
     */
    public Snake(int[] x, int[] y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Metoda pro získání počtu částí hada.
     *
     * @return počet částí hada
     */
    public int getBodyParts() {
        return bodyParts;
    }

    /**
     * Metoda pro zvýšení počtu částí hada o určitý počet bodů.
     * Pokud je počet částí hada 1 a počet bodů je menší než 0, had neztratí tělo.
     *
     * @param points počet bodů, o který se má zvýšit počet částí hada
     */
    public void eat(int points) {
        if (!(bodyParts == 1 && points < 0)) {
            this.bodyParts = this.bodyParts + points;
        }
        if (bodyParts < 1) {
            bodyParts = 1;
        }

        setSpeed();

        if (speed < 1) {
            speed = 1;
        }

        //this.sidebarPanel.setScores();
    }

    /**
     * Metoda pro nastavení směru hada.
     *
     * @param direction nový směr hada ('U' pro nahoru, 'D' pro dolů, 'L' pro doleva, 'R' pro doprava)
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Metoda pro získání směru hada.
     *
     * @return směr hada
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Metoda pro pohyb hada podle jeho směru.
     * Každá část hada se posune na místo předchozí části.
     */
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - 1;
                break;
            case 'D':
                y[0] = y[0] + 1;
                break;
            case 'L':
                x[0] = x[0] + 1;
                break;
            case 'R':
                x[0] = x[0] - 1;
                break;
        }
    }

    /**
     * Metoda pro vykreslení hada na zadaném grafickém kontextu.
     *
     * @param g     grafický kontext, na kterém se má had vykreslit
     * @param panel panel, na kterém se had vykresluje
     */
    public void paint(Graphics g, JPanel panel) {
        if (this.x != null && this.y != null && x.length > 0 && y.length > 0) {
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.yellow); // Barva hlavy hada
                } else {
                    g.setColor(new Color(221, 221, 119)); // Barva těla hada
                }

                g.fillRect(x[i] * GameSettings.UNIT_SIZE, y[i] * GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
            }
        } else {
            // Handle the case when x or y is null (optional based on your logic)
        }
    }

    /**
     * Metoda pro získání rychlosti hada.
     *
     * @return rychlost hada
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Metoda pro nastavení rychlosti hada na základě počtu jeho částí.
     * Čím více částí hada, tím nižší je jeho rychlost.
     */
    public void setSpeed() {
        this.speed = (90 - (bodyParts / 10));
    }

    /**
     * Metoda pro nastavení počtu částí hada.
     *
     * @param bodyParts nový počet částí hada
     */
    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }

    /**
     * Metoda pro nastavení x-ových souřadnic částí hada.
     *
     * @param x pole x-ových souřadnic částí hada
     */
    public void setX(int[] x) {
        this.x = x;
    }

    /**
     * Metoda pro nastavení y-ových souřadnic částí hada.
     *
     * @param y pole y-ových souřadnic částí hada
     */
    public void setY(int[] y) {
        this.y = y;
    }

    /**
     * Metoda pro nastavení rychlosti hada.
     *
     * @param speed nová rychlost hada
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Metoda pro získání x-ových souřadnic částí hada.
     *
     * @return pole x-ových souřadnic částí hada
     */
    public int[] getX() {
        return x;
    }

    /**
     * Metoda pro získání y-ových souřadnic částí hada.
     *
     * @return pole y-ových souřadnic částí hada
     */
    public int[] getY() {
        return y;
    }


    /**
     * Metoda pro nastavení hlavy hada na zadané x-ové a y-ové souřadnice.
     *
     * @param x x-ová souřadnice hlavy hada
     * @param y y-ová souřadnice hlavy hada
     */
    public void setHead(int x, int y) {
        this.x[0] = x;
        this.y[0] = y;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "direction=" + direction +
                ", bodyParts=" + bodyParts +
                ", x=" + Arrays.toString(x) +
                ", y=" + Arrays.toString(y) +
                ", name='" + name + '\'' +
                ", maxSize=" + maxSize +
                ", speed=" + speed +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
