package testData;

import com.google.gson.Gson;
import tabels.Advertise;
import tabels.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParseGJON {

    public static TestDataUser parseJsonUser(File fileName) {
        TestDataUser testDataUserSimple=null;
        try(FileReader reader=new FileReader(fileName)){
            testDataUserSimple = new Gson().fromJson(reader, TestDataUser.class);
        }catch (IOException e){
            e.printStackTrace();
        }
            return testDataUserSimple;
    }

    public static TestDataAdvertise parseJsonAdvertise(File fileName) {
        TestDataAdvertise testDataAdvertise=null;
        try(FileReader reader=new FileReader(fileName)){
            testDataAdvertise = new Gson().fromJson(reader, TestDataAdvertise.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return testDataAdvertise;
    }

   public static void main(String[] args) {
      toGsonUser();
      // toGsonAdvertise();
   }

    public static void toGsonAdvertise()  {
        TestDataAdvertise testDataAdvertise=new TestDataAdvertise();

        testDataAdvertise.setUserForTests(new User("login1","email1","phone1"));

        List<Advertise> advertiseBeforeSuite=new ArrayList<>();
        advertiseBeforeSuite.add(new Advertise("title1","description1", LocalDate.now(), 1));
        testDataAdvertise.setAdvertisesBeforeSuite(advertiseBeforeSuite);

        List<Advertise> advertiseAdd=new ArrayList<>();
        advertiseAdd.add(new Advertise("title2","description2", LocalDate.now(), 1));
        testDataAdvertise.setAdvertisesAdd(advertiseAdd);

        testDataAdvertise.setGetAdvertiseId(new Advertise("title3","description3", LocalDate.now(), 1));

        List<Advertise> advertiseUpdate=new ArrayList<>();
        advertiseUpdate.add(new Advertise("title4","description4", LocalDate.now(), 1));
        testDataAdvertise.setAdvertisesUpdate(advertiseUpdate);

        List<Advertise> advertiseDelete=new ArrayList<>();
        advertiseDelete.add(new Advertise("title5","description5", LocalDate.now(), 1));
        testDataAdvertise.setAdvertisesDelete(advertiseDelete);

        Gson gson=new Gson();
        try(FileWriter writer=new FileWriter("dataTestAdvertise.json")) {
            gson.toJson(testDataAdvertise, writer);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void toGsonUser()  {
        TestDataUser testDataUser=new TestDataUser();
        List<User> usersBeforeSuite=new ArrayList<>();
        usersBeforeSuite.add(new User("login1","email1","phone1"));
        usersBeforeSuite.add(new User("login2","email2"));
        testDataUser.setUserBeforeSuite(usersBeforeSuite);

        List<User> negativeAddUser=new ArrayList<>();
        negativeAddUser.add(new User("login1","email3","phone1"));
        negativeAddUser.add(new User("login3","email1","phone1"));
        testDataUser.setNegativeAddUser(negativeAddUser);

        List<User> addPositiveTests=new ArrayList<>();
        addPositiveTests.add(new User(3,"login3","email3","phone3"));
        testDataUser.setAddPositiveTests(addPositiveTests);

        List<User> updatePositiveTests=new ArrayList<>();
        updatePositiveTests.add(new User("login4","email4","phone4"));
        testDataUser.setUpdatePositiveTests(updatePositiveTests);

        List<User> negativeAddUserNull=new ArrayList<>();
        negativeAddUserNull.add(new User(null,"email*","phone1"));
        negativeAddUserNull.add(new User("login*",null,"phone1"));
        testDataUser.setNegativeAddUserNull(negativeAddUserNull);

        Gson gson=new Gson();
        try(FileWriter writer=new FileWriter("dataTestUser.json")) {
            gson.toJson(testDataUser, writer);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
