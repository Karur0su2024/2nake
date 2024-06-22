package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.ui.SidebarPanel;

import java.awt.*;

/**
 * Třída reprezentující hada ve hře.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Snake {
    private char direction = 'R';
    private int bodyParts = 6;
    private int[] x;
    private int[] y;
    private int speed;

    @JsonIgnore
    private SidebarPanel sidebarPanel;

    /**
     * Výchozí konstruktor pro třídu Snake, potřebný pro Jackson.
     */
    public Snake() {
        // Default constructor needed for Jackson
    }

    /**
     * Konstruktor pro vytvoření hada se zadanými polohami x a y.
     *
     * @param x pole obsahující souřadnice x hada
     * @param y pole obsahující souřadnice y hada
     */
    public Snake(int[] x, int[] y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Konstruktor pro vytvoření hada s maximální velikostí a počtem tělíček.
     *
     * @param maxSize maximální velikost hada
     * @param bodySize počet tělíček hada
     */
    public Snake(int maxSize, int bodySize) {
        this.x = new int[maxSize / 2];
        this.y = new int[maxSize / 2];
        bodyParts = bodySize;
        setSpeed();
    }

    /**
     * Nastaví hlavu hada na zadané souřadnice x a y.
     *
     * @param x souřadnice x hlavy hada
     * @param y souřadnice y hlavy hada
     */
    public void setHead(int x, int y) {
        this.x[0] = x;
        this.y[0] = y;
    }

    /**
     * Zvětší počet tělíček hada o zadaný počet bodů, pokud to není jednobodový had.
     * Nastaví rychlost hada a aktualizuje skóre na panelu.
     *
     * @param points počet bodů, které had získá
     */
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

        if (sidebarPanel != null) {
            sidebarPanel.setScores();
        }
    }

    /**
     * Nastaví směr hada.
     *
     * @param direction směr hada ('U', 'D', 'L', 'R')
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Vrací aktuální směr hada.
     *
     * @return směr hada ('U', 'D', 'L', 'R')
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Vrací pole souřadnic x hada.
     *
     * @return pole souřadnic x
     */
    public int[] getX() {
        return x;
    }

    /**
     * Vrací pole souřadnic y hada.
     *
     * @return pole souřadnic y
     */
    public int[] getY() {
        return y;
    }

    /**
     * Nastaví pole souřadnic x hada.
     *
     * @param x pole souřadnic x
     */
    public void setX(int[] x) {
        this.x = x;
    }

    /**
     * Nastaví pole souřadnic y hada.
     *
     * @param y pole souřadnic y
     */
    public void setY(int[] y) {
        this.y = y;
    }

    /**
     * Pohne hadem podle aktuálního směru.
     */
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

    /**
     * Vrací rychlost hada.
     *
     * @return rychlost hada
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Nastaví rychlost hada na základě počtu tělíček.
     */
    public void setSpeed() {
        this.speed = (22 - (bodyParts / 7));
    }

    /**
     * Vrací panel bočního menu, který zobrazuje skóre a další informace o hře.
     * Pro server část nepotřebný ale je tu aby se mi nerozpadl server
     *
     * @return panel bočního menu
     */
    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    /**
     * Nastaví panel bočního menu pro hada.
     * Pro server část nepotřebný ale je tu aby se mi nerozpadl server
     *
     * @param sidebarPanel panel bočního menu
     */
    public void setSidebarPanel(SidebarPanel sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    /**
     * Vrací počet tělíček hada.
     *
     * @return počet tělíček
     */
    public int getBodyParts() {
        return bodyParts;
    }

    /**
     * Nastaví počet tělíček hada.
     *
     * @param bodyParts počet tělíček
     */
    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }
}
