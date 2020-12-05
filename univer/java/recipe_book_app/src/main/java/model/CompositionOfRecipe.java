package model;

import utility.IncorrectValueException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompositionOfRecipe implements Serializable{
    public static final String PATTERN;
    static {
        PATTERN = "^id: ([A-Za-z0-9]+)\\|\\| ingredientId: ([A-Za-z0-9]+)\\|\\| amountOfIngredient: ([0-9.,]{0,6})\\|\\| value: ([A-Za-z]{1,10})\\|\\| recipeId: ([A-Za-z0-9]+)";
    }

    @NotNull(message = "should be set")
    private String id;

    @NotNull(message = "should be set")
    private String ingredientId;

    private double amountOfIngredient;
    private String valueId;

    @NotNull(message = "should be set")
    private String recipeId;

    public CompositionOfRecipe() {

    }
    public static class Builder {
        private final CompositionOfRecipe newCompositionOfRecipe;

        public Builder() {
            newCompositionOfRecipe=new CompositionOfRecipe();
        }

        public Builder setId(String id){
            newCompositionOfRecipe.id = id;
            return this;
        }

        public Builder setIngredientId(String ingredientId){
            newCompositionOfRecipe.ingredientId = ingredientId;
            return this;
        }

        public Builder setAmountOfIngredient(double amountOfIngredient){
            newCompositionOfRecipe.amountOfIngredient = amountOfIngredient;
            return this;
        }

        public Builder setValueId(String value_id){
            newCompositionOfRecipe.valueId =value_id;
            return this;
        }

        public Builder setRecipeId(String recipeId){
            newCompositionOfRecipe.recipeId = recipeId;
            return this;
        }

        public CompositionOfRecipe build() throws IncorrectValueException {
            if(newCompositionOfRecipe.id==null)newCompositionOfRecipe.id = UUID.randomUUID().toString();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<CompositionOfRecipe>> violations = validator.validate(newCompositionOfRecipe);

            StringBuilder sb=new StringBuilder();
            for (ConstraintViolation<CompositionOfRecipe> violation : violations) {
                sb.append("[ ").append(violation.getInvalidValue()).append(" : ").append(violation.getMessage()).append(" ]");
            }
            if(sb.length()==0) return newCompositionOfRecipe;
            throw new IncorrectValueException(sb.toString());
        }
    }

    @Override
    public List<CompositionOfRecipe> fromString(List<String> strings) throws IncorrectValueException {
        List<CompositionOfRecipe> compositionOfRecipes=new ArrayList<>();
        Pattern pat = Pattern.compile(PATTERN);
        Matcher matcher;
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                compositionOfRecipes.add(new CompositionOfRecipe.Builder().setId(matcher.group(1))
                        .setIngredientId(matcher.group(2)).setAmountOfIngredient(Double.parseDouble(matcher.group(3)))
                        .setValueId(matcher.group(4)).setRecipeId(matcher.group(5)).build());
            }
        }
        return compositionOfRecipes;
    }

    @Override
    public String toStringTXT() {
        return String.format("id: %s|| ingredientId: %s|| amountOfIngredient: %s|| value: %s|| recipeId: %s",
                id,ingredientId,amountOfIngredient, valueId,recipeId)+"\n";
    }

    public String getId() {
        return id;
    }

    public void setAmountOfIngredient(double amountOfIngredient) {
        this.amountOfIngredient = amountOfIngredient;
    }



    public double getAmountOfIngredient() {
        return amountOfIngredient;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CompositionOfRecipe{" +
                "id='" + id + '\'' +
                ", ingredientId='" + ingredientId + '\'' +
                ", amountOfIngredient=" + amountOfIngredient +
                ", valueId='" + valueId + '\'' +
                ", recipeId='" + recipeId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositionOfRecipe)) return false;
        CompositionOfRecipe that = (CompositionOfRecipe) o;
        return Double.compare(that.getAmountOfIngredient(), getAmountOfIngredient()) == 0 &&
                ingredientId.equals(that.ingredientId) &&
                Objects.equals(getValueId(), that.getValueId()) &&
                recipeId.equals(that.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, getAmountOfIngredient(), getValueId(), recipeId);
    }


}
