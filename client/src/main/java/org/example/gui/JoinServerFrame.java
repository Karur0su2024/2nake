package org.example.gui;

import org.example.GameClient;
import org.example.GuiHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Okno pro připojení k serveru hry pomocí klienta.
 */
public class JoinServerFrame extends JFrame {

    private GameClient gameClient;
    private JLabel messageLabel;
    private JButton submitButton;
    private JTextField nameTextField;
    private GuiHandler gui;


    public JoinServerFrame(GuiHandler gui) {
        this.gui = gui;
        this.gui.setJoinServerFrame(this);

        setTitle("Join server");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        initUIComponents();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                gui.getMainMenu().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    /**
     * Initialize and arrange the UI components.
     */
    private void initUIComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Name Label
        JLabel nameLabel = new JLabel("Enter your name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        // Name Text Field
        nameTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameTextField, gbc);

        // Submit Button
        submitButton = new JButton("Start Game");
        submitButton.addActionListener(e -> setGame());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(submitButton, gbc);

        // Message Label
        messageLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(messageLabel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Spustí klienta hry po stisknutí tlačítka "Start Game".
     */
    private void setGame() {
        String playerName = nameTextField.getText().trim().replaceAll("\\s+", "_");
        if (playerName.isEmpty()) {
            setMessage("Name cannot be empty.");
            return;
        }
        new GameClient(gui, playerName);
    }

    /**
     * Nastaví zprávu, která se zobrazí uživateli.
     *
     * @param text text zprávy
     */
    public void setMessage(String text) {
        messageLabel.setText(text);
    }

    /**
     * Skryje tlačítko pro připojení, pokud již není potřeba.
     */
    public void hideJoinButton() {
        this.submitButton.setVisible(false);
    }
}
