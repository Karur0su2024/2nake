package org.example;

import org.example.objects.Snake;
import org.example.objects.Food;
import org.example.objects.Obstacle;

import java.util.*;

public class GameState {
    private List<Snake> snakes;
    private Food food;
    private List<Obstacle> obstacles;
    private Game game;

    public GameState() {
        // Initialize the game state
    }

    public void update() {
        // Update the positions of snakes, food, and check for collisions
        for (Snake snake : snakes) {
            snake.move();
            // Check for collisions with food and obstacles
        }
    }

    @Override
    public String toString() {
        // Convert game state to a string representation
        return "GameState{" +
                "snakes=" + snakes +
                ", food=" + food +
                ", obstacles=" + obstacles +
                '}';
    }
}

