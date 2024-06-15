package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame implements ActionListener {

    private JTextField widthField;
    private JTextField heightField;
    private JTextField snakeSizeField;
    private JTextField obstaclesField;
    private JTextField foodField;
    private JTextField timeField;
    private final MainMenuFrame mainMenuFrame;

    public SettingsFrame(MainMenuFrame mainMenuFrame, int players) {
        setTitle("Snake Game Settings");
        this.mainMenuFrame = mainMenuFrame;
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        JPanel panel = new JPanel();


        // Width setting
        add(new JLabel("Width:"));
        widthField = new JTextField("45");
        add(widthField);

        // Height setting
        add(new JLabel("Height:"));
        heightField = new JTextField("30");
        add(heightField);

        // Snake starting size setting
        add(new JLabel("Snake Starting Size:"));
        snakeSizeField = new JTextField("6");
        add(snakeSizeField);

        // Number of obstacles setting
        add(new JLabel("Number of Obstacles:"));
        obstaclesField = new JTextField("20");
        add(obstaclesField);

        // Number of food setting
        add(new JLabel("Number of Food:"));
        foodField = new JTextField("5");
        add(foodField);

        add(new JLabel("Time:"));
        timeField = new JTextField("60");
        add(timeField);

        // Submit button
        JButton submitButton = new JButton("Start Game");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(players,
                        mainMenuFrame, Integer.parseInt(widthField.getText()),
                        Integer.parseInt(heightField.getText()), Integer.parseInt(obstaclesField.getText()),
                                Integer.parseInt(foodField.getText()), Integer.parseInt(snakeSizeField.getText()), Integer.parseInt(timeField.getText()));
                mainMenuFrame.setVisible(false);
                dispose();

            }
        });
        add(submitButton);

        add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
