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
import java.util.regex.Pattern;

public class Cuisine implements Serializable{
    public static final String PATTERN;
    static {
        PATTERN = "title: ([A-Za-z0-9\\s]{3,100})";
    }

    private int id;

    @NotEmpty(message = " couldn't be empty")
    @Size(min=3, message = "too short text")
    @Size(max=200, message = "too long text")
    private String title;

    public Cuisine() {
    }

    public static class Builder {
        private final Cuisine newCuisine;

        public Builder() {
            newCuisine=new Cuisine();
        }


        public Builder setTitle(String title){
            newCuisine.title = title;
            return this;
        }

        public Cuisine build() throws IncorrectValueException {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Cuisine>> violations = validator.validate(newCuisine);
            for (ConstraintViolation<Cuisine> violation : violations) {
                throw new IncorrectValueException(violation.getMessage());
            }
            return newCuisine;

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuisine cuisine = (Cuisine) o;
        return Objects.equals(title, cuisine.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public String toStringTXT() {
        return String.format("title: %s", title)+"\n";
    }

    @Override
    public List<Cuisine> fromString(List<String> strings) throws IncorrectValueException {
        List<Cuisine> cuisines=new ArrayList<>();
        Pattern pat = Pattern.compile(PATTERN);
        Matcher matcher;
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                cuisines.add(new Cuisine.Builder().setTitle(matcher.group(1)).build());
            }
        }
        return cuisines;
    }
}
