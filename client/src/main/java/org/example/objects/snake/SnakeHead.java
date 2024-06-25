package org.example.objects.snake;

import java.util.List;

public class SnakeHead implements SnakePart {
    private int x;
    private int y;
    private Direction direction;
    private SnakeBodyPart nextPart;

    public SnakeHead(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
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
    }


}
