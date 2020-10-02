package actions;

import actions.exceptionsforthisproject.AlreadyExists;
import actions.exceptionsforthisproject.PresentNullException;
import tabels.User;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static actions.AdvertiseRepository.deleteAllAdvertisesForUser;

public class UserRepository {
    static final String USER_TABLE="users";
    static final String USER_LOGIN="login";
    static final String USER_EMAIL="email";
    static final String USER_PHONE="phone";
    public static final String CREATE_USERS_TABLE = "create table if not exists users (id serial primary key, login varchar(50) not null, email varchar(50) not null, phone varchar(20))";
    public static final String DELETE_USERS_TABLE = "drop table users";
    static final String GET_ALL_PERSONS = "SELECT *  FROM users";

    private UserRepository() {
    }

    public static User addUser(User user) throws AlreadyExists, PresentNullException {
        if (CRUD.isPresent(USER_TABLE,USER_LOGIN,user.getLogin()) ) {
            throw new AlreadyExists("This login is already in the table!");
        }
        if(CRUD.isPresent(USER_TABLE,USER_EMAIL,user.getEmail())){
            throw new AlreadyExists("This email is already in the table!");
        }
        if(user.getEmail() == null){
            throw new PresentNullException("Email must be fill in!");
        }
        if(user.getLogin() == null){
            throw new PresentNullException("Login must be fill in!");
        }
        String ADD_NEW_PERSON = "INSERT INTO " +USER_TABLE+" ("+USER_LOGIN+", "+ USER_EMAIL+", "+USER_PHONE+")"
                    + "values(?,?,?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_PERSON, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setObject(3, user.getPhone());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public static boolean updateUser(int id,User user) throws AlreadyExists, PresentNullException {
        if (CRUD.isPresent(USER_TABLE,USER_LOGIN,user.getLogin()) ) {
            throw new AlreadyExists("This login is already in the table!");
        }
        if(CRUD.isPresent(USER_TABLE,USER_EMAIL,user.getEmail())){
            throw new AlreadyExists("This email is already in the table!");
        }
        if(!CRUD.isPresent(USER_TABLE,"id",""+id)){
            throw new AlreadyExists("This id isn't in the table!");
        }
        if(user.getEmail() == null){
            throw new PresentNullException("Email must be fill in!");
        }
        if(user.getLogin() == null){
            throw new PresentNullException("Login must be fill in!");
        }
        String EDIT_PERSON = "UPDATE "+USER_TABLE+" SET "+USER_LOGIN+" =?, "+USER_EMAIL+" =?, "+USER_PHONE+" =? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_PERSON)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setObject(3, user.getPhone());
            preparedStatement.setInt(4, id);
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(int id) {
        deleteAllAdvertisesForUser(id);
        String DELETE_USER = "DELETE FROM "+USER_TABLE+" where id = "+ id;

        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



  /*  public static boolean isUserPresent(User user) {

        String isUserPresent = "SELECT * FROM "+USER_TABLE+" WHERE login = '" + user.getLogin() + "'" +
                "AND email = '" + user.getEmail() + "'";
        try (Connection connection = PostgresConnection.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(isUserPresent)) {
                if(rs.next()) {
                    return true;
                }
            }
        } catch (SQLException E) {
            E.printStackTrace();
        }
        return false;
    }*/


    public static List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();

        try (Connection connection = PostgresConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_PERSONS)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString(USER_LOGIN));
                user.setEmail(rs.getString(USER_EMAIL));
                user.setPhone(rs.getString(USER_PHONE));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }






    public static int getUserId(User user) throws SQLException {
        String GET_PERSON_ID = "SELECT * FROM "+USER_TABLE+" WHERE "+USER_LOGIN+" = '" + user.getLogin() + "'" + "AND "+USER_EMAIL+" = '" + user.getEmail() + "'";
        int userId = -1;
        Connection connection = PostgresConnection.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_PERSON_ID)) {
            while (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }


 /*   public static User getUserById(int id) {
        String SELECT_USER = "SELECT * FROM "+USER_TABLE+" WHERE id = ?";
        User user = null;
        try (Connection connection = PostgresConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    user = new User(rs.getString(USER_LOGIN), rs.getString(USER_EMAIL), rs.getString(USER_PHONE));
                }
            }
        } catch (SQLException E) {
            E.printStackTrace();
        }
        return user;
    }*/

}
