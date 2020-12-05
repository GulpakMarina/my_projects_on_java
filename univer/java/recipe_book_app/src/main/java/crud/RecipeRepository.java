package crud;

import model.Recipe;
import utility.IncorrectValueException;
import utility.PostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository implements DefaultRepository{

    public static final String TITLE="title";
    public static final String WEIGHT="weight";
    public static final String CUISINE_ID="cuisine_Id";
    public static final String DESCRIBE="describe";
    public static final String TABLE_NAME="recipe";
    public static final String CRETE_TABLE="create table if not exists " + TABLE_NAME+
            "(id serial primary key, " +
            TITLE + " varchar(100) UNIQUE not null, " +
            WEIGHT + " real not null, " +
            CUISINE_ID + " integer, " +
            DESCRIBE + " text , " +
            "FOREIGN KEY ("+CUISINE_ID+") REFERENCES "+CuisineRepository.TABLE_NAME+"(id));";
    public static final String DELETE_TABLE = "drop table if exists "+TABLE_NAME+" RESTRICT";
    public static final String GET_ALL_RECIPE = "select * from "+TABLE_NAME;

    public Recipe add(Recipe recipe)  {
        String ADD_NEW = "INSERT INTO "+TABLE_NAME+" ("+TITLE+ ", "+WEIGHT+", "+CUISINE_ID+", "+DESCRIBE+") "+
                "values(?,?,?,?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, recipe.getTitle());
            preparedStatement.setDouble(2, recipe.getWeight());
            preparedStatement.setString(3, recipe.getCuisineId());
            preparedStatement.setString(4, recipe.getDescribe());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                recipe.setId(""+ rs.getObject("id"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return recipe;
    }

    public static boolean update(String id,Recipe recipe)  {
        String EDIT = "UPDATE "+TABLE_NAME+" SET "+TITLE+"=?, "+WEIGHT+"=?, "+
                CUISINE_ID+"=? "+DESCRIBE+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT)) {
            preparedStatement.setString(1, recipe.getTitle());
            preparedStatement.setDouble(2, recipe.getWeight());
            preparedStatement.setString(3, recipe.getCuisineId());
            preparedStatement.setString(4, recipe.getDescribe());
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

    private static List<Recipe> getAll(String query) {
        List<Recipe> allRecipe = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Recipe recipe = new Recipe.Builder()
                        .setId(""+rs.getObject("id"))
                        .setTitle(rs.getString(TITLE))
                        .setWeight(rs.getDouble(WEIGHT))
                        .setSomeCuisine(rs.getString(CUISINE_ID))
                        .setDescribe(rs.getString(DESCRIBE)).build();
                allRecipe.add(recipe);
            }
        }catch (SQLException | IncorrectValueException throwable) {
            throwable.printStackTrace();
        }
        return allRecipe;
    }

    public static List<Recipe> getAll() {
        return getAll(GET_ALL_RECIPE);
    }

    public static List<Recipe> getAllForCuisine(String id) {
        return getAll(GET_ALL_RECIPE +" where "+CUISINE_ID+" = "+id);
    }

    public static String getRecipeId(Recipe recipe) {
        String GET_ID = "SELECT * FROM "+TABLE_NAME+" WHERE "+TITLE+" = '" + recipe.getTitle() + "'" +
                " AND "+WEIGHT+" = '" + recipe.getWeight() + "'"+
                " AND "+CUISINE_ID+" = '" + recipe.getCuisineId() + "'"+
                " AND "+DESCRIBE+" = '" + recipe.getDescribe()+"'";
        return DefaultRepository.getIdByQuery(GET_ID);
    }

    public static List<Recipe> getSortedRecipeByWeight(){
        String query="SELECT * FROM "+TABLE_NAME+"ORDER BY "+WEIGHT;
        return getAll(query);
    }


}
