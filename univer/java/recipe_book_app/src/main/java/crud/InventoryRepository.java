package crud;

import model.Inventory;
import utility.IncorrectValueException;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository implements DefaultRepository {

    public static final String TITLE="title";
    public static final String RECIPE_ID="recipe_id";
    public static final String TABLE_NAME="inventory";
    public static final String CRETE_TABLE="create table if not exists " + TABLE_NAME+
            "(id serial primary key, " +
            TITLE+" varchar(100) not null, " +
            RECIPE_ID + " integer ," +
            "FOREIGN KEY ("+RECIPE_ID+") REFERENCES "+RecipeRepository.TABLE_NAME+"(id))";
    public static final String DELETE_TABLE = "drop table if exists "+TABLE_NAME+" RESTRICT";
    public static final String GET_ALL_INVENTORY = "select * from "+TABLE_NAME;

    public Inventory add(Inventory inventory)  {

        String ADD_NEW = "INSERT INTO "+TABLE_NAME+" ("+TITLE+ ", "+RECIPE_ID+") "+
                "values(?,?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, inventory.getTitle());
            preparedStatement.setString(2, inventory.getRecipeId());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                inventory.setId(""+ rs.getObject("id"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return inventory;
    }

    public static boolean update(String id,Inventory inventory)  {
        String EDIT = "UPDATE "+TABLE_NAME+" SET "+TITLE+"=?, "+RECIPE_ID+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT)) {
            preparedStatement.setString(1, inventory.getTitle());
            preparedStatement.setString(2, inventory.getRecipeId());
            preparedStatement.setString(3, id);

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

    private static List<Inventory> getAll(String query) {
        List<Inventory> allInventory = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Inventory inventory = new Inventory.Builder()
                        .setId(""+rs.getObject("id"))
                        .setTitle(rs.getString(TITLE))
                        .setRecipeId(rs.getString(RECIPE_ID)).build();
                allInventory.add(inventory);
            }
        }catch (SQLException | IncorrectValueException throwable) {
            throwable.printStackTrace();
        }
        return allInventory;
    }

    public static List<Inventory> getAll() {
        return getAll(GET_ALL_INVENTORY);
    }

    public static List<Inventory> getAllForRecipe(String recipeId) {
        return getAll(GET_ALL_INVENTORY +" where "+RECIPE_ID+" = "+recipeId);
    }

    public static String getInventoryId(Inventory inventory) {
        String GET_ID = "SELECT * FROM "+TABLE_NAME+" WHERE "+TITLE+ " = '"+inventory.getTitle()+"'"+
                " AND "+RECIPE_ID+" = '"+inventory.getRecipeId()+"'";
        return DefaultRepository.getIdByQuery(GET_ID);
    }

}
