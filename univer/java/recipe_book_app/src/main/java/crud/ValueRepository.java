package crud;

import model.Recipe;
import model.Value;
import utility.IncorrectValueException;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ValueRepository implements DefaultRepository {

    public static final String NAME="name";
    public static final String OWNER_ID="owner_id";
    public static final String TABLE_NAME="value";
    public static final String CRETE_TABLE="create table if not exists " + TABLE_NAME+
            "(id serial primary key, " +
            NAME+" varchar(100) not null, " +
            OWNER_ID + " integer)";
    public static final String DELETE_TABLE = "drop table if exists "+TABLE_NAME+" RESTRICT";
    public static final String GET_ALL_VALUE = "select * from "+TABLE_NAME;

    public Value add(Value value)  {
        String ADD_NEW = "INSERT INTO "+TABLE_NAME+" ("+NAME+ ", "+OWNER_ID+") "+
                "values(?,?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, value.getName());
            preparedStatement.setString(2, value.getOwnerId());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                value.setId(""+ rs.getObject("id"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return value;
    }

    public static boolean update(String id,Value value)  {
        String EDIT = "UPDATE "+TABLE_NAME+" SET "+NAME+"=?, "+OWNER_ID+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT)) {
            preparedStatement.setString(1, value.getName());
            preparedStatement.setString(2, value.getOwnerId());
            preparedStatement.setObject(3, id);

            return (preparedStatement.executeUpdate() != 0);
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return  false;
    }

    public static void delete(String id){
        String DELETE = "DELETE FROM "+TABLE_NAME+" WHERE id = "+id;
        DefaultRepository.deleteByQuery(DELETE);
    }

    private static List<Value> getAll(String query) {
        List<Value> allValue = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Value value = new Value.Builder()
                        .setId(""+rs.getObject("id"))
                        .setName(rs.getString(NAME))
                        .setOwnerId(rs.getString(OWNER_ID)).build();
                allValue.add(value);
            }
        }catch (SQLException | IncorrectValueException throwable) {
            throwable.printStackTrace();
        }
        return allValue;
    }

    public static List<Value> getAll() {
        return getAll(GET_ALL_VALUE);
    }

    public static List<Value> getAllForOwner(String id) {
        return getAll(GET_ALL_VALUE +" where "+OWNER_ID+" = "+id);
    }

    public static String getValueId(Value value) {
        String GET_ID = "SELECT * FROM "+TABLE_NAME+" WHERE "+NAME+" = '" + value.getName() + "'" +
                " AND "+OWNER_ID+" = '" + value.getOwnerId() + "'";
        return DefaultRepository.getIdByQuery(GET_ID);
    }
}
