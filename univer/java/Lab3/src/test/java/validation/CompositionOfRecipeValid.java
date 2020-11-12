package validation;

import model.CompositionOfRecipe;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.IncorrectValueException;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class CompositionOfRecipeValid {

    @DataProvider
    public Object[][] providerCompositionOfRecipeValid(){
        return new Object[][]{{"1","I1",200.,"GRAm","R1"}};
    }

    @Test(dataProvider = "providerCompositionOfRecipeValid")
    public void testBuilder(String id,String inId, Double am, String v, String rId) throws IncorrectValueException {
        assertEquals(new CompositionOfRecipe.Builder().setId(id).setIngredientId(inId).setAmountOfIngredient(am)
                .setValue(v).setRecipeId(rId).build().getClass(), CompositionOfRecipe.class);
    }

    @DataProvider
    public Object[][] providerCompositionOfRecipeInvalid(){
        return new Object[][]{{null,null,200.,"GRAme",null}};
    }

    @Test(dataProvider = "providerCompositionOfRecipeInvalid")
    public void negativeBuilder(String id,String inId, Double am, String v, String rId){
        assertThrows(IncorrectValueException.class,
                ()->new CompositionOfRecipe.Builder().setId(id).setIngredientId(inId).setAmountOfIngredient(am)
                .setValue(v).setRecipeId(rId).build());

        try{
            new CompositionOfRecipe.Builder().setId(id).setIngredientId(inId).setAmountOfIngredient(am)
                    .setValue(v).setRecipeId(rId).build();
        }catch (IncorrectValueException e){
            List<String> strings1= Arrays.asList(e.getMessage().split("]"));
            List<String> strings2= Arrays.asList("[ null : should be set ][ null : should be set ][ GRAme : not a value ]".split("]"));
            assertTrue(strings1.containsAll(strings2));
        }
    }

}
