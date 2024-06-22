package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.GameSettings;

import java.awt.*;

/**
 * Třída reprezentující překážku v herním prostředí.
 */
public class Obstacle {
    int x;
    int y;

    /**
     * Konstruktor pro vytvoření nové instance překážky se zadanou pozicí.
     *
     * @param X x-ová souřadnice překážky
     * @param Y y-ová souřadnice překážky
     */
    public Obstacle(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    /**
     * Prázdný konstruktor pro serializaci pomocí Jackson ObjectMapper.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Obstacle(){

    }

    /**
     * Metoda pro získání x-ové souřadnice překážky.
     *
     * @return x-ová souřadnice překážky
     */
    public int getX() {
        return x;
    }

    /**
     * Metoda pro získání y-ové souřadnice překážky.
     *
     * @return y-ová souřadnice překážky
     */
    public int getY() {
        return y;
    }

    /**
     * Metoda pro vykreslení překážky na zadaném grafickém kontextu.
     *
     * @param g grafický kontext, na kterém se má překážka vykreslit
     */
    public void paint(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(this.x * GameSettings.UNIT_SIZE, this.y * GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
    }

    /**
     * Metoda pro nastavení x-ové souřadnice překážky.
     *
     * @param x nová x-ová souřadnice překážky
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Metoda pro nastavení y-ové souřadnice překážky.
     *
     * @param y nová y-ová souřadnice překážky
     */
    public void setY(int y) {
        this.y = y;
    }
}
