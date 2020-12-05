package crud;

import model.CompositionOfRecipe;
import utility.IncorrectValueException;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompositionOfRecipeRepository implements DefaultRepository {

    public static final String INGREDIENT_ID="ingredient_id";
    public static final String AMOUNT_OF_INGREDIENT="amount_of_ingredient";
    public static final String VALUE_ID ="value_id";
    public static final String RECIPE_ID="recipe_id";
    public static final String TABLE_NAME="composition_of_recipe";
    public static final String CRETE_TABLE="create table if not exists " + TABLE_NAME+
            "(id serial primary key, " +
            INGREDIENT_ID + " integer not null, " +
            AMOUNT_OF_INGREDIENT + " real, " +
            RECIPE_ID + " integer not null, "+
            VALUE_ID + " integer,"+
            "FOREIGN KEY ("+INGREDIENT_ID+") REFERENCES "+IngredientRepository.TABLE_NAME+"(id)" +
            "FOREIGN KEY ("+RECIPE_ID+") REFERENCES "+RecipeRepository.TABLE_NAME+"(id)" +
            "FOREIGN KEY ("+VALUE_ID+") REFERENCES "+ValueRepository.TABLE_NAME+"(id));";
    public static final String DELETE_TABLE = "drop table if exists "+TABLE_NAME+" RESTRICT";
    public static final String GET_ALL_COMPOSITION = "select * from "+TABLE_NAME;

    public  CompositionOfRecipe add(CompositionOfRecipe compositionOfRecipe)  {

        String ADD_NEW = "INSERT INTO "+TABLE_NAME+" ("+INGREDIENT_ID+ ", "+AMOUNT_OF_INGREDIENT+", "+VALUE_ID+", "+RECIPE_ID+") "+
                "values(?,?,?,?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, compositionOfRecipe.getIngredientId());
            preparedStatement.setDouble(2, compositionOfRecipe.getAmountOfIngredient());
            preparedStatement.setString(3, compositionOfRecipe.getRecipeId());
            preparedStatement.setString(4, compositionOfRecipe.getValueId());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                compositionOfRecipe.setId(""+ rs.getObject("id"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return compositionOfRecipe;
    }

    public static boolean update(String id,CompositionOfRecipe composition)  {
        String EDIT = "UPDATE "+TABLE_NAME+" SET "+INGREDIENT_ID+"=?, "+AMOUNT_OF_INGREDIENT+"=?, "+
                 RECIPE_ID+"=?, "+VALUE_ID+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT)) {
            preparedStatement.setString(1, composition.getIngredientId());
            preparedStatement.setDouble(2, composition.getAmountOfIngredient());
            preparedStatement.setString(3, composition.getRecipeId());
            preparedStatement.setString(4, composition.getValueId());
            preparedStatement.setObject(5, id);

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

    private static List<CompositionOfRecipe> getAll(String query) {
        List<CompositionOfRecipe> allComposition = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                CompositionOfRecipe composition = new CompositionOfRecipe.Builder()
                        .setId(""+rs.getObject("id"))
                        .setIngredientId(rs.getString(INGREDIENT_ID))
                        .setAmountOfIngredient(rs.getDouble(AMOUNT_OF_INGREDIENT))
                        .setRecipeId(rs.getString(RECIPE_ID))
                        .setValueId(rs.getString(VALUE_ID)).build();
                allComposition.add(composition);
            }
        }catch (SQLException | IncorrectValueException throwable) {
            throwable.printStackTrace();
        }
        return allComposition;
    }

    public static List<CompositionOfRecipe> getAll() {
        return getAll(GET_ALL_COMPOSITION);
    }

    public static List<CompositionOfRecipe> getAll(String column_id, String id) {
        return getAll(GET_ALL_COMPOSITION+" where "+column_id+" = "+id);
    }

    public static String getCompositionId(CompositionOfRecipe composition) {
        String GET_ID = "SELECT * FROM "+TABLE_NAME+" WHERE "+INGREDIENT_ID+" = '" + composition.getIngredientId() + "'" +
                " AND "+AMOUNT_OF_INGREDIENT+" = '" + composition.getAmountOfIngredient() + "'"+
                " AND "+RECIPE_ID+" = '" + composition.getRecipeId() + "'"+
                " AND "+VALUE_ID+" = '" + composition.getValueId()+"'";
        return DefaultRepository.getIdByQuery(GET_ID);
    }



}
