package model;

import utility.IncorrectValueException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.regex.Matcher;


public class Recipe implements Serializable, Comparable<Recipe>{
    public static final String PATTERN;
    static {
        PATTERN = "^id: ([A-Za-z0-9]+)\\|\\| title: ([A-Za-z0-9\\s]{1,100})\\|\\| weight: ([0-9.,]{0,6})\\|\\| cuisineId: ([A-Za-z0-9]+)\\|\\| describe: ([A-Za-z0-9\\s]{1,1000})";
    }

    @NotNull(message = " should be set ")
    private  String id;

    @NotEmpty(message = "Can't be empty")
    @Size(min=3, message = "Too short text")
    @Size(max=200, message = "Too long text")
    private String title;

    private double weight;

    private String cuisineId;

    @Size(max = 1000, message = "Too long text")
    private String describe;

    public Recipe() {
    }

    @Override
    public String toStringTXT() {
        return String.format("id: %s|| title: %s|| weight: %s|| cuisineId: %s|| describe: %s",
                id,title,weight, cuisineId,describe)+"\n";
    }

    @Override
    public List<Recipe> fromString(List<String> strings) throws IncorrectValueException {
        java.util.regex.Pattern pat = java.util.regex.Pattern.compile(PATTERN);
        Matcher matcher;
        List<Recipe> recipes=new ArrayList<>();
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                recipes.add(new Recipe.Builder().setId(matcher.group(1)).setTitle(matcher.group(2))
                        .setWeight(Double.parseDouble(matcher.group(3))).setSomeCuisine(matcher.group(4))
                        .setDescribe(matcher.group(5)).build());
            }
        }
        return recipes;
    }

    @Override
    public int compareTo(Recipe recipe) {
        return title.compareTo(recipe.getTitle());
    }


    public static class Builder {
        private final Recipe newRecipe;

        public Builder() {
            newRecipe=new Recipe();
        }

        public Builder setId(String id){
            newRecipe.id = id;
            return this;
        }

        public Builder setTitle(String title){
            newRecipe.title = title;
            return this;
        }

        public Builder setWeight(double weight){
            newRecipe.weight = weight;
            return this;
        }

        public Builder setDescribe(String describe){
            newRecipe.describe = describe;
            return this;
        }

        public Builder setSomeCuisine(String cuisineId){
            newRecipe.cuisineId = cuisineId;
            return this;
        }

        public Recipe build() throws IncorrectValueException {
            if(newRecipe.id==null)newRecipe.id = UUID.randomUUID().toString();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Recipe>> violations = validator.validate(newRecipe);

            StringBuilder sb=new StringBuilder();
            for (ConstraintViolation<Recipe> violation : violations) {
                sb.append(violation.getInvalidValue()).append(" : ").append(violation.getMessage());
            }
            if(sb.length()==0) return newRecipe;
            throw new IncorrectValueException(sb.toString());
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWeight() {
        return weight;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", weight=" + weight +
                ", cuisineId='" + cuisineId + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return  title.equals(recipe.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, weight, cuisineId, describe);
    }
}
