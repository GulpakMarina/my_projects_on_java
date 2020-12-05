package crud;

import model.Cuisine;
import utility.IncorrectValueException;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuisineRepository implements DefaultRepository {

    public static final String TITLE="title";
    public static final String TABLE_NAME="cuisine";
    public static final String CRETE_TABLE="create table if not exists " + TABLE_NAME+
            " (id serial primary key, " +
            TITLE + " varchar(100) unique not null);";
    public static final String DELETE_TABLE = "drop table if exists "+TABLE_NAME+" RESTRICT";
    public static final String GET_ALL_CUISINE = "select * from "+TABLE_NAME;

    public static boolean add(Cuisine cuisine)  {

        String ADD_NEW = "INSERT INTO "+TABLE_NAME+" ("+TITLE+ ") "+
                "values(?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, cuisine.getTitle());
            return (preparedStatement.executeUpdate()!=0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static boolean update(int id,Cuisine cuisine)  {
        String EDIT = "UPDATE "+TABLE_NAME+" SET "+TITLE+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT)) {
            preparedStatement.setString(1, cuisine.getTitle());
            preparedStatement.setObject(2, id);

            return (preparedStatement.executeUpdate() != 0);
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return  false;
    }

    public static void delete(int id){
        String DELETE = "DELETE FROM "+TABLE_NAME+" WHERE id = "+id;
        DefaultRepository.deleteByQuery(DELETE);
    }

    public static List<Cuisine> getAll() {
        List<Cuisine> allCuisine = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_CUISINE)) {

            while (rs.next()) {
                Cuisine cuisine = new Cuisine.Builder()
                        .setTitle(rs.getString(TITLE)).build();
                cuisine.setId(rs.getInt("id"));
                allCuisine.add(cuisine);
            }
        }catch (SQLException | IncorrectValueException throwable) {
            throwable.printStackTrace();
        }
        return allCuisine;
    }

    public static String getCuisineId(Cuisine cuisine) {
        String GET_ID = "SELECT * FROM "+TABLE_NAME+" WHERE "+TITLE+" = '" + cuisine.getTitle();
        return DefaultRepository.getIdByQuery(GET_ID);
    }


}
