package service;

import model.Cuisine;
import model.Recipe;
import utility.AlreadyExists;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeService {
    private List<Recipe> recipes;


    public RecipeService(List<Recipe> recipes) {
        this.recipes = recipes.stream().distinct().collect(Collectors.toList());
    }

    public void add(Recipe recipe) throws AlreadyExists {
        if(recipe!=null)
            if(recipes.contains(recipe))throw new AlreadyExists("title");
            else recipes.add(recipe);
    }

    public List<Recipe> sortRecipeByWeight(){
        List<Recipe> result = recipes;
        Collections.sort(result, (recipe, t1) -> {
            if (recipe.getWeight() < t1.getWeight()) return -1;
            if (recipe.getWeight() > t1.getWeight()) return 1;
            else return 0;
        });
        return result;
    }

    public List<Recipe> sortRecipeByTitle(){
        List<Recipe> result = recipes;
        Collections.sort(result, Recipe::compareTo);
        return result;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeService)) return false;
        RecipeService that = (RecipeService) o;
        return Objects.equals(getRecipes(), that.getRecipes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecipes());
    }

    @Override
    public String toString() {
        return "RecipeService{" +
                "recipes=" + recipes +
                '}';
    }
}
