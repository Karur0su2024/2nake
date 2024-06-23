package org.example;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;
import org.example.gui.Sidebar;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Třída {@code Game} reprezentuje stav hry ve hře Snake.
 * Obsahuje informace o hráčích, velikosti hry, překážkách, jídle a dalších prvcích hry.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private static final Logger log = LoggerFactory.getLogger(Game.class);
    private Snake[] snakes = null;
    private Food[] foods = null;
    private Obstacle[] obstacles = null;
    private GamePlan gamePlan = null;

    private int size = 6;

    private int time;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Game(){

    }

    @JsonCreator
    public Game(@JsonProperty("obstacles") Obstacle[] obstacles,
                @JsonProperty("size") int size,
                @JsonProperty("time") int time,
                @JsonProperty("snakes") Snake[] snakes,
                @JsonProperty("foods") Food[] foods,
                @JsonProperty("gamePlan") GamePlan gamePlan) {
        this.obstacles = obstacles;
        this.size = size;
        this.time = time;
        this.snakes = snakes;
        this.foods = foods;
        this.gamePlan = gamePlan;
    }

    public Game(GameSettings gameSettings) {
        this.time = gameSettings.getTimeLimit();
        this.gamePlan = gameSettings.getGamePlan();
        this.obstacles = gameSettings.getObstacles();
        this.foods = gameSettings.getFoods();
        this.snakes = new Snake[gameSettings.getNoPlayers()];
        this.size = gameSettings.getInitialSnakeSize();
    }


    public void paint(Graphics g, JPanel panel) {
        gamePlan.paint(g);
        for (Food food : foods) {
            if (food != null) {
                food.paint(g, panel);
            }
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.paint(g);
        }
        for (Snake snake : snakes) {
            snake.paint(g, panel);
        }
    }


    public void decreaseTime() {
        time--;
    }


    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(""+ e);
            return null;
        }
    }

    public static Game fromString(String jsonString) {
        try {
            return mapper.readValue(jsonString, Game.class);
        } catch (JsonProcessingException e) {
            log.error(""+ e);
            return null;
        }
    }


    public Snake[] getSnakes() {
        return snakes;
    }

    public Food[] getFoods() {
        return foods;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public GamePlan getGamePlan() {
        return gamePlan;
    }

    public int getSize() {
        return size;
    }


    public int getTime() {
        return time;
    }

    public void setSnakes(Snake[] snakes) {
        this.snakes = snakes;
    }
}
