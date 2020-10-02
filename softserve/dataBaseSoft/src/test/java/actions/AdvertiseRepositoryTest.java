package actions;


import actions.exceptionsforthisproject.AlreadyExists;
import actions.exceptionsforthisproject.PresentNullException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tabels.Advertise;
import testData.ParseGJON;
import testData.TestDataAdvertise;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import static actions.AdvertiseRepository.*;
import static actions.UserRepository.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
//import static actions.UserRepository.deleteAllUser;

public class AdvertiseRepositoryTest {
    TestDataAdvertise testDataAdvertise;
    int user_id;

    @BeforeSuite
    public void beforeSuite() throws AlreadyExists, PresentNullException {
        CRUD.deleteAll(ADVERTISE_TABLE);
        CRUD.deleteAll(USER_TABLE);
        testDataAdvertise= ParseGJON.parseJsonAdvertise(new File("dataTestAdvertise.json"));
        user_id=addUser(testDataAdvertise.getUserForTests()).getId();
        List<Advertise> userBeforeSuite= testDataAdvertise.getAdvertisesBeforeSuite();
        for(Advertise advertise: userBeforeSuite){
            advertise.setUserId(user_id);
            addAdvertise(advertise);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 1)
    public void testGetCountAdvertise() {
        assertEquals(CRUD.getCount(ADVERTISE_TABLE),1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 2)
    public void testGetAllAdvertisesForUser() {
        assertEquals(getAllAdvertisesForUser(user_id).size(),testDataAdvertise.getAdvertisesBeforeSuite().size());
    }

    ////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 3)
    public void testGetAdvertiseId() {
        Advertise advertise= getAllAdvertises().get(0);
        assertEquals(getAdvertiseId(advertise),advertise.getId());
    }

    /////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testAddAdvertiseProvider(){
        List<Advertise> addPositiveTests=testDataAdvertise.getAdvertisesAdd();
        Object[][] objects = new Object[addPositiveTests.size()][];
        for (int i = 0; i < addPositiveTests.size(); i++) {
            Advertise advertise=addPositiveTests.get(i);
            advertise.setUserId(user_id);
            objects[i] = new Object[]{advertise,advertise.getId()};
        }
        return objects;
    }

    @Test(dataProvider = "testAddAdvertiseProvider",priority = 4)
    public void testAddAdvertise(Advertise advertise, int res) {
        int i=CRUD.getCount(ADVERTISE_TABLE);
        advertise.setUserId(user_id);
        addAdvertise(advertise);
        assertEquals(CRUD.getCount(ADVERTISE_TABLE),i+1);
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testUpdateAdvertiseProvider(){
        List<Advertise> updatePositiveTests=testDataAdvertise.getAdvertisesUpdate();
        Object[][] objects = new Object[updatePositiveTests.size()][];
        for (int i = 0; i < updatePositiveTests.size(); i++) {
            Advertise advertise=updatePositiveTests.get(i);
            objects[i] = new Object[]{advertise};
        }
        return objects;
    }

    @Test(dataProvider = "testUpdateAdvertiseProvider",priority = 4)
    public void testUpdateAdvertise(Advertise advertise) {
        Advertise advertiseToUpdate= getAllAdvertises().get(0);
        updateAdvertise(advertiseToUpdate.getId(), advertise);
        advertise.setUserId(user_id);
        assertTrue(getAdvertiseId(advertise)!=-1);
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 5)
    public void testDeleteAdvertise() {
        Advertise advertiseToDelete= getAllAdvertises().get(0);
        int i=CRUD.getCount(ADVERTISE_TABLE);
        deleteAdvertise(advertiseToDelete.getId());
        assertEquals(CRUD.getCount(ADVERTISE_TABLE),i-1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetAllAdvertises() {
        int size=CRUD.getCount(ADVERTISE_TABLE);
        assertEquals(getAllAdvertises().size(),size);
    }


    @Test(priority = 5)
    public void testDeleteAllAdvertisesForUser() {
        deleteAllAdvertisesForUser(user_id);
        assertEquals(getAllAdvertisesForUser(user_id).size(),0);

    }

    @AfterSuite
    public void afterSuite(){
        CRUD.deleteAll(ADVERTISE_TABLE);
        CRUD.deleteAll(USER_TABLE);
    }



}