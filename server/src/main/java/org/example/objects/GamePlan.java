package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.awt.*;

/**
 * Třída reprezentující herní plán s definovanou šířkou a výškou.
 */
public class GamePlan {
    private int width = 45;
    private int height = 30;

    @JsonIgnore
    private final Color[] colors = new Color[2];

    /**
     * Konstruktor pro vytvoření instance herního plánu se zadanou šířkou a výškou.
     *
     * @param width šířka herního plánu
     * @param height výška herního plánu
     */
    public GamePlan(int width, int height){
        colors[0] = new Color(134, 182, 82);
        colors[1] = new Color(136, 189, 83);

        this.width = width;
        this.height = height;
    }

    /**
     * Vrací šířku herního plánu.
     *
     * @return šířka herního plánu
     */
    public int getWidth() {
        return width;
    }

    /**
     * Nastaví šířku herního plánu.
     *
     * @param width šířka herního plánu
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Nastaví výšku herního plánu.
     *
     * @param height výška herního plánu
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Vrací výšku herního plánu.
     *
     * @return výška herního plánu
     */
    public int getHeight() {
        return height;
    }

    /**
     * Vrací pole barev používaných pro zobrazení herního plánu.
     *
     * @return pole barev herního plánu
     */
    @JsonIgnore
    public Color[] getColors() {
        return colors;
    }
}
