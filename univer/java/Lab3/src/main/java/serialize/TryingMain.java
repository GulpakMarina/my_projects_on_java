package serialize;

import model.*;
import utility.IncorrectValueException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TryingMain {
    public static void main(String[] args) throws IOException, IncorrectValueException {
        ParseTXT<List<String>> parseTXT=new ParseTXT<>();
        File file;
        String ingredientString= new Ingredient.Builder().setId("1").setTitle("title1").setDateExpiry(LocalDate.now())
                .setWhereToBuy("xs").setPrise(2)
                .setProteinsPer100(1).setFatsPer100(1).setCarbohydratesPer100(1).build().toStringTXT() +
                new Ingredient.Builder().setId("2").setTitle("title2").setDateExpiry(LocalDate.now().plusDays(1))
                        .setWhereToBuy("xs").setPrise(2)
                        .setProteinsPer100(2).setFatsPer100(1).setCarbohydratesPer100(1).build().toStringTXT();
        file=new File("src/test/txtData/Ingredient.txt");
        parseTXT.toFile(ingredientString,file);
        for(Ingredient ingredient: new Ingredient().fromString(parseTXT.fromFile(file)))
                System.out.println(ingredient);

        String inventoryString=new Inventory.Builder().setId("ID1").setTitle("title1").setRecipeId("id1").build().toStringTXT()+
                new Inventory.Builder().setId("ID2").setTitle("title2").setRecipeId("id2").build().toStringTXT();
        file=new File("src/test/txtData/Inventory.txt");
        parseTXT.toFile(inventoryString,file);
        for(Inventory inventory: new Inventory().fromString(parseTXT.fromFile(file)))
            System.out.println(inventory);

        String cuisineString=new Cuisine.Builder().setId("ID1").setTitle("title1").build().toStringTXT()+
                new Cuisine.Builder().setId("ID2").setTitle("title2").build().toStringTXT();
        file=new File("src/test/txtData/Cuisine.txt");
        parseTXT.toFile(cuisineString,file);
        for(Cuisine cuisine: new Cuisine().fromString(parseTXT.fromFile(file)))
            System.out.println(cuisine);

        String recipeString=new Recipe.Builder().setId("ID1").setTitle("title1").setWeight(123)
                .setSomeCuisine("ID1")
                .setDescribe("QWERTY").build().toStringTXT() + "\n" +
                new Recipe.Builder().setId("ID2").setTitle("title2").setWeight(3).setSomeCuisine("ID2")
                        .setDescribe("QWERTY").build().toStringTXT();
        file=new File("src/test/txtData/Recipe.txt");
        parseTXT.toFile(recipeString,file);
        for (Recipe recipe: new Recipe().fromString(parseTXT.fromFile(file)))
            System.out.println(recipe);

        String compositionString=new CompositionOfRecipe.Builder().setId("ID1")
                .setIngredientId("iid1").setAmountOfIngredient(123).setValue("GLASS").setRecipeId("Rid1").build().toStringTXT()+ "\n"+
                new CompositionOfRecipe.Builder().setId("ID2").setIngredientId("iid2")
                        .setAmountOfIngredient(4).setValue("GRAM").setRecipeId("Rid2").build().toStringTXT();
        file=new File("src/test/txtData/CompositionOfRecipe.txt");
        parseTXT.toFile(compositionString,file);
        for (CompositionOfRecipe compositionOfRecipe: new CompositionOfRecipe().fromString(parseTXT.fromFile(file)))
            System.out.println(compositionOfRecipe);

        System.out.println("JSON");
        ParseJSON parseJSON=new ParseJSON();
        CompositionOfRecipe[] compositionOfRecipe =new CompositionOfRecipe[]{
                new CompositionOfRecipe.Builder().setId("ID1").setIngredientId("iid1").setAmountOfIngredient(123)
                .setValue("GLASS").setRecipeId("Rid1").build(),
                new CompositionOfRecipe.Builder().setId("ID2").setIngredientId("iid2").setAmountOfIngredient(4)
                .setValue("GRAM").setRecipeId("Rid2").build()};
        file=new File("src/test/jsonData/CompositionOfRecipe.json");
        parseJSON.toFile(compositionOfRecipe,file);
        for (CompositionOfRecipe composition: (CompositionOfRecipe[])parseJSON.fromFile(file,CompositionOfRecipe[].class))
            System.out.print(composition+"\n");

        ParseXML parseXML=new ParseXML();
        Recipe[] recipes =new Recipe[]{
                new Recipe.Builder().setId("ID1").setTitle("title1").setWeight(123).setSomeCuisine("Cid1").setDescribe("QWERTY").build(),
                new Recipe.Builder().setId("ID2").setTitle("title2").setWeight(4).setSomeCuisine("Cid2").setDescribe("QWERTY").build()};
        file=new File("src/test/xmlData/Recipe.xml");
        parseXML.toFile(recipes,file);
        for (Recipe recipe: (Recipe[])parseXML.fromFile(file,Recipe[].class))
            System.out.print(recipe+"\n");

    }
}
