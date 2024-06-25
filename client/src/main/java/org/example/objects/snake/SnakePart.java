package org.example.objects.snake;

import java.util.List;

public interface SnakePart {
    public void grow(int iteration, List<SnakePart> bodyParts);

    public int getX();
    public int getY();
    public Direction getDirection();

    void move();

    void setDirection(Direction direction);
}
