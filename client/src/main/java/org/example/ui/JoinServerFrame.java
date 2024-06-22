package org.example.ui;

import org.example.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Okno pro připojení k serveru hry pomocí klienta.
 */
public class JoinServerFrame extends JFrame {

    private final MainMenuFrame mainMenuFrame;

    private GameClient gameClient;
    private JLabel message;
    private JButton submitButton;

    /**
     * Konstruktor pro inicializaci okna připojení k serveru.
     *
     * @param mainMenuFrame hlavní menu aplikace
     */
    public JoinServerFrame(MainMenuFrame mainMenuFrame) {
        setTitle("Snake Game Settings");
        this.mainMenuFrame = mainMenuFrame;
        setSize(400, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));
        message = new JLabel("");

        // Tlačítko pro odeslání požadavku na připojení
        submitButton = new JButton("Start Game");
        submitButton.addActionListener(e -> setGame());
        add(submitButton);
        add(message);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    /**
     * Spustí klienta hry po stisknutí tlačítka "Start Game".
     */
    private void setGame() {
        new GameClient(mainMenuFrame, this);
    }

    /**
     * Nastaví zprávu, která se zobrazí uživateli.
     *
     * @param text text zprávy
     */
    public void setMessage(String text) {
        message.setText(text);
    }

    /**
     * Skryje tlačítko pro připojení, pokud již není potřeba.
     */
    public void hideJoinButton() {
        this.submitButton.setVisible(false);
    }
}
