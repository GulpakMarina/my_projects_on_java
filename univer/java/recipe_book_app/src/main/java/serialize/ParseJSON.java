package serialize;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class ParseJSON<T> implements Serializer<T>{

    @Override
    public void toFile(T data, File file) {
        Gson gson=new Gson();
        try(FileWriter writer=new FileWriter(file)) {
            gson.toJson(data, writer);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public T fromFile(File file, Class<T> clazz) {
        T data=null;
        try(FileReader reader=new FileReader(file)){
            data = new Gson().fromJson(reader, clazz);
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

}