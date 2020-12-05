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

public class Inventory implements Serializable{
    public static final String PATTERN;
    static {
        PATTERN = "^id: ([A-Za-z0-9]+)\\|\\| title: ([A-Za-z0-9\\s]{1,100})\\|\\| recipeId: ([A-Za-z0-9]+)";
    }

    @NotNull(message = " should be set ")
    private String id;

    @Size(min=3, message = "Too short text")
    @Size(max=200, message = "Too long text")
    @NotEmpty(message = "Can't be empty")
    private String title;

    @NotNull(message = " should be set ")
    private String recipeId;

    public Inventory() {
    }

    public static class Builder {
        private final Inventory newInventory;

        public Builder() {
            newInventory=new Inventory();
        }

        public Builder setId(String id){
            newInventory.id = id;
            return this;
        }

        public Builder setTitle(String title){
            newInventory.title = title;
            return this;
        }

        public Builder setRecipeId(String recipeId){
            newInventory.recipeId = recipeId;
            return this;
        }

        public Inventory build() throws IncorrectValueException {
            if(newInventory.id==null)newInventory.id = UUID.randomUUID().toString();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Inventory>> violations = validator.validate(newInventory);

            StringBuilder sb=new StringBuilder();
            for (ConstraintViolation<Inventory> violation : violations) {
                sb.append(violation.getInvalidValue()).append(" : ").append(violation.getMessage());
            }
            if(sb.length()==0) return newInventory;
            throw new IncorrectValueException(sb.toString());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return title.equals(inventory.title) &&
                Objects.equals(recipeId, inventory.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, recipeId);
    }

    @Override
    public String toStringTXT() {
        return String.format("id: %s|| title: %s|| recipeId: %s",
                id,title, recipeId)+"\n";
    }

    @Override
    public List<Inventory> fromString(List<String> strings) throws IncorrectValueException {
        List<Inventory> inventories=new ArrayList<>();
        Pattern pat = Pattern.compile(PATTERN);
        Matcher matcher;
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                inventories.add(new Inventory.Builder().setId(matcher.group(1)).setTitle(matcher.group(2))
                        .setRecipeId(matcher.group(3)).build());
            }
        }
        return inventories;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", recipeId='" + recipeId + '\'' +
                '}';
    }
}
