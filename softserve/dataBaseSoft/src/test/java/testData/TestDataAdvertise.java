package testData;

import tabels.Advertise;
import tabels.User;

import java.util.List;

public class TestDataAdvertise {
    User userForTests;
    List<Advertise> advertisesBeforeSuite;
    List<Advertise> advertisesAdd;
    Advertise getAdvertiseId;
    List<Advertise> advertisesUpdate;
    List<Advertise> advertisesDelete;


    public List<Advertise> getAdvertisesDelete() {
        return advertisesDelete;
    }

    public void setAdvertisesDelete(List<Advertise> advertisesDelete) {
        this.advertisesDelete = advertisesDelete;
    }

    public List<Advertise> getAdvertisesUpdate() {
        return advertisesUpdate;
    }

    public void setAdvertisesUpdate(List<Advertise> advertisesUpdate) {
        this.advertisesUpdate = advertisesUpdate;
    }

    public User getUserForTests() {
        return userForTests;
    }

    public void setUserForTests(User userForTests) {
        this.userForTests = userForTests;
    }

    public Advertise getGetAdvertiseId() {
        return getAdvertiseId;
    }

    public void setGetAdvertiseId(Advertise getAdvertiseId) {
        this.getAdvertiseId = getAdvertiseId;
    }

    public List<Advertise> getAdvertisesAdd() {
        return advertisesAdd;
    }

    public void setAdvertisesAdd(List<Advertise> advertisesAdd) {
        this.advertisesAdd = advertisesAdd;
    }

    public List<Advertise> getAdvertisesBeforeSuite() {
        return advertisesBeforeSuite;
    }

    public void setAdvertisesBeforeSuite(List<Advertise> advertisesBeforeSuite) {
        this.advertisesBeforeSuite = advertisesBeforeSuite;
    }
}
