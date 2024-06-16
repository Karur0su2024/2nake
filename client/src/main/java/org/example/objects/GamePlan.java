package org.example.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.GameSettings;

import java.awt.*;

public class GamePlan {
    private int width = 45;
    private int height = 30;

    @JsonIgnore
    private final Color[] colors = new Color[2];

    public GamePlan(int width, int height){
        colors[0] = new Color(134, 182, 82);
        colors[1] = new Color(136, 189, 83);

        this.width = width;
        this.height = height;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public GamePlan(){

    }

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


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public Color[] getColors() {
        return colors;
    }
}
