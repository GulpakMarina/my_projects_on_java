package model;

import utility.IncorrectValueException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;


public class Ingredient implements Serializable, Comparable<Ingredient>{
    public static final String PATTERN;
    static {
        PATTERN = "^id: ([A-Za-z0-9]+)\\|\\| title: ([A-Za-z0-9\\s]{1,100})\\|\\| whereToBuy: ([A-Za-z0-9\\s]{0,200})\\|\\| prise: ([0-9.,]{0,6})\\|\\| caloriesPer100: ([0-9.,]{0,6})\\|\\| proteinsPer100: ([0-9.,]{0,6})\\|\\| fatsPer100: ([0-9.,]{0,6})\\|\\| carbohydratesPer100: ([0-9.,]{0,6})\\|\\| dateExpiry: ([0-9\\/\\-\\.]{3,10})";
    }

    @NotNull(message = "Can't be empty")
    private  String id;

    @Size(min=3, message = "Too short text")
    @Size(max=200, message = "Too long text")
    @NotEmpty(message = "Can't be empty")
    private String title;

    @Size(max = 1000, message = "Too long text")
    private String whereToBuy;

    private double price;
    private int caloriesPer100;
    private int proteinsPer100;
    private int fatsPer100;
    private int carbohydratesPer100;

    @FutureOrPresent(message = "Must be future or present")
    private  LocalDate dateExpiry;


    public Ingredient() {
    }

    public String getId() {
        return id;
    }

    public LocalDate getDateExpiry() {
        return dateExpiry;
    }

    @Override
    public String toStringTXT() {
        return String.format("id: %s|| title: %s|| whereToBuy: %s|| prise: %s|| caloriesPer100: %s|| " +
                        "proteinsPer100: %s|| fatsPer100: %s|| carbohydratesPer100: %s|| dateCreate: %s",
                id,title,whereToBuy, price,caloriesPer100,proteinsPer100,fatsPer100,carbohydratesPer100, dateExpiry)+"\n";
    }

    @Override
    public List<Ingredient> fromString(List<String> strings) throws IncorrectValueException {
        java.util.regex.Pattern pat = java.util.regex.Pattern.compile(PATTERN);
        Matcher matcher;
        List<Ingredient> ingredients=new ArrayList<>();
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                ingredients.add(new Ingredient.Builder().setId(matcher.group(1)).setTitle(matcher.group(2))
                        .setDateExpiry(LocalDate.parse(matcher.group(9)))
                        .setWhereToBuy(matcher.group(3))
                        .setPrise(Double.parseDouble(matcher.group(4)))
                        .setCaloriesPer100(Integer.parseInt(matcher.group(5)))
                        .setProteinsPer100(Integer.parseInt(matcher.group(6)))
                        .setFatsPer100(Integer.parseInt(matcher.group(7)))
                        .setCarbohydratesPer100(Integer.parseInt(matcher.group(8))).build());
            }
        }
        return ingredients;
    }

    @Override
    public int compareTo(Ingredient ingredient) {
        return title.compareTo(ingredient.title);
    }

    public static class Builder{
        private final Ingredient newIngredient;
        public Builder() {
            newIngredient=new Ingredient();
        }

        public Builder setId(String id){
            newIngredient.id = id;
            return this;
        }

        public Builder setTitle(String title){
            newIngredient.title = title;
            return this;
        }

        public Builder setDateExpiry(LocalDate dateCreate){
            newIngredient.dateExpiry = dateCreate;
            return this;
        }

        public Builder setWhereToBuy(String whereToBuy){
            newIngredient.whereToBuy = whereToBuy;
            return this;
        }

        public Builder setPrise(double prise){
            newIngredient.price = prise;
            return this;
        }
        public Builder setCaloriesPer100(int caloriesPer100){
            newIngredient.caloriesPer100 = caloriesPer100;
            return this;
        }

        public Builder setProteinsPer100(int proteinsPer100){
            newIngredient.proteinsPer100 = proteinsPer100;
            return this;
        }

        public Builder setFatsPer100(int fatsPer100){
            newIngredient.fatsPer100 = fatsPer100;
            return this;
        }

        public Builder setCarbohydratesPer100(int carbohydratesPer100){
            newIngredient.carbohydratesPer100 = carbohydratesPer100;
            return this;
        }

        public Ingredient build() throws IncorrectValueException {
            if(newIngredient.id==null)newIngredient.id = UUID.randomUUID().toString();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Ingredient>> violations = validator.validate(newIngredient);

            StringBuilder sb=new StringBuilder();
            for (ConstraintViolation<Ingredient> violation : violations) {
                sb.append(violation.getInvalidValue()).append(" : ").append(violation.getMessage());
            }
            if(sb.length()==0) return newIngredient;
            throw new IncorrectValueException(sb.toString());
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getWhereToBuy() {
        return whereToBuy;
    }

    public double getPrice() {
        return price;
    }

    public int getCaloriesPer100() {
        return caloriesPer100;
    }

    public int getProteinsPer100() {
        return proteinsPer100;
    }

    public int getFatsPer100() {
        return fatsPer100;
    }

    public int getCarbohydratesPer100() {
        return carbohydratesPer100;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return title.equals(that.title) &&
                Objects.equals(dateExpiry, that.dateExpiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, dateExpiry);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", whereToBuy='" + whereToBuy + '\'' +
                ", prise=" + price +
                ", caloriesPer100=" + caloriesPer100 +
                ", proteinsPer100=" + proteinsPer100 +
                ", fatsPer100=" + fatsPer100 +
                ", carbohydratesPer100=" + carbohydratesPer100 +
                ", dateCreate=" + dateExpiry +
                '}';
    }
}

