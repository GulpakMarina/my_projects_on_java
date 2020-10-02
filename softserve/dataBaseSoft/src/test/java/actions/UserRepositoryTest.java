package actions;

import actions.exceptionsforthisproject.AlreadyExists;
import actions.exceptionsforthisproject.PresentNullException;
import org.testng.annotations.*;

import static actions.UserRepository.*;
import static org.testng.Assert.*;
import tabels.User;
import testData.ParseGJON;
import testData.TestDataUser;

import java.io.File;
import java.sql.SQLException;
import java.util.*;


public class UserRepositoryTest {
    TestDataUser testDataUserSimple;

    @BeforeSuite
    public void beforeSuite() throws AlreadyExists, PresentNullException {
        CRUD.deleteAll(USER_TABLE);
        testDataUserSimple= ParseGJON.parseJsonUser(new File("dataTestUser.json"));
        List<User> userBeforeSuite= testDataUserSimple.getUserBeforeSuite();
        for(User user: userBeforeSuite){
           addUser(user);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetAllUsers() {
        assertEquals(getAllUsers().size(),CRUD.getCount(USER_TABLE));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 1)
    public void testGetCountUsers() {
        assertEquals(CRUD.getCount(USER_TABLE),2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testAddUserProvider(){
        List<User> addPositiveTests=testDataUserSimple.getAddPositiveTests();
        Object[][] objects = new Object[addPositiveTests.size()][];
        for (int i = 0; i < addPositiveTests.size(); i++) {
            User user=addPositiveTests.get(i);
            objects[i] = new Object[]{user};
        }
        return objects;
    }

    @Test(dataProvider = "testAddUserProvider",priority = 3)
    public void testAddUser(User user) throws PresentNullException, AlreadyExists {
        int i=CRUD.getCount(USER_TABLE);
        addUser(user);
        assertEquals(CRUD.getCount(USER_TABLE),i+1);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testNegativeAddUserProvider(){
        List<User> negativeAddUser=testDataUserSimple.getNegativeAddUser();
        Object[][] objects = new Object[negativeAddUser.size()][];
        for (int i = 0; i < negativeAddUser.size(); i++) {
            User user=negativeAddUser.get(i);
            objects[i] = new Object[]{user};
        }
        return objects;
    }

    @Test(dataProvider = "testNegativeAddUserProvider",expectedExceptions = {AlreadyExists.class},priority = 3)
    public void negativeAddUser(User user) throws PresentNullException, AlreadyExists {
        int i=CRUD.getCount(USER_TABLE);
        addUser(user);
        assertEquals(CRUD.getCount(USER_TABLE),i+1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 4)
    public void testGetUserId() throws  SQLException {
        User user=getAllUsers().get(0);
        assertEquals(getUserId(user),user.getId());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testUpdateUserProvider(){
        List<User> updatePositiveTests=testDataUserSimple.getUpdatePositiveTests();
        Object[][] objects = new Object[updatePositiveTests.size()][];
        for (int i = 0; i < updatePositiveTests.size(); i++) {
            User user=updatePositiveTests.get(i);
            objects[i] = new Object[]{user};
        }
        return objects;
    }

    @Test(dataProvider = "testUpdateUserProvider",priority = 4)
    public void testUpdateUser(User user) throws PresentNullException, AlreadyExists, SQLException {
        User userToUpdate=getAllUsers().get(0);
        updateUser(userToUpdate.getId(), user);
        assertTrue(getUserId(user)!=-1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Test(priority = 4)
    public void testDeleteUser()  {
        User userToDelete=getAllUsers().get(0);
        int i=CRUD.getCount(USER_TABLE);
        deleteUser(userToDelete.getId());
        assertEquals(CRUD.getCount(USER_TABLE),i-1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testNegativeAddUserNullProvider(){
        List<User> negativeAddUserNull=testDataUserSimple.getNegativeAddUserNull();
        Object[][] objects = new Object[negativeAddUserNull.size()][];
        for (int i = 0; i < negativeAddUserNull.size(); i++) {
            User user=negativeAddUserNull.get(i);
            objects[i] = new Object[]{user};
        }
        return objects;
    }

    @Test(dataProvider = "testNegativeAddUserNullProvider",expectedExceptions = {PresentNullException.class},priority = 4)
    public void negativeAddUserNull(User user) throws PresentNullException, AlreadyExists {
        int i=CRUD.getCount(USER_TABLE);
        addUser(user);
        assertEquals(CRUD.getCount(USER_TABLE),i+1);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testNegativeUpdateUserProvider(){
        Object[][] objects = new Object[2][];
        List<User> users=getAllUsers();
        User user=users.get(0);
        User userToDelete=users.get(1);
        userToDelete.setEmail(user.getEmail());
        objects[0] = new Object[]{user.getId(),userToDelete};
        userToDelete=users.get(1);
        userToDelete.setLogin(user.getLogin());
        objects[1] = new Object[]{user.getId(),userToDelete};
        return objects;
    }

    @Test(dataProvider = "testNegativeUpdateUserProvider",expectedExceptions = {AlreadyExists.class})
    public void negativeUpdateUser(int id,User user) throws PresentNullException, AlreadyExists, SQLException {
        int i=CRUD.getCount(USER_TABLE);
        updateUser(id,user);
        assertTrue(getUserId(user)!=-1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] testNegativeUpdateUserNullProvider(){
        Object[][] objects = new Object[2][];
        List<User> users=getAllUsers();
        User user=users.get(0);
        User userToDelete=users.get(1);
        userToDelete.setEmail(null);
        objects[0] = new Object[]{user.getId(),userToDelete};
        userToDelete=users.get(1);
        userToDelete.setLogin(null);
        objects[1] = new Object[]{user.getId(),userToDelete};
        return objects;
    }

    @Test(dataProvider = "testNegativeUpdateUserNullProvider",expectedExceptions = {PresentNullException.class})
    public void negativeUpdateUserNull(int id,User user) throws PresentNullException, AlreadyExists, SQLException {
        int i=CRUD.getCount(USER_TABLE);
        updateUser(id,user);
        assertTrue(getUserId(user)!=-1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @AfterSuite
    public void afterSuite(){
        CRUD.deleteAll(USER_TABLE);
    }

}