package serialize;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import model.Recipe;
import utility.IncorrectValueException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.testng.Assert.*;

public class ParseXMLTest<T> {

    ParseXML parseXML =new ParseXML();

    @Test(dataProvider = "toFailProvider",priority = 1)
    public void testToFile(T data, File file, String res) throws IOException {
        Scanner scanner=new Scanner(file);
        parseXML.toFile(data,file);
        assertEquals(scanner.next(),res);
    }

    @DataProvider
    public Object[][] toFailProvider() throws IncorrectValueException {
        File file=new File("src/test/xmlData/Recipe.xml");
        Recipe[] recipes =new Recipe[]{new Recipe.Builder().setId("ID1").setTitle("title1").setWeight(123)
                .setSomeCuisine("IC1").setDescribe("QWERTY").build(),
                new Recipe.Builder().setId("ID2").setTitle("title2").setWeight(3)
                        .setSomeCuisine("IC2").setDescribe("QWERTY").build()};
        return new Object[][]{{recipes,file,"<Recipes><item><id>ID1</id><title>title1</title><weight>123.0</weight><cuisineId>IC1</cuisineId><describe>QWERTY</describe></item><item><id>ID2</id><title>title2</title><weight>3.0</weight><cuisineId>IC2</cuisineId><describe>QWERTY</describe></item></Recipes>"}};
    }

    @Test(dataProvider = "fromFailProvider",priority = 2)
    public void testFromFile(File file,Class<T> clazz,T res) {
        assertEquals(parseXML.fromFile(file,clazz),res);
    }

    @DataProvider
    public Object[][] fromFailProvider() throws IncorrectValueException {
        File file=new File("src/test/xmlData/Recipe.xml");
        Recipe[] recipes =new Recipe[]{new Recipe.Builder().setId("ID1").setTitle("title1").setWeight(123)
                .setSomeCuisine("IC1").setDescribe("QWERTY").build(),
                new Recipe.Builder().setId("ID2").setTitle("title2").setWeight(3)
                        .setSomeCuisine("IC2").setDescribe("QWERTY").build()};
        return new Object[][]{{file,Recipe[].class,recipes}};
    }
}