package org.example.gui;

import org.example.GuiHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Hlavní okno aplikace obsahující hlavní menu pro výběr herních režimů a možností.
 */
public class MainMenu extends JFrame {

    /**
     * Konstruktor pro inicializaci hlavního menu.
     */
    public MainMenu() {
        GuiHandler gui = new GuiHandler();
        gui.setMainMenu(this);

        // Nastavení titulku a výchozí operace při zavření
        setTitle("2nake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Vytvoření panelu pro hlavní menu
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Vytvoření tlačítek
        JButton startLocalGameButton = createButton("Lokální hra");
        startLocalGameButton.addActionListener(e -> new SettingsFrame(gui));


        JButton startServerButton = createButton("Servery");
        startServerButton.addActionListener(e -> new JoinServerFrame(MainMenu.this));

        JButton instructionsButton = createButton("Nápověda");
        instructionsButton.addActionListener(e -> showInstructions());

        JButton exitButton = createButton("Konec");
        exitButton.addActionListener(e -> System.exit(0));

        // Přidání tlačítek do panelu
        panel.add(startLocalGameButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(startServerButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(instructionsButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(exitButton);

        // Přidání panelu do rámečku
        add(panel);
    }

    /**
     * Vytvoří tlačítko s daným textem a akcí.
     *
     * @param text text tlačítka
     * @return vytvořené tlačítko
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        return button;
    }

    /**
     * Zobrazuje instrukce k ovládání hry.
     */
    private void showInstructions() {
        String instructions = "Pro pohyb hada použijte klávesy šipek nebo ASDW.\n" +
                "Modré jídlo prodlužuje hada, červené ho zkracuje.\n" +
                "Pokud nabouráte do překážky nebo těla hada, prohráváte.";
        JOptionPane.showMessageDialog(this, instructions, "Nápověda", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Hlavní metoda pro spuštění hlavního menu.
     *
     * @param args argumenty příkazového řádku
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
