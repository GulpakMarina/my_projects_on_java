package classes;

import java.util.Objects;


public class Lift {
    private int id;
    private int fromFloor;
    private int toFloor;

    public Lift(int id, int fromFloor, int toFloor) {
        this.id = id;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;

    }
    public Lift(int fromFloor, int toFloor) {

        this.fromFloor = fromFloor;
        this.toFloor = toFloor;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFromFloor(int fromFloor) {
        this.fromFloor = fromFloor;
    }

    public void setToFloor(int toFloor) {
        this.toFloor = toFloor;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lift)) return false;
        Lift lift = (Lift) o;
        return id == lift.id &&
                fromFloor == lift.fromFloor &&
                toFloor == lift.toFloor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromFloor, toFloor);
    }

    @Override
    public String toString() {
        return "Lift{" +
                "ID=" + id +
                ", fromFloor=" + fromFloor +
                ", toFloor=" + toFloor +
                '}';
    }

}
