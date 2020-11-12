package service;

import model.Ingredient;
import model.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientService {
    private List<Ingredient> ingredients;

    public IngredientService(List<Ingredient> ingredients) {
        this.ingredients = ingredients.stream().distinct().collect(Collectors.toList());
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> sortIngredientByPrise(){
        List<Ingredient> result = ingredients;
        Collections.sort(result, (ingredient, t1) -> {
            if (ingredient.getPrise() < t1.getPrise()) return -1;
            if (ingredient.getPrise() > t1.getPrise()) return 1;
            else return 0;
        });
        return result;

    }

    public List<Ingredient> sortIngredientsByDateWithStream(){
        return ingredients.stream().sorted((ing1, ing2) -> {
            if(ing1.getDateExpiry().isBefore(ing2.getDateExpiry()))return -1;
            if(ing1.getDateExpiry().isAfter(ing2.getDateExpiry())) return 1;
            return 0;
        }).collect(Collectors.toList());
    }


}
