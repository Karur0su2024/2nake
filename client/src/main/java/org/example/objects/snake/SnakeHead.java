package org.example.objects.snake;

import org.example.GameSettings;

import java.awt.*;
import java.util.List;

public class SnakeHead implements SnakePart {
    private int x;
    private int y;
    private Direction direction;
    private SnakeBodyPart nextPart;
    private Direction beforeTurnDirection;
    private int cD;

    public SnakeHead(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;

        beforeTurnDirection = direction;
        cD = 0;
    }

    public void move() {
        if(nextPart != null) {
            nextPart.move(this);
        }
        moveInDirection();
    }

    public void grow(int iteration, List<SnakePart> bodyParts) {
        if(iteration > 0){
            if(nextPart != null){
                nextPart.grow(iteration, bodyParts);
            }
            else {
                nextPart = new SnakeBodyPart(this);
                bodyParts.add(nextPart);
                nextPart.grow(--iteration, bodyParts);
            }
        }
    }


    public void moveInDirection(){
        switch (direction){
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
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

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
        cD = 1;
    }

    public void paint(Graphics g){
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
