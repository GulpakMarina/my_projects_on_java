

import classes.Human;
import classes.Lift;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;


public class TestTask {

    @Test(dataProvider = "taskProvider")

    public void inputTest(Lift[] lifts, Human human, int res) {
        assertEquals(Main.bestLift(lifts,human), res);
    }

    /*@DataProvider
    public Object[][] taskProvider() {
        return new Object[][]{{new Lift[]{new Lift(1, 12, 6),
                                          new Lift(2, 8, 7),
                                          new Lift(3, 6, 8)},
                                new Human(7,Human.Direction.DOWN), 1},
                              {new Lift[]{new Lift(1, 4, 1),
                                          new Lift(2, 3, 4)},
                                      new Human(2,Human.Direction.UP),2},
                {new Lift[]{new Lift(1, 1, 8),
                        new Lift(2, 2, 7),
                        new Lift(3, 15, 18),
                        new Lift(4, 6, 8),
                        new Lift(5, 20, 1),
                        new Lift(6, 19, 6)},
                        new Human(7,Human.Direction.DOWN),6}};
    }*/
    @DataProvider
    public Object[][] taskProvider() throws IOException {
        DataFromGJON[] dataFromGJONS = ParseGJON.parseJson(new File("src/test/java/pos.json"));
        Object[][] objects = new Object[dataFromGJONS.length][];
        for (int i = 0; i < dataFromGJONS.length; i++) {
            objects[i] = new Object[]{dataFromGJONS[i].getLifts(), dataFromGJONS[i].getHuman(), dataFromGJONS[i].getExtended()};
        }
        return objects;
    }

    @Test(dataProvider = "negativeTaskProvider")
    public void negativeWhileTest(Lift[] lifts, Human human, int res) {
        assertEquals(Main.bestLift(lifts,human), res);
    }

    @DataProvider
    public Object[][] negativeTaskProvider() {
        return new Object[][]{{new Lift[]{new Lift(1, 1, 1),
                new Lift(2, 8, 8)},
                new Human(7,Human.Direction.DOWN), 2}};

    }



}
