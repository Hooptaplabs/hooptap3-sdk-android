package com.hooptap.sdkbrandclub.Engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hooptap.sdkbrandclub.Deserializer.HooptapResponseDeserializer;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapResponse;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
import com.hooptap.sdkbrandclub.Utilities.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class ParseObjects {

    public static <T> T getObjectParse(JSONObject json) {
        try {

            final GsonBuilder gsonBuilder = new GsonBuilder();
            //Parseamos de forma manual la clase HT Response ya que el array puede contener varios tipos
            gsonBuilder.registerTypeAdapter(HooptapResponse.class, new HooptapResponseDeserializer<T>());
            final Gson gson = gsonBuilder.create();

            //Miramos que tipo es el objeto
            String itemType = json.getString("itemType");
            Log.e("PARSE","PARSE");
            //Obtenemos la clase que vamos a utilizar para parsear el objeto gracias al mapeador y la key anterior
            Class cls = MapperObjects.getClassFromKey(itemType);
            //Casteamos a un objeto generico (T) mediante la lib GSON y deserializamos el JSonObject response
            T obj = (T) gson.fromJson(String.valueOf(json), cls);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Convierte el Object que devuelve el SDK de AWS en un JsonObject y coge lo que hay dentro de response
    public static JSONObject getObjectJson(Object o) {
        try {
            Gson g = new Gson();
            String value = g.toJson(o);
            return new JSONObject(value).getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
