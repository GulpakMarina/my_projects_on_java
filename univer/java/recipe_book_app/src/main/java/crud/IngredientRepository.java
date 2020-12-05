package crud;

import model.Cuisine;
import model.Ingredient;
import utility.IncorrectValueException;
import utility.PostgresConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepository implements DefaultRepository{

    public static final String TITLE="title";
    public static final String WHERE_TO_BUY="where_to_buy";
    public static final String PRICE="price";
    public static final String CALORIES="calories_per_100";
    public static final String PROTEINS="proteins_per_100";
    public static final String FATS="fats_per_100";
    public static final String CARBOHYDRATES="carbohydrates_per_100";
    public static final String EXPIRY="date_expiry";

    public static final String TABLE_NAME="ingredient";
    public static final String CRETE_TABLE="create table if not exists " + TABLE_NAME+
            "(id serial primary key, " +
            TITLE + " varchar(100) not null, " +
            EXPIRY + " timestamp not null, " +
            WHERE_TO_BUY + " text, " +
            PRICE + " real, " +
            CALORIES + " integer, " +
            PROTEINS + " integer, " +
            FATS + " integer, " +
            CARBOHYDRATES + " integer);";
    public static final String DELETE_TABLE = "drop table if exists "+TABLE_NAME+" RESTRICT";
    public static final String GET_ALL_INGREDIENT = "select * from "+TABLE_NAME;

    public Ingredient add(Ingredient ingredient)  {

        String ADD_NEW = "INSERT INTO "+TABLE_NAME+" ("+TITLE+ ", "+WHERE_TO_BUY+", "+PRICE+", "+CALORIES+", "+
                PROTEINS+", "+FATS+", "+CARBOHYDRATES+", "+EXPIRY+") "+
                "values(?,?,?,?,?,?,?,?)";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ingredient.getTitle());
            preparedStatement.setString(2, ingredient.getWhereToBuy());
            preparedStatement.setDouble(3, ingredient.getPrice());
            preparedStatement.setInt(4, ingredient.getCaloriesPer100());
            preparedStatement.setInt(5, ingredient.getProteinsPer100());
            preparedStatement.setInt(6, ingredient.getFatsPer100());
            preparedStatement.setInt(7, ingredient.getCarbohydratesPer100());
            preparedStatement.setDate(8, Date.valueOf(ingredient.getDateExpiry()));

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                ingredient.setId(""+ rs.getObject("id"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return ingredient;
    }

    public static boolean update(String id,Ingredient ingredient)  {
        String EDIT = "UPDATE "+TABLE_NAME+" SET "+TITLE+"=?, "+WHERE_TO_BUY+"=?, "+PRICE+"=?, "+CALORIES+"=?, "+
                PROTEINS+"=?, "+FATS+"=?, "+CARBOHYDRATES+"=?, "+EXPIRY+"=? "+
                " WHERE id = ? ;";
        try (Connection connection = PostgresConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT)) {
            preparedStatement.setString(1, ingredient.getTitle());
            preparedStatement.setString(2, ingredient.getWhereToBuy());
            preparedStatement.setDouble(3, ingredient.getPrice());
            preparedStatement.setInt(4, ingredient.getCaloriesPer100());
            preparedStatement.setInt(5, ingredient.getProteinsPer100());
            preparedStatement.setInt(6, ingredient.getFatsPer100());
            preparedStatement.setInt(7, ingredient.getCarbohydratesPer100());
            preparedStatement.setDate(8, Date.valueOf(ingredient.getDateExpiry()));
            preparedStatement.setString(9, id);

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

    private static List<Ingredient> getAll() {
        List<Ingredient> allIngredient = new ArrayList<>();

        try(Connection connection = PostgresConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_INGREDIENT)) {

            while (rs.next()) {
                Ingredient ingredient = new Ingredient.Builder()
                        .setId(""+rs.getObject("id"))
                        .setTitle(rs.getString(TITLE))
                        .setWhereToBuy(rs.getString(WHERE_TO_BUY))
                        .setPrise(rs.getDouble(PRICE))
                        .setCaloriesPer100(rs.getInt(CALORIES))
                        .setProteinsPer100(rs.getInt(PROTEINS))
                        .setFatsPer100(rs.getInt(FATS))
                        .setCarbohydratesPer100(rs.getInt(CARBOHYDRATES))
                        .setDateExpiry((LocalDate) rs.getObject(EXPIRY)).build();
                allIngredient.add(ingredient);
            }
        }catch (SQLException | IncorrectValueException throwable) {
            throwable.printStackTrace();
        }
        return allIngredient;
    }

    public static String getCuisineId(Ingredient ingredient) {
        String GET_ID = "SELECT * FROM "+TABLE_NAME+" WHERE "+TITLE+ " = '"+ingredient.getTitle()+"'"+
                " AND "+WHERE_TO_BUY+" = '"+ingredient.getWhereToBuy()+"'"+
                " AND "+PRICE+" = '"+ingredient.getPrice() + "'"+
                " AND "+CALORIES+" = '"+ingredient.getCaloriesPer100() + "'"+
                " AND "+PROTEINS+" = '"+ingredient.getProteinsPer100() + "'"+
                " AND "+FATS+" = '"+ingredient.getFatsPer100() + "'"+
                " AND "+CARBOHYDRATES+" = '"+ingredient.getCarbohydratesPer100() + "'"+
                " AND "+EXPIRY +" = '"+ingredient.getDateExpiry() + "'";
        return DefaultRepository.getIdByQuery(GET_ID);
    }

}
