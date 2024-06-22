package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class SetGameWindow extends JFrame {

    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField snakeSizeField;
    private final JTextField obstaclesField;
    private final JTextField foodField;
    private final JTextField timeField;
    private final MainMenuFrame mainMenuFrame;

    public SetGameWindow(MainMenuFrame mainMenuFrame, int players) {
        setTitle("Snake Game Settings");
        this.mainMenuFrame = mainMenuFrame;
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Snake Game Settings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Initialize fields with default values
        widthField = createAndAddField("Width:", "45", "Set the width of the game area.", 1);
        heightField = createAndAddField("Height:", "30", "Set the height of the game area.", 2);
        snakeSizeField = createAndAddField("Snake Starting Size:", "6", "Set the starting size of the snake.", 3);
        obstaclesField = createAndAddField("Number of Obstacles:", "20", "Set the number of obstacles in the game.", 4);
        foodField = createAndAddField("Number of Food:", "5", "Set the number of food items in the game.", 5);
        timeField = createAndAddField("Time:", "60", "Set the game duration in seconds.", 6);

        // Submit button
        JButton submitButton = new JButton("Start Game");
        submitButton.addActionListener(e -> setGame(players));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        setVisible(true);
    }

    private JTextField createAndAddField(String labelText, String defaultValue, String toolTipText, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = gridy;

        JLabel label = new JLabel(labelText);
        add(label, gbc);

        gbc.gridx = 1;
        JTextField textField = new JTextField(defaultValue);
        textField.setToolTipText(toolTipText);
        add(textField, gbc);

        return textField;
    }

    private void setGame(int players) {
        int width = parseField(widthField, 45, 10, 70);
        int height = parseField(heightField, 30, 10, 50);
        int obstacles = parseField(obstaclesField, 10, 0, (width * height) / 10);
        int food = parseField(foodField, 5, 1, (width * height) / 50);
        int snakeSize = parseField(snakeSizeField, 6, 1, (width * height) / 50);
        int time = parseField(timeField, 60, 30, 600);

        new GameFrame(players, mainMenuFrame, width, height, obstacles, food, snakeSize, time, "local", null);
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
