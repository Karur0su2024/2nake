package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame {

    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField snakeSizeField;
    private final JTextField obstaclesField;
    private final JTextField foodField;
    private final JTextField timeField;
    private final MainMenuFrame mainMenuFrame;
    private final JComboBox<String> gameModeBox;

    public SettingsFrame(MainMenuFrame mainMenuFrame, int players) {
        setTitle("Snake Game Settings");
        this.mainMenuFrame = mainMenuFrame;
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        // Initialize fields with default values
        widthField = createAndAddField("Width:", "45");
        heightField = createAndAddField("Height:", "30");
        snakeSizeField = createAndAddField("Snake Starting Size:", "6");
        obstaclesField = createAndAddField("Number of Obstacles:", "20");
        foodField = createAndAddField("Number of Food:", "5");
        timeField = createAndAddField("Time:", "60");

        // Game mode selection
        add(new JLabel("Game Mode:"));
        gameModeBox = new JComboBox<>(new String[] { "Local", "Remote" });
        add(gameModeBox);

        // Submit button
        JButton submitButton = new JButton("Start Game");
        submitButton.addActionListener(e -> setGame(players));
        add(submitButton);

        setVisible(true);
    }

    private JTextField createAndAddField(String labelText, String defaultValue) {
        add(new JLabel(labelText));
        JTextField textField = new JTextField(defaultValue);
        add(textField);
        return textField;
    }

    private void setGame(int players) {
        int width = parseField(widthField, 45, 10, 70);
        int height = parseField(heightField, 30, 10, 50);
        int obstacles = parseField(obstaclesField, 10, 0, (width * height) / 10);
        int food = parseField(foodField, 5, 1, (width * height) / 50);
        int snakeSize = parseField(snakeSizeField, 6, 1, (width * height) / 50);
        int time = parseField(timeField, 60, 30, 600);

        String gameMode = (String) gameModeBox.getSelectedItem();
        new GameFrame(players, mainMenuFrame, width, height, obstacles, food, snakeSize, time, gameMode);
        mainMenuFrame.setVisible(false);
        dispose();
    }

    private int parseField(JTextField field, int defaultValue, int minValue, int maxValue) {
        int value;
        try {
            value = Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            value = defaultValue;
        }
        return Math.max(minValue, Math.min(value, maxValue));
    }
}
