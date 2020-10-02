

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


import classes.Human;
import classes.Lift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;//gson


public class ParseJson {




    public static Object[][] parseJson(File fileName) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject resultFromJSON = (JSONObject) parser.parse(new FileReader(fileName));
            JSONArray tests = (JSONArray) resultFromJSON.get("tests");
            Iterator<JSONObject> iterator = tests.iterator();
            Object[][] objects=new Object[tests.size()][3];
            for(int i=0;i<tests.size();i++){
                JSONObject currant=iterator.next();
                JSONArray JSONLifts= (JSONArray) currant.get("lifts");
                Iterator<JSONObject> iteratorJSONLift = JSONLifts.iterator();
                Lift[] lifts=new Lift[JSONLifts.size()];
                for(int j=0;j<JSONLifts.size();j++){
                    JSONObject currantJSONLift=iteratorJSONLift.next();
                    Lift lift=new Lift(Integer.parseInt( currantJSONLift.get("id").toString()),
                            Integer.parseInt(currantJSONLift.get("fromFloor").toString()),
                            Integer.parseInt(currantJSONLift.get("toFloor").toString()));
                    lifts[j]=lift;
                }
                JSONObject JSONHuman= (JSONObject) currant.get("human");
                Human human=null;
                if(JSONHuman.get("direction").toString().equalsIgnoreCase("down")){
                    human=new Human(Integer.parseInt( JSONHuman.get("currentFloor").toString()), Human.Direction.DOWN);
                }
                if(JSONHuman.get("direction").toString().equalsIgnoreCase("UP")){
                     human=new Human(Integer.parseInt(JSONHuman.get("currentFloor").toString()), Human.Direction.UP);
                }

                objects[i]= new Object[]{lifts, human, Integer.parseInt(currant.get("expected").toString())};
            }
            return objects;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new Object[0][0];
    }
}
