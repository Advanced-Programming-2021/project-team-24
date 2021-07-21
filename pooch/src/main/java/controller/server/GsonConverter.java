package controller.server;

import com.google.gson.Gson;
import model.Request;
import model.Response;

public class GsonConverter {
    public static String serialize(Object object){
        return new Gson().toJson(object);
    }
    public static Object deserialize(String json, Class aClass){
        return new Gson().fromJson(json, aClass);
    }
}
