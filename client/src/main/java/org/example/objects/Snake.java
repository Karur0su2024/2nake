package org.example.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.GameSettings;
import org.example.objects.snake.Direction;
import org.example.objects.snake.SnakeHead;
import org.example.objects.snake.SnakePart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Třída reprezentující hada v herním prostředí.
 */
public class Snake {
    private char direction = 'R'; // Směr hada ('U' pro nahoru, 'D' pro dolů, 'L' pro doleva, 'R' pro doprava)
    //private int bodyParts = 6; // Počet částí hada
    private int[] x; // Pole obsahující x-ové souřadnice jednotlivých částí hada
    private int[] y; // Pole obsahující y-ové souřadnice jednotlivých částí hada
    private String name;
    private List<SnakePart> bodyParts;
    private SnakeHead head;
    private Color[] colors;

    @JsonIgnore
    private int maxSize; // Maximální velikost hada

    private int speed; // Rychlost hada



    @JsonCreator
    public Snake(
            @JsonProperty("direction") char direction,
            @JsonProperty("bodyParts") List<SnakePart> bodyParts,
            @JsonProperty("name") String name,
            @JsonProperty("speed") int speed) {
        this.direction = direction;
        this.bodyParts = bodyParts;
        this.name = name;
        this.speed = speed;
    }




    /**
     * Konstruktor pro vytvoření hada s určitou maximální velikostí a počátečním počtem částí.
     *
     * @param bodySize počáteční počet částí hada
     */
    public Snake(int bodySize, int x, int y, Direction direction, String name) {
        bodyParts = new ArrayList<>();
        bodyParts.add(new SnakeHead(x, y, direction));
        bodyParts.getFirst().grow(bodySize, bodyParts);

        setSpeed();
        this.name = name;


        colors = new Color[]{Color.yellow, new Color(221, 221, 119)};
    }



    /**
     * Prázdný konstruktor pro serializaci pomocí Jackson ObjectMapper.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Snake() {
        x = new int[maxSize];
        y = new int[maxSize];
    }

    /**
     * Metoda pro získání počtu částí hada.
     *
     * @return počet částí hada
     */
    public List<SnakePart> getBodyParts() {
        return bodyParts;
    }

    /**
     * Metoda pro zvýšení počtu částí hada o určitý počet bodů.
     * Pokud je počet částí hada 1 a počet bodů je menší než 0, had neztratí tělo.
     *
     */
    public void eat(Food food) {
        this.getBodyParts().getFirst().grow(1, bodyParts);
        setSpeed();
        if (speed < 1) {
            speed = 1;
        }
        colors[1] = food.getColor();
    }

    /**
     * Metoda pro nastavení směru hada.
     *
     * @param direction nový směr hada ('U' pro nahoru, 'D' pro dolů, 'L' pro doleva, 'R' pro doprava)
     */
    public void setDirection(Direction direction) {
        bodyParts.getFirst().setDirection(direction);
    }

    /**
     * Metoda pro získání směru hada.
     *
     * @return směr hada
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Metoda pro pohyb hada podle jeho směru.
     * Každá část hada se posune na místo předchozí části.
     */
    public void move() {
        bodyParts.getFirst().move();
    }

    /**
     * Metoda pro vykreslení hada na zadaném grafickém kontextu.
     *
     * @param g     grafický kontext, na kterém se má had vykreslit
     */
    public void paint(Graphics g) {
        g.setColor(Color.cyan);
        for (SnakePart part : bodyParts) {
            g.fillRect(part.getX() * GameSettings.UNIT_SIZE, part.getY() * GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE, GameSettings.UNIT_SIZE);
        }
    }

    /**
     * Metoda pro získání rychlosti hada.
     *
     * @return rychlost hada
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Metoda pro nastavení rychlosti hada na základě počtu jeho částí.
     * Čím více částí hada, tím nižší je jeho rychlost.
     */
    public void setSpeed() {
        this.speed = (22 - (bodyParts.size() / 7));
    }

    /**
     * Metoda pro nastavení počtu částí hada.
     *
     * @param bodyParts nový počet částí hada
     */
    public void setBodyParts(int bodyParts) {
        head.grow(bodyParts, this.bodyParts);
    }


    /**
     * Metoda pro nastavení rychlosti hada.
     *
     * @param speed nová rychlost hada
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Metoda pro nastavení hlavy hada na zadané x-ové a y-ové souřadnice.
     *
     * @param x x-ová souřadnice hlavy hada
     * @param y y-ová souřadnice hlavy hada
     */
    public void setHead(int x, int y) {
        this.x[0] = x;
        this.y[0] = y;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "name='" + name + '\'' +
                '}';
    }

}
