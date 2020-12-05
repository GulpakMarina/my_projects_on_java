package service;

import model.Cuisine;
import utility.AlreadyExists;

import java.util.ArrayList;
import java.util.List;

public class CuisineService {
    private List<Cuisine> cuisines=new ArrayList<>();

    public void add(Cuisine cuisine) throws AlreadyExists {
        if(cuisine!=null)
            if(cuisines.contains(cuisine))throw new AlreadyExists("title");
            else cuisines.add(cuisine);
    }
}
