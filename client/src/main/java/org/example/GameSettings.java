package org.example;

import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.example.objects.Food;

public class GameSettings {
    public static final int UNIT_SIZE = 20;
    public static final int DELAY = 5;

    private final int noPlayers;
    private final int timeLimit;
    private final GamePlan gamePlan;
    private final Obstacle[] obstacles;
    private final Food[] foods;
    private final int initialSnakeSize;

    public GameSettings(int noPlayers, int timeLimit, GamePlan gamePlan, Obstacle[] obstacles, Food[] foods, int initialSnakeSize) {
        this.noPlayers = noPlayers;
        this.timeLimit = timeLimit;
        this.gamePlan = gamePlan;
        this.obstacles = obstacles;
        this.foods = foods;
        this.initialSnakeSize = initialSnakeSize;
    }

    public int getNoPlayers() {
        return noPlayers;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public GamePlan getGamePlan() {
        return gamePlan;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public Food[] getFoods() {
        return foods;
    }

    public int getInitialSnakeSize() {
        return initialSnakeSize;
    }
}
