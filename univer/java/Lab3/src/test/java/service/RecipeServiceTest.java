package service;

import model.Ingredient;
import model.Recipe;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import serialize.ParseJSON;
import utility.AlreadyExists;
import utility.IncorrectValueException;

import java.io.File;
import java.util.Arrays;

import static org.testng.Assert.*;

public class RecipeServiceTest {
    ParseJSON<Recipe[][]> parseJSON=new ParseJSON();
    Recipe[][] recipes;

    @BeforeClass
    public void setUp(){
        File file=new File("src/test/jsonData/RecipeServiceTest.json");
        recipes=parseJSON.fromFile(file,Recipe[][].class);
    }

    @DataProvider
    public Object[][] testSortRecipeByWeightProvider() {
        return new Object[][]{{recipes[0],recipes[2]}};
    }

    @Test(dataProvider = "testSortRecipeByWeightProvider")
    public void testSortRecipeByWeight(Recipe[] data,Recipe[] res) {
        assertEquals(new RecipeService(Arrays.asList(data)).sortRecipeByWeight(),Arrays.asList(res));
    }

    @DataProvider
    public Object[][] testSortRecipeByTitleProvider() {
        return new Object[][]{{recipes[0],recipes[1]}};
    }

    @Test(dataProvider = "testSortRecipeByTitleProvider")
    public void testSortRecipeByTitle(Recipe[] data,Recipe[] res) {
        assertEquals(new RecipeService(Arrays.asList(data)).sortRecipeByTitle(),Arrays.asList(res));
    }

    @DataProvider
    public Object[][] testAddProvider() {
        return new Object[][]{{"1","carbonara",10.,"qwerty","1"}};
    }

    @Test
    public void testAdd(String id,String title, Double w,) throws IncorrectValueException, AlreadyExists {
        new RecipeService(Arrays.asList(recipes[0])).add(new Recipe.Builder().setId("1").setTitle("carbonara").setWeight(10.)
        .setDescribe("qwerty").setSomeCuisine("1").build());
    }

  /*  public static void main(String[] args) {
        Recipe[][] recipes=new Recipe[][]{{
            new Recipe.Builder("pie").setWeight(200).build(),
                new Recipe.Builder("vareniki").setWeight(100).build(),
                new Recipe.Builder("galushki").setWeight(500).build()},{
                new Recipe.Builder("galushki").setWeight(500).build(),
                new Recipe.Builder("pie").setWeight(200).build(),
                new Recipe.Builder("vareniki").setWeight(100).build()},{
                new Recipe.Builder("vareniki").setWeight(100).build(),
                new Recipe.Builder("pie").setWeight(200).build(),
                new Recipe.Builder("galushki").setWeight(500).build()}};
        File file=new File("src/test/jsonData/RecipeServiceTest.json");
        parseJSON.toFile(recipes,file);
    }*/


}