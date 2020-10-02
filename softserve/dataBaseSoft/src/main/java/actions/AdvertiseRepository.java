package actions;

import tabels.Advertise;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdvertiseRepository  implements CRUD {
    public static final String ADVERTISE_TABLE="advertises";
    public static final String ADVERTISE_TITLE="title";
    public static final String ADVERTISE_CREATION="date_creation";
    public static final String ADVERTISE_UPDATE="date_update";
    public static final String ADVERTISE_DESCRIPTION="description";
    public static final String CREATE_ADVERTISE_TABLE =
            "create table if not exists advertises  " +
                    "(id serial primary key, " +
                    ADVERTISE_TITLE +" varchar(30) not null, " +
                    ADVERTISE_DESCRIPTION +" varchar(500) not null, " +
                    ADVERTISE_CREATION + " timestamp not null, " +
                    ADVERTISE_UPDATE + " timestamp not null, " +
                    "user_id int not null, "+
                    "CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id))";
    public static final String DELETE_ADVERTISE_TABLE = "drop table advertises";
    public static final String GET_ALL_ADVERTISES = "select * from advertises";

    private AdvertiseRepository() {
    }

    /*public static boolean isAdvertisePresent(Advertise advertise)  {
        String sql = " select* from "+ADVERTISE_TABLE+" where id = '";
        try(Connection connection = PostgresConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql)) {
           if (rs.next()) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }*/

    public static Advertise addAdvertise(Advertise advertise)  {

        String ADD_NEW_ADVERTISE = "INSERT INTO "+ADVERTISE_TABLE+" ("+ADVERTISE_TITLE+ ", "+ADVERTISE_DESCRIPTION+", "+ADVERTISE_CREATION+", "+ADVERTISE_UPDATE+", "+"user_id) "+
                 "values(?,?,?,?,?)";
         try (Connection connection = PostgresConnection.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_ADVERTISE, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, advertise.getTitle());
                preparedStatement.setString(2, advertise.getDescription());
                preparedStatement.setObject(3, advertise.getDateCreation());
                preparedStatement.setObject(4, advertise.getDateCreation());
                preparedStatement.setObject(5, advertise.getUserId());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    advertise.setId(rs.getInt("id"));
                }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return advertise;
    }

    public static boolean updateAdvertise(int id,Advertise advertise)  {
        String EDIT_ADVERTISE = "UPDATE "+ADVERTISE_TABLE+" SET "+ADVERTISE_TITLE+"=?, "+ADVERTISE_DESCRIPTION+"=?, "+
                ADVERTISE_UPDATE+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_ADVERTISE)) {
            preparedStatement.setString(1, advertise.getTitle());
            preparedStatement.setString(2, advertise.getDescription());
            preparedStatement.setObject(3, advertise.getDateCreation());
            preparedStatement.setObject(4, id);

            return (preparedStatement.executeUpdate() != 0);
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return  false;
    }

    public static void deleteAdvertise(int id){
        String DELETE_USER = "DELETE FROM "+ADVERTISE_TABLE+" WHERE id = ?";

        try(Connection connection = PostgresConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    public static List<Advertise> getAllAdvertises() {
        List<Advertise> allAdvertises = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_ADVERTISES)) {

            while (rs.next()) {
                Advertise advertise = new Advertise();
                advertise.setId(rs.getInt("id"));
                advertise.setTitle(rs.getString(ADVERTISE_TITLE));
                advertise.setDescription(rs.getString(ADVERTISE_DESCRIPTION));
                advertise.setDateCreation(rs.getDate(ADVERTISE_CREATION).toLocalDate());
                advertise.setDateUpdate(rs.getDate(ADVERTISE_UPDATE).toLocalDate());
                advertise.setUserId(rs.getInt("user_id"));

                allAdvertises.add(advertise);
            }
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allAdvertises;
    }

    public static List<Advertise> getAllAdvertisesForUser(int id) {
        List<Advertise> allAdvertises = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_ADVERTISES+" where user_id = "+id)) {
            while (rs.next()) {
                Advertise advertise = new Advertise();
                advertise.setId(rs.getInt("id"));
                advertise.setTitle(rs.getString(ADVERTISE_TITLE));
                advertise.setDescription(rs.getString(ADVERTISE_DESCRIPTION));
                advertise.setDateCreation(rs.getDate(ADVERTISE_CREATION).toLocalDate());
                advertise.setDateUpdate(rs.getDate(ADVERTISE_UPDATE).toLocalDate());
                advertise.setUserId(rs.getInt("user_id"));
                allAdvertises.add(advertise);
            }
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allAdvertises;
    }

    public static boolean deleteAllAdvertisesForUser(int id) {
        String DELETE_ADVERTISE = "DELETE FROM "+AdvertiseRepository.ADVERTISE_TABLE+" WHERE user_id = ?";

        try(Connection connection = PostgresConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADVERTISE)) {
            preparedStatement.setInt(1, id);
            return (preparedStatement.executeUpdate()!=0);
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }


    public static int getAdvertiseId(Advertise advertise) {
        String GET_ADVERTISE_ID = "SELECT * FROM "+ADVERTISE_TABLE+" WHERE "+ADVERTISE_TITLE+" = '" + advertise.getTitle() + "'" +
                " AND "+ADVERTISE_DESCRIPTION+" = '" + advertise.getDescription() + "'"+
                " AND "+ADVERTISE_CREATION+" = '" + advertise.getDateCreation() + "'"+
                " AND user_id = " + advertise.getUserId();
        int advertiseId = -1;
        try (Connection connection = PostgresConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ADVERTISE_ID)) {
            while (rs.next()) {
                advertiseId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advertiseId;
    }


}
