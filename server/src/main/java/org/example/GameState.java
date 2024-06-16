package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameState implements ActionListener {
    private Game game;

    public GameState(Game game) {
        this.game = game;
    }

    public void update() {
        // Update the positions of snakes, food, and check for collisions

    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

