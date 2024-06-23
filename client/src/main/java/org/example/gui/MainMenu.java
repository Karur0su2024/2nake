package org.example.gui;

import org.example.GuiHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        panel.setLayout(new GridLayout(5, 1, 20, 10));

        // Vytvoření tlačítek
        JButton start1player = new JButton("Hra pro jednoho hráče");
        JButton start2players = new JButton("Hra pro dva hráče");
        JButton startServer = new JButton("Servery");
        JButton instructionsButton = new JButton("Nápověda");
        JButton exitButton = new JButton("Konec");

        // Přidání posluchačů událostí pro tlačítka
        start1player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spuštění hry pro jednoho hráče
                new SettingsFrame(gui, 1);
            }
        });

        start2players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spuštění hry pro dva hráče
                new SettingsFrame(gui, 2);
            }
        });

        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spuštění okna pro připojení k serveru
                new JoinServerFrame(MainMenu.this);
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zobrazení nápovědy
                JOptionPane.showMessageDialog(null, "Pro pohyb hada použijte klávesy šipek nebo ASDW.\nModré jídlo prodlužuje hada červené ho zkracuje.\nPokud nabouráte do překážky nebo těla hada prohráváte.", "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ukončení aplikace
                System.exit(0);
            }
        });

        // Přidání tlačítek do panelu
        panel.add(start1player);
        panel.add(start2players);
        panel.add(startServer);
        panel.add(instructionsButton);
        panel.add(exitButton);

        // Přidání panelu do rámečku
        add(panel);
    }
}
