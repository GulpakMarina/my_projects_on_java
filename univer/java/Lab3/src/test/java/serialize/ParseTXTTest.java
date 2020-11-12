package serialize;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import model.*;
import utility.IncorrectValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.testng.Assert.*;

public class ParseTXTTest {

    ParseTXT parseTXT=new ParseTXT();

    @Test(dataProvider = "toFailProvider")
    public void testToFile(String data, File file, String res) throws IOException {
        Scanner scanner=new Scanner(file);
        parseTXT.toFile(data,file);
        StringBuilder stringBuilder=new StringBuilder();
        while(scanner.hasNextLine()){
            stringBuilder.append(scanner.nextLine()+"\n");
        }
        assertEquals(stringBuilder.toString(),res);
    }

    @DataProvider
    public Object[][] toFailProvider() throws IncorrectValueException {
        String ingredientString= new Ingredient.Builder().setId("1").setTitle("title1")
                .setDateExpiry(LocalDate.now()).setWhereToBuy("xs")
                .setPrise(2).setProteinsPer100(1).setFatsPer100(1)
                .setCarbohydratesPer100(1).build().toStringTXT() +
                new Ingredient.Builder().setId("2").setTitle("title2")
                        .setDateExpiry(LocalDate.now().plusDays(1)).setWhereToBuy("xs").setPrise(2)
                        .setProteinsPer100(2).setFatsPer100(1).setCarbohydratesPer100(1).build().toStringTXT();
        String resIngredient="id: 1|| title: title1|| whereToBuy: xs|| prise: 2.0|| caloriesPer100: 0|| proteinsPer100: 1|| fatsPer100: 1|| carbohydratesPer100: 1|| dateCreate: "+ LocalDate.now()+"\n" +
                "id: 2|| title: title2|| whereToBuy: xs|| prise: 2.0|| caloriesPer100: 0|| proteinsPer100: 2|| fatsPer100: 1|| carbohydratesPer100: 1|| dateCreate: "+ LocalDate.now().plusDays(1)+"\n";

        String inventoryString=new Inventory.Builder().setId("ID1").setTitle("title1")
                .setRecipeId("ir1").build().toStringTXT()+
                new Inventory.Builder().setId("ID2").setTitle("title2").setRecipeId("ir2").build().toStringTXT();
        String resInventory="id: ID1|| title: title1|| recipeId: ir1\n" + "id: ID2|| title: title2|| recipeId: ir2\n";

        String cuisineString=new Cuisine.Builder().setId("ID1").setTitle("title1").build().toStringTXT()+
                new Cuisine.Builder().setId("ID2").setTitle("title2").build().toStringTXT();
        String resCuisine="id: ID1|| title: title1\n" + "id: ID2|| title: title2\n";

        String recipeString=new Recipe.Builder().setId("ID1").setTitle("title1")
                .setWeight(123).setSomeCuisine("IC1").setDescribe("QWERTY").build().toStringTXT() +
                new Recipe.Builder().setId("ID2").setTitle("title2").setWeight(3)
                        .setSomeCuisine("IC2").setDescribe("QWERTY").build().toStringTXT();
        String resRecipe="id: ID1|| title: title1|| weight: 123.0|| cuisineId: IC1|| describe: QWERTY\n" + "id: ID2|| title: title2|| weight: 3.0|| cuisineId: IC2|| describe: QWERTY\n";

        String compositionString=new CompositionOfRecipe.Builder().setId("ID1").setIngredientId("iid1")
                .setAmountOfIngredient(123).setValue("GLASS").setRecipeId("Rid1").build().toStringTXT()+
                new CompositionOfRecipe.Builder().setId("ID2").setIngredientId("iid2").setAmountOfIngredient(4)
                        .setValue("GRAM").setRecipeId("Rid2").build().toStringTXT();
        String resComposition="id: ID1|| ingredientId: iid1|| amountOfIngredient: 123.0|| value: GLASS|| recipeId: Rid1\n" + "id: ID2|| ingredientId: iid2|| amountOfIngredient: 4.0|| value: GRAM|| recipeId: Rid2\n";
        return new Object[][]{{ingredientString,new File("src/test/txtData/Ingredient.txt"),resIngredient},
                {inventoryString,new File("src/test/txtData/Inventory.txt"),resInventory},
                {cuisineString,new File("src/test/txtData/Cuisine.txt"),resCuisine},
                {recipeString,new File("src/test/txtData/Recipe.txt"),resRecipe},
                {compositionString,new File("src/test/txtData/CompositionOfRecipe.txt"),resComposition}};
    }

    @Test(dataProvider = "fromFailProvider")
    public void testFromFile(File file,List<String> res) throws FileNotFoundException {
        List<String> strings=new ArrayList<>();
        Scanner scanner = new Scanner(file).useDelimiter("\n");
        while (scanner.hasNext()) {
            strings.add(scanner.next()+"\n");
        }
        assertEquals(strings,res);
    }

    @DataProvider
    public Object[][] fromFailProvider() throws IncorrectValueException {
        List<String> ingredientString=new ArrayList<>();
        ingredientString.add(new Ingredient.Builder().setId("1").setTitle("title1")
                .setDateExpiry(LocalDate.now()).setWhereToBuy("xs").setPrise(2)
                .setProteinsPer100(1).setFatsPer100(1).setCarbohydratesPer100(1).build().toStringTXT());
        ingredientString.add(new Ingredient.Builder().setId("2").setTitle("title2")
                .setDateExpiry(LocalDate.now().plusDays(1)).setWhereToBuy("xs").setPrise(2)
                .setProteinsPer100(2).setFatsPer100(1).setCarbohydratesPer100(1).build().toStringTXT());
        List<String> inventoryString=new ArrayList<>();
        inventoryString.add(new Inventory.Builder().setId("ID1").setTitle("title1").setRecipeId("ir1").build().toStringTXT());
        inventoryString.add(new Inventory.Builder().setId("ID2").setTitle("title2").setRecipeId("ir2").build().toStringTXT());
        List<String> cuisineString=new ArrayList<>();
        cuisineString.add(new Cuisine.Builder().setId("ID1").setTitle("title1").build().toStringTXT());
        cuisineString.add(new Cuisine.Builder().setId("ID2").setTitle("title2").build().toStringTXT());
        List<String> recipeString=new ArrayList<>();
        recipeString.add(new Recipe.Builder().setId("ID1").setTitle("title1").setWeight(123)
                .setSomeCuisine("IC1").setDescribe("QWERTY").build().toStringTXT());
        recipeString.add(new Recipe.Builder().setId("ID2").setTitle("title2").setWeight(3)
                .setSomeCuisine("IC2").setDescribe("QWERTY").build().toStringTXT());
        List<String> compositionString=new ArrayList<>();
        compositionString.add(new CompositionOfRecipe.Builder().setId("ID1").setIngredientId("iid1")
                .setAmountOfIngredient(123).setValue("GLASS").setRecipeId("Rid1").build().toStringTXT());
        compositionString.add(new CompositionOfRecipe.Builder().setId("ID2").setIngredientId("iid2")
                .setAmountOfIngredient(4).setValue("GRAM").setRecipeId("Rid2").build().toStringTXT());
        return new Object[][]{{new File("src/test/txtData/Ingredient.txt"),ingredientString},
                {new File("src/test/txtData/Inventory.txt"),inventoryString},
                {new File("src/test/txtData/Cuisine.txt"),cuisineString},
                {new File("src/test/txtData/Recipe.txt"),recipeString},
                {new File("src/test/txtData/CompositionOfRecipe.txt"),compositionString}};
    }



}