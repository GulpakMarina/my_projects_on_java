package crud;

import utility.PostgresConnection;

import java.sql.*;

public interface DefaultRepository {

    static void createAndDelete(String query){
        try (Connection connection = PostgresConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    static String getIdByQuery(String query){
        String id = null;
        try (Connection connection = PostgresConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                id = rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    static void deleteByQuery(String query){
        try(Connection connection = PostgresConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    static boolean isPresent(String table,String column, String someValue) {
        String query = "SELECT "+column+" FROM "+table+" WHERE "+column+" = '" + someValue + "'";
        try (Connection connection = PostgresConnection.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                if(rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
