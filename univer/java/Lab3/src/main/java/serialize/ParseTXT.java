package serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseTXT<T>{

    public void toFile(String data, File file) {
        try(FileWriter writer = new FileWriter(file, false))
        {
            writer.write(data);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }


    public T fromFile(File file){
        List<String> strings=new ArrayList<>();
        try(FileInputStream fileInputStream= new FileInputStream(file))
        {
            Scanner scanner = new Scanner(fileInputStream).useDelimiter("\n");
            while (scanner.hasNext()) {
                strings.add(scanner.next());
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return (T) strings;
    }
}
