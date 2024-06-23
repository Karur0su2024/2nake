package org.example.gui;

import org.example.GameSettings;
import org.example.GuiHandler;
import org.example.Main;
import org.example.objects.Food;
import org.example.objects.GamePlan;
import org.example.objects.Obstacle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Okno pro nastavení parametrů hry hada a jejich zahájení.
 */
public class SettingsFrame extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField snakeSizeField;
    private final JTextField obstaclesField;
    private final JTextField foodField;
    private final JTextField timeField;

    GuiHandler gui;

    /**
     * Konstruktor pro vytvoření okna s nastavením parametrů hry.
     *
     * @param players       Počet hráčů hry
     */
    public SettingsFrame(GuiHandler gui, int players) {
        this.gui = gui;

        setTitle("Snake Game Settings");
        log.info("Otevírám nastavení hry");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        // Inicializace textových polí s výchozími hodnotami
        widthField = createAndAddField("Šířka:", "45");
        heightField = createAndAddField("Výška:", "30");
        snakeSizeField = createAndAddField("Počáteční velikost hada:", "6");
        obstaclesField = createAndAddField("Počet překážek:", "20");
        foodField = createAndAddField("Počet jídel:", "5");
        timeField = createAndAddField("Čas:", "60");

        // Tlačítko pro spuštění hry
        JButton submitButton = new JButton("Spustit hru");
        submitButton.addActionListener(e -> setGame(players));
        add(submitButton);

        setVisible(true);
    }

    /**
     * Metoda pro vytvoření a přidání textového pole s popiskem do okna nastavení.
     *
     * @param labelText    Text popisku
     * @param defaultValue Výchozí hodnota textového pole
     * @return Reference na vytvořené textové pole
     */
    private JTextField createAndAddField(String labelText, String defaultValue) {
        add(new JLabel(labelText));
        JTextField textField = new JTextField(defaultValue);
        add(textField);
        return textField;
    }

    /**
     * Metoda pro zahájení hry s vybranými parametry.
     *
     * @param players Počet hráčů hry
     */
    private void setGame(int players) {
        int width = parseField(widthField, 45, 10, 70);
        int height = parseField(heightField, 30, 10, 50);
        int obstacles = parseField(obstaclesField, 10, 0, (width * height) / 10);
        int food = parseField(foodField, 5, 1, (width * height) / 50);
        int snakeSize = parseField(snakeSizeField, 6, 1, (width * height) / 50);
        int time = parseField(timeField, 60, 30, 600);

        new GameFrame(new GameSettings(players, time, new GamePlan(width, height), new Obstacle[obstacles], new Food[food], snakeSize), gui, "local", null);

        gui.toggleMainMenu();
        dispose();
    }

    /**
     * Metoda pro zpracování a validaci hodnot z textového pole.
     *
     * @param field      Textové pole pro zpracování
     * @param defaultValue Výchozí hodnota, pokud se nepodaří načíst platné číslo
     * @param minValue   Minimální platná hodnota
     * @param maxValue   Maximální platná hodnota
     * @return Načtená a validovaná hodnota
     */
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
