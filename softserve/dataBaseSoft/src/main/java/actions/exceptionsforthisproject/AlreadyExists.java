package actions.exceptionsforthisproject;

public class AlreadyExists extends Exception{
    public AlreadyExists(String errorMessage) {
        super(errorMessage);
    }

}
