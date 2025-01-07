package org.example.objects.snake;

import org.example.GameSettings;

import java.awt.*;
import java.util.List;

public class SnakeBodyPart implements SnakePart {
    private int x; // Pole obsahující x-ové souřadnice jednotlivých částí hada
    private int y; // Pole obsahující y-ové souřadnice jednotlivých částí hada
    private Direction direction;

    private SnakeBodyPart nextPart;
    private SnakePart previousPart;

    public SnakeBodyPart(SnakePart previousPart){
        this.previousPart = previousPart;
        x = previousPart.getX();
        y = previousPart.getX();
        direction = previousPart.getDirection();
    }

    public void move(SnakePart previousPart){
        if(nextPart != null){
            nextPart.move(this);
            x = previousPart.getX();
            y = previousPart.getY();
            direction = previousPart.getDirection();
        }
        else {
            x = previousPart.getX();
            y = previousPart.getY();
            direction = previousPart.getDirection();
        }
    }


    public void move(){}

    public void grow(int iteration, List<SnakePart> bodyParts) {
        if(iteration > 0){
            if(nextPart != null){
                nextPart.grow(iteration, bodyParts);
            }
            else {
                nextPart = new SnakeBodyPart(this);
                bodyParts.add(nextPart);
                bodyParts.getLast().grow(--iteration, bodyParts);
            }
        }
    }

    public void paint(Graphics g){

        g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 10, GameSettings.UNIT_SIZE - 10);
        paintConnections(g, previousPart);
        if(nextPart != null){
            paintConnections(g, nextPart);
        }


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void paintConnections(Graphics g, SnakePart part){
        Direction pPart = previousPart.getDirection();

        if(pPart != direction){
            pPart = direction;
        }

        if(pPart == Direction.UP){
            g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE), GameSettings.UNIT_SIZE - 10, GameSettings.UNIT_SIZE - 5);
        }
        if(pPart == Direction.DOWN){
            g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 10, GameSettings.UNIT_SIZE - 5);

        }
        if(pPart == Direction.LEFT){
            g.fillRect((x * GameSettings.UNIT_SIZE), (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 5, GameSettings.UNIT_SIZE - 10);
        }
        if(pPart == Direction.RIGHT){

            g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 5, GameSettings.UNIT_SIZE - 10);
        }



        if(nextPart != null){
            //NextPart
            if(nextPart.getDirection() == Direction.UP){
                g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 10, GameSettings.UNIT_SIZE - 5);
            }
            if(nextPart.getDirection() == Direction.DOWN){
                g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE), GameSettings.UNIT_SIZE - 10, GameSettings.UNIT_SIZE - 5);

            }
            if(nextPart.getDirection() == Direction.LEFT){
                g.fillRect((x * GameSettings.UNIT_SIZE) + 5, (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 5, GameSettings.UNIT_SIZE - 10);
            }
            if(nextPart.getDirection() == Direction.RIGHT){
                g.fillRect((x * GameSettings.UNIT_SIZE), (y * GameSettings.UNIT_SIZE) + 5, GameSettings.UNIT_SIZE - 5, GameSettings.UNIT_SIZE - 10);
            }
        }

    }
}
