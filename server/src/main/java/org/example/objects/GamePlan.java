package org.example.objects;

import org.example.GameSettings;

import java.awt.*;

public class GamePlan {
    private final int width;
    private final int height;
    private final Color[] colors = new Color[2];

    public GamePlan(int width, int height){
        colors[0] = new Color(134, 182, 82);
        colors[1] = new Color(136, 189, 83);

        this.width = width;
        this.height = height;
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
}
