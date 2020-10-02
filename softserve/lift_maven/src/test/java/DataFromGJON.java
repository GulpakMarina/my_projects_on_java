import classes.Human;
import classes.Lift;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DataFromGJON {
    private Lift[] lifts;
    private Human human;
    private int extended;

    public Lift[] getLifts() {
        return lifts;
    }

    public void setLifts(Lift[] lifts) {
        this.lifts = lifts;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public int getExtended() {
        return extended;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataFromGJON)) return false;
        DataFromGJON that = (DataFromGJON) o;
        return extended == that.extended &&
                Objects.equals(lifts, that.lifts) &&
                Objects.equals(human, that.human);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifts, human, extended);
    }
}
