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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
    private final JTextField playersField;

    private final GuiHandler gui;

    /**
     * Konstruktor pro vytvoření okna s nastavením parametrů hry.
     */
    public SettingsFrame(GuiHandler gui) {
        this.gui = gui;
        this.gui.setSettingsFrame(this);
        setTitle("Snake Game Settings");
        log.info("Otevírám nastavení hry");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Vytvoření hlavního panelu s BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        // Inicializace textových polí s výchozími hodnotami
        widthField = createAndAddField(panel, "Šířka:", "45");
        heightField = createAndAddField(panel, "Výška:", "30");
        snakeSizeField = createAndAddField(panel, "Počáteční velikost hada:", "6");
        obstaclesField = createAndAddField(panel, "Počet překážek:", "20");
        foodField = createAndAddField(panel, "Počet jídel:", "5");
        timeField = createAndAddField(panel, "Čas:", "60");
        playersField = createAndAddField(panel, "Počet hráčů:", "1");

        // Tlačítko pro spuštění hry
        JButton submitButton = new JButton("Spustit hru");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> setGame());
        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                gui.setSettingsFrame(null);
                gui.toggleMainMenu();
                dispose();
            }
        });

    }


    /**
     * Metoda pro vytvoření a přidání textového pole s popiskem do okna nastavení.
     *
     * @param panel        Panel, do kterého bude přidáno textové pole
     * @param labelText    Text popisku
     * @param defaultValue Výchozí hodnota textového pole
     * @return Reference na vytvořené textové pole
     */
    private JTextField createAndAddField(JPanel panel, String labelText, String defaultValue) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField textField = new JTextField(defaultValue);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
        return textField;
    }

    /**
     * Metoda pro zahájení hry s vybranými parametry.
     */
    private void setGame() {
        int width = parseField(widthField, 45, 10, 70);
        int height = parseField(heightField, 30, 10, 50);
        int obstacles = parseField(obstaclesField, 10, 0, (width * height) / 10);
        int food = parseField(foodField, 5, 1, (width * height) / 50);
        int snakeSize = parseField(snakeSizeField, 6, 1, (width * height) / 50);
        int time = parseField(timeField, 60, 30, 600);
        int players = parseField(playersField, 1, 1, 2);

        GameSettings gs = new GameSettings(players, time, new GamePlan(width, height), new Obstacle[obstacles], new Food[food], snakeSize);

        new GameWindow(gs, gui);

        setVisible(false);

    }

    /**
     * Metoda pro zpracování a validaci hodnot z textového pole.
     *
     * @param field        Textové pole pro zpracování
     * @param defaultValue Výchozí hodnota, pokud se nepodaří načíst platné číslo
     * @param minValue     Minimální platná hodnota
     * @param maxValue     Maximální platná hodnota
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
