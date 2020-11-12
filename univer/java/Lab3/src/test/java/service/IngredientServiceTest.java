package service;

import model.CompositionOfRecipe;
import model.Ingredient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import serialize.ParseJSON;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.testng.Assert.*;

public class IngredientServiceTest {

   ParseJSON<Ingredient[][]> parseJSON=new ParseJSON();
    Ingredient[][] ingredients;

   @BeforeClass
   public void setUp(){
       File file=new File("src/test/jsonData/IngredientServiceTest.json");
       ingredients=parseJSON.fromFile(file,Ingredient[][].class);
   }

    @DataProvider
    public Object[][] testSortIngredientByPriseProvider() {
         return new Object[][]{{ingredients[0],ingredients[1]}};
    }

    @Test(dataProvider = "testSortIngredientByPriseProvider")
    public void testSortIngredientByPrise(Ingredient[] data, Ingredient[] res) {
        assertEquals(new IngredientService(Arrays.asList(data)).sortIngredientByPrise(),Arrays.asList(res));
   }

    @DataProvider
    public Object[][] testSortIngredientsByDateWithStreamProvider() {
        return new Object[][]{{ingredients[0],ingredients[1]}};
    }

    @Test(dataProvider = "testSortIngredientsByDateWithStreamProvider")
    public void testSortIngredientsByDateWithStream(Ingredient[] data, Ingredient[] res) {
        IngredientService ingredientService=new IngredientService(Arrays.asList(data.clone()));
        assertEquals(ingredientService.sortIngredientsByDateWithStream(),Arrays.asList(res));
    }

    @DataProvider
    public Object[][] testCompareToIngredientsProvider() {
        return new Object[][]{{ingredients[0],ingredients[2]}};
    }

    @Test(dataProvider = "testCompareToIngredientsProvider")
    public void testCompareToIngredients(Ingredient[] data, Ingredient[] res) {
        Arrays.sort(data);
        assertEquals(data,res);
    }

   /* public static void main(String[] args) {
        Ingredient[][] data = new Ingredient[][]{{
                new Ingredient.Builder("flour", LocalDate.now()).setPrise(34.25).build(),
                new Ingredient.Builder("eggs",LocalDate.now().minusDays(30)).setPrise(12.5).build(),
                new Ingredient.Builder("cucumber",LocalDate.now().minusDays(10)).setPrise(20.3).build(),
                new Ingredient.Builder("garlic",LocalDate.now().plusDays(2)).setPrise(40).build()},
                {
                new Ingredient.Builder("eggs",LocalDate.now().minusDays(30)).setPrise(12.5).build(),
                new Ingredient.Builder("cucumber",LocalDate.now().minusDays(10)).setPrise(20.3).build(),
                new Ingredient.Builder("flour", LocalDate.now()).setPrise(34.25).build(),
                new Ingredient.Builder("garlic",LocalDate.now().plusDays(2)).setPrise(40).build()},
                {
                new Ingredient.Builder("cucumber",LocalDate.now().minusDays(10)).setPrise(20.3).build(),
                new Ingredient.Builder("eggs",LocalDate.now().minusDays(30)).setPrise(12.5).build(),
                new Ingredient.Builder("flour", LocalDate.now()).setPrise(34.25).build(),
                new Ingredient.Builder("garlic",LocalDate.now().plusDays(2)).setPrise(40).build()}};
        File file=new File("src/test/jsonData/IngredientServiceTest.json");
        parseJSON.toFile(data,file);
    }*/


}