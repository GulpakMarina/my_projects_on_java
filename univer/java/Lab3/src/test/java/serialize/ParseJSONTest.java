package serialize;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import model.CompositionOfRecipe;
import utility.IncorrectValueException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.testng.Assert.*;

public class ParseJSONTest<T> {

    ParseJSON parseJSON=new ParseJSON();

    @Test(dataProvider = "toFailProvider",priority = 1)
    public void testToFile(T data, File file,String res) throws IOException {
        Scanner scanner=new Scanner(file);
        parseJSON.toFile(data,file);
        assertEquals(scanner.next(),res);
    }

    @DataProvider
    public Object[][] toFailProvider() throws IncorrectValueException {
        File file=new File("src/test/jsonData/CompositionOfRecipe.json");
        CompositionOfRecipe[] compositionOfRecipes =new CompositionOfRecipe[]{
                new CompositionOfRecipe.Builder().setId("ID1").setIngredientId("iid1")
                        .setAmountOfIngredient(123).setValue("GLASS").setRecipeId("Rid1").build(),
                new CompositionOfRecipe.Builder().setId("ID2").setIngredientId("iid2").setAmountOfIngredient(4)
                        .setValue("GRAM").setRecipeId("Rid2").build()};
        return new Object[][]{{compositionOfRecipes,file,"[{\"id\":\"ID1\",\"ingredientId\":\"iid1\",\"amountOfIngredient\":123.0,\"value\":\"GLASS\",\"recipeId\":\"Rid1\"},{\"id\":\"ID2\",\"ingredientId\":\"iid2\",\"amountOfIngredient\":4.0,\"value\":\"GRAM\",\"recipeId\":\"Rid2\"}]"}};
    }

    @Test(dataProvider = "fromFailProvider",priority = 2)
    public void testFromFile(File file,Class<T> clazz,T res) {
        assertEquals(parseJSON.fromFile(file,clazz),res);
    }

    @DataProvider
    public Object[][] fromFailProvider() throws IncorrectValueException {
        File file=new File("src/test/jsonData/CompositionOfRecipe.json");
        CompositionOfRecipe[] compositionOfRecipes =new CompositionOfRecipe[]{
                new CompositionOfRecipe.Builder().setId("ID1").setIngredientId("iid1")
                        .setAmountOfIngredient(123).setValue("GLASS").setRecipeId("Rid1").build(),
                new CompositionOfRecipe.Builder().setId("ID2").setIngredientId("iid2").setAmountOfIngredient(4)
                        .setValue("GRAM").setRecipeId("Rid2").build()};
        return new Object[][]{{file,CompositionOfRecipe[].class,compositionOfRecipes}};
    }
}