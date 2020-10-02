package classes;

import java.util.Objects;

public class Human {
    private String name;
    private int currentFloor;
    private Direction direction;
    public enum Direction{
        UP,
        DOWN
    }
    public Human(String name, int currantFloor, Direction direction) {
        this.name = name;
        this.currentFloor = currantFloor;
        this.direction = direction;
    }

    public Human(int currantFloor, Direction direction) {
        this.currentFloor = currantFloor;
        this.direction = direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", currantFloor=" + currentFloor +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return currentFloor == human.currentFloor &&
                direction == human.direction &&
                Objects.equals(name, human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currentFloor, direction);
    }

}
