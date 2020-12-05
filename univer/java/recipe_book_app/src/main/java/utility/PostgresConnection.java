package utility;
import crud.CuisineRepository;
import crud.DefaultRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class PostgresConnection {

    private static String LOGIN;
    private static String PASSWORD ;
    private static String URL;

    static {
        try(InputStream fileProp = new FileInputStream("src/main/resources/prop.properties")){
            Properties propConnection=new Properties();
            propConnection.load(fileProp);
            LOGIN=propConnection.getProperty("login");
            PASSWORD=propConnection.getProperty("password");
            URL=propConnection.getProperty("URL");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        getConnection();
        DefaultRepository.createAndDelete(CuisineRepository.CRETE_TABLE);
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/recipe_book", "postgres", "lenovo13");
    }


}