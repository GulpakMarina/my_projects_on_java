package utility;

import actions.CRUD;

import java.sql.*;

import static actions.AdvertiseRepository.CREATE_ADVERTISE_TABLE;
import static actions.UserRepository.*;


public class PostgresConnection {
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = ///password
    private static final String URL = "jdbc:postgresql://localhost:5432/Advertisements";

    public static void main(String[] args)  {

        CRUD.createAndDelete(CREATE_USERS_TABLE);
        CRUD.createAndDelete(CREATE_ADVERTISE_TABLE);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }


}



