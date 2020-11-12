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
        PATTERN = "^id: ([A-Za-z0-9]+)\\|\\| title: ([A-Za-z0-9\\s]{1,100})";
    }

    @NotNull(message = " should be set ")
    private  String id;

    @Size(min=3, message = "Too short text")
    @Size(max=200, message = "Too long text")
    @NotEmpty(message = " couldn't be empty")
    private String title;

    public Cuisine() {
    }

    public static class Builder {
        private final Cuisine newCuisine;

        public Builder() {
            newCuisine=new Cuisine();
        }

        public Builder setId(String id){
            newCuisine.id = id;
            return this;
        }

        public Builder setTitle(String title){
            newCuisine.title = title;
            return this;
        }

        public Cuisine build() throws IncorrectValueException {
            if(newCuisine.id==null)newCuisine.id = UUID.randomUUID().toString();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Cuisine>> violations = validator.validate(newCuisine);

            StringBuilder sb=new StringBuilder();
            for (ConstraintViolation<Cuisine> violation : violations) {
                sb.append(violation.getInvalidValue()).append(" : ").append(violation.getMessage());
            }
            if(sb.length()==0) return newCuisine;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuisine)) return false;
        Cuisine cuisine = (Cuisine) o;
        return title.equals(cuisine.title);
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toStringTXT() {
        return String.format("id: %s|| title: %s", id,title)+"\n";
    }

    @Override
    public List<Cuisine> fromString(List<String> strings) throws IncorrectValueException {
        List<Cuisine> cuisines=new ArrayList<>();
        Pattern pat = Pattern.compile(PATTERN);
        Matcher matcher;
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                cuisines.add(new Cuisine.Builder().setId(matcher.group(1)).setTitle(matcher.group(2)).build());
            }
        }
        return cuisines;
    }
}
