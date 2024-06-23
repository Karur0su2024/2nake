package org.example;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Snake;


import java.util.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Třída {@code Game} reprezentuje stav hry ve hře Snake.
 * Obsahuje informace o hráčích, velikosti hry, překážkách, jídle a dalších prvcích hry.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private static final Logger log = LoggerFactory.getLogger(Game.class);
    private final List<Snake> snakes;
    private final Food[] foods;
    private final Obstacle[] obstacles;
    private final GamePlan gamePlan;

    private int time;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @JsonCreator
    public Game(@JsonProperty("obstacles") Obstacle[] obstacles,
                @JsonProperty("time") int time,
                @JsonProperty("snakes") List<Snake> snakes,
                @JsonProperty("foods") Food[] foods,
                @JsonProperty("gamePlan") GamePlan gamePlan) {
        this.obstacles = obstacles;
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
        this.snakes = new ArrayList<>();
    }

    public Game(Game game){
        this.time = game.time;
        this.gamePlan = game.getGamePlan();
        this.obstacles = game.getObstacles();
        this.foods = game.getFoods();
        this.snakes = game.snakes;
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

    public List<Snake> getSnakes() {
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

    public int getTime() {
        return time;
    }
}
