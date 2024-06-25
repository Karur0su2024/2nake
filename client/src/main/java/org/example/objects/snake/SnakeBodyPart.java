package org.example.objects.snake;

import java.util.List;

public class SnakeBodyPart implements SnakePart {
    private int x; // Pole obsahující x-ové souřadnice jednotlivých částí hada
    private int y; // Pole obsahující y-ové souřadnice jednotlivých částí hada
    private Direction direction;
    private SnakeBodyPart nextPart;

    public SnakeBodyPart(SnakePart previousPart){
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
}
