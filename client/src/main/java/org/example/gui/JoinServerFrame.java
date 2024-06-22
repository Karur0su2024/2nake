package org.example.gui;

import org.example.GameClient;

import javax.swing.*;
import java.awt.*;

public class JoinServerFrame extends JFrame {

    private final MainMenuFrame mainMenuFrame;

    GameClient gameClient;

    public JoinServerFrame(MainMenuFrame mainMenuFrame) {
        setTitle("Snake Game Settings");
        this.mainMenuFrame = mainMenuFrame;
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));


        // Submit button
        JButton submitButton = new JButton("Start Game");
        submitButton.addActionListener(e -> setGame());
        add(submitButton);

        setVisible(true);
    }



    private void setGame() {
        new GameClient(mainMenuFrame);
        mainMenuFrame.setVisible(false);
        dispose();

    }
}
