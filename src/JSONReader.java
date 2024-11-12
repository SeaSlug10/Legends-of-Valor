import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSONReader {
    /*
    * reads a specific JSON file and delivers information from it
    * */

    private String file;
    private JSONObject jsonObj;

    public JSONReader(String file) {
        try{
            this.file = file;
            Object obj = new JSONParser().parse(new FileReader(file));
            this.jsonObj = (JSONObject) obj;
        } catch (Exception e) {
            System.out.println("FAIL:");
            e.printStackTrace();
        }
    }

    public String getString(String key){
        return (String)jsonObj.get(key);
    }

    public int getInt(String key){
        return Math.toIntExact((long)jsonObj.get(key));
    }

    public int[] getIntArray(String key){
        JSONArray arr = (JSONArray)jsonObj.get(key);
        int[] arr2 = new int[arr.size()];
        for(int i = 0; i < arr.size(); i++){
            arr2[i] = Math.toIntExact((long)arr.get(i));
        }
        return arr2;
    }

    public String[] getStringArray(String key){
        JSONArray arr = (JSONArray)jsonObj.get(key);
        String[] arr2 = new String[arr.size()];
        for(int i = 0; i < arr.size(); i++){
            arr2[i] = (String)arr.get(i);
        }
        return arr2;
    }

    public Map[] getMaps(String key){
        JSONArray arr = (JSONArray)jsonObj.get(key);
        Map[] arr2 = new Map[arr.size()];
        for(int i = 0; i < arr.size(); i++){
            arr2[i] = (Map)arr.get(i);
        }
        return arr2;
    }

}
