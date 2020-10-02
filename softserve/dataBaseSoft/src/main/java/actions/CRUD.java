package actions;

import utility.PostgresConnection;

import java.sql.*;

public interface CRUD{
    static void createAndDelete(String query) {
        try (Connection connection = PostgresConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    static boolean isPresent(String table,String column, String someValue) {
        String isUserPresent = "SELECT "+column+" FROM "+table+" WHERE "+column+" = '" + someValue + "'";
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
    }

    static int getCount(String TABLE){
        String COUNT_USERS = "select count(id) as counted from "+TABLE;
        try (Connection connection = PostgresConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(COUNT_USERS)) {
            if(rs.next()) {
                return rs.getInt("counted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    static boolean deleteAll(String TABLE)  {
        String DELETE_USERS = "DELETE FROM "+TABLE;

        try (Connection connection = PostgresConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS)) {
            return (preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
