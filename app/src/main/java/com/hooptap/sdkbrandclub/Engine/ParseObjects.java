package com.hooptap.sdkbrandclub.Engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hooptap.sdkbrandclub.Deserializer.HooptapResponseDeserializer;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class ParseObjects {

    public static <T> T getObjectParse(JSONObject json, OptionsMapper options) {
        try {

            final GsonBuilder gsonBuilder = new GsonBuilder();
            //Parseamos de forma manual la clase HooptapListResponse ya que el array puede contener varios tipos
            //Los tipos pueden venir dentro de su JSON como itemType o si no vienen usaremos el que nos llega en options
            gsonBuilder.registerTypeAdapter(HooptapListResponse.class, new HooptapResponseDeserializer<T>(options.getSubClassName()));
            final Gson gson = gsonBuilder.create();

            String itemNameClass = options.getClassName();
            //Obtenemos la clase que vamos a utilizar para parsear el objeto gracias al mapeador y la key anterior
            Class cls = MapperObjects.getClassFromKey(itemNameClass);
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
