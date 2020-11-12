package utility;

public class AlreadyExists extends Exception{
    public AlreadyExists(String field) {
        super("Such "+field+" already exists");
    }

    public AlreadyExists() {
        super("Such element already exists");
    }
}
