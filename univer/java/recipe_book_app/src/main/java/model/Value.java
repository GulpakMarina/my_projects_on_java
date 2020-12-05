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

public class Value implements Serializable{
    public static final String PATTERN;
    static {
        PATTERN = "^id: ([A-Za-z0-9]+)\\|\\| title: ([A-Za-z0-9\\s]{1,100}) \\|\\| ownerId: ([A-Za-z0-9]?)";
    }

    @NotNull(message = " should be set ")
    private  String id;

    @Size(max=100, message = "Too long text")
    @NotEmpty(message = " couldn't be empty")
    private String name;
    private String ownerId;

    public Value(){
    }

    public static class Builder {
        private final Value newVales;

        public Builder(){
            newVales = new Value();
        }

        public Builder setId(String id){
            newVales.id=id;
            return this;
        }

        public Builder setName(String name){
            newVales.name =name;
            return this;
        }

        public Builder setOwnerId(String ownerId){
            newVales.ownerId=ownerId;
            return this;
        }

        public Value build() throws IncorrectValueException {
            newVales.id = newVales.id==null? UUID.randomUUID().toString(): newVales.id;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<Value>> violations = validator.validate(newVales);

            StringBuilder sb=new StringBuilder();
            for (ConstraintViolation<Value> violation : violations) {
                sb.append(violation.getInvalidValue()).append(" : ").append(violation.getMessage());
            }
            if(sb.length()==0) return newVales;
            throw new IncorrectValueException(sb.toString());
        }

    }

    @Override
    public String toStringTXT() {
        return String.format("id: %s|| title: %s|| ownerId: %s", id,name,ownerId)+"\n";
    }

    @Override
    public Object fromString(List<String> strings) throws IncorrectValueException {
        List<Value> values=new ArrayList<>();
        Pattern pat = Pattern.compile(PATTERN);
        Matcher matcher;
        for(String str: strings) {
            matcher = pat.matcher(str);
            while (matcher.find()) {
                values.add(new Value.Builder().setId(matcher.group(1)).setName(matcher.group(2))
                        .setOwnerId(matcher.group(3)).build());
            }
        }
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return name.equals(value.name) &&
                Objects.equals(ownerId, value.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ownerId);
    }

    @Override
    public String toString() {
        return "Values{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId( String id) {
        this.id = id;
    }
}
