package org.example.objects.obstacles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.GameSettings;

import java.awt.*;

public class Water extends Obstacle{

    @JsonCreator
    public Water(
            @JsonProperty("x") int x,
            @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(22, 120, 186));
        g.fillRect(this.x * GameSettings.UNIT_SIZE, this.y * GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
    }
}
