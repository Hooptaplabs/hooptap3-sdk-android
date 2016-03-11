package com.hooptap.sdkbrandclub.Engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hooptap.sdkbrandclub.Deserializer.HooptapActionResultDeserializer;
import com.hooptap.sdkbrandclub.Deserializer.HooptapActionsDeserializer;
import com.hooptap.sdkbrandclub.Deserializer.HooptapFeedDeserializer;
import com.hooptap.sdkbrandclub.Deserializer.HooptapListResponseDeserializer;
import com.hooptap.sdkbrandclub.Deserializer.HooptapQuestDeserializer;
import com.hooptap.sdkbrandclub.Models.HooptapAction;
import com.hooptap.sdkbrandclub.Models.HooptapActionResult;
import com.hooptap.sdkbrandclub.Models.HooptapFeed;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class ParseObjects {

    public static GsonBuilder gsonBuilder;

    public static <T> T getObjectParse(JSONObject json, OptionsMapper options) {
        try {

            gsonBuilder = new GsonBuilder();
            //Parseamos de forma manual algunas clases ya que tienen varibles que no se pueden parsear directamente
            //Los tipos pueden venir dentro de su JSON como itemType o si no vienen usaremos el que nos llega en options
            gsonBuilder.registerTypeAdapter(HooptapListResponse.class, new HooptapListResponseDeserializer<T>(options.getSubClassName()));
            gsonBuilder.registerTypeAdapter(HooptapFeed.class, new HooptapFeedDeserializer<>());
            gsonBuilder.registerTypeAdapter(HooptapActionResult.class, new HooptapActionResultDeserializer<>());
            gsonBuilder.registerTypeAdapter(HooptapQuest.class, new HooptapQuestDeserializer<>());
            gsonBuilder.registerTypeAdapter(HooptapAction.class, new HooptapActionsDeserializer<>());
            Gson gson = gsonBuilder.create();

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
    public static JSONObject convertObjectToJsonResponse(Object o) {
        try {
            Gson g = new Gson();
            String value = g.toJson(o);
            JSONObject json = new JSONObject(value);
            return json.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
