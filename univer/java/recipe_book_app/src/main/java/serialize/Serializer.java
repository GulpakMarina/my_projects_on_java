package serialize;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Serializer<T>{

    void toFile(T data, File file) throws IOException;

    T fromFile(File file, Class<T> clazz) throws IOException;


}
