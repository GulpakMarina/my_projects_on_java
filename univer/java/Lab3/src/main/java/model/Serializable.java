package model;

import utility.IncorrectValueException;

import java.util.List;

public interface Serializable {

    String toStringTXT();

    Object fromString(List<String> strings) throws IncorrectValueException;

}
