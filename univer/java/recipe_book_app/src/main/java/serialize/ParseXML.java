package serialize;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParseXML<T> implements Serializer<T> {

    @Override
    public void toFile(T data, File file) throws IOException {
        new XmlMapper().writeValue(file, data);
    }

    @Override
    public T fromFile(File file,Class<T> clazz) {
        T data=null;
        try(FileReader reader=new FileReader(file)){
            data = new XmlMapper().readValue(reader, clazz);
        }catch (IOException e){
            e.printStackTrace();
        }

        return data;
    }

}
