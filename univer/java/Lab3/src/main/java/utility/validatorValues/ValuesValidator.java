package utility.validatorValues;

import model.CompositionOfRecipe;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValuesValidator implements ConstraintValidator<Values,String> {

    @Override
    public void initialize(Values constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        for (CompositionOfRecipe.Value type : CompositionOfRecipe.Value.values()) {
            if(s.equalsIgnoreCase(String.valueOf(type)))return true;
        }
        return false;
    }
}
