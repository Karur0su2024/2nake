package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.GameSettings;

import java.awt.*;

/**
 * Třída reprezentující herní plán, na kterém se hra odehrává.
 */
public class GamePlan {
    private int width = 45;
    private int height = 30;

    @JsonIgnore
    private final Color[] colors = new Color[2];

    /**
     * Konstruktor pro vytvoření nové instance herního plánu s určenou šířkou a výškou.
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
     * Prázdný konstruktor pro serializaci pomocí Jackson ObjectMapper.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public GamePlan(){

    }

    /**
     * Metoda pro vykreslení herního plánu na zadaném grafickém kontextu.
     *
     * @param g grafický kontext, na kterém se má herní plán vykreslit
     */
    public void paint(Graphics g){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if((i+j)%2 == 1){
                    g.setColor(colors[0]);
                }
                else {
                    g.setColor(colors[1]);
                }
                g.fillRect(i* GameSettings.UNIT_SIZE, j*GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
            }
        }
    }

    /**
     * Metoda pro získání šířky herního plánu.
     *
     * @return šířka herního plánu
     */
    public int getWidth() {
        return width;
    }

    /**
     * Metoda pro nastavení šířky herního plánu.
     *
     * @param width nová šířka herního plánu
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Metoda pro nastavení výšky herního plánu.
     *
     * @param height nová výška herního plánu
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Metoda pro získání výšky herního plánu.
     *
     * @return výška herního plánu
     */
    public int getHeight() {
        return height;
    }

    /**
     * Metoda pro získání pole barev, které se používají pro vykreslení herního plánu.
     *
     * @return pole barev herního plánu
     */
    public Color[] getColors() {
        return colors;
    }
}
