package com.hooptap.sdkbrandclub.Utilities;

import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 22/12/15.
 */
public class Utils {

    /**
     * Metodo que asigna un token al objeto Hooptap para permitir el acceso a la API
     *
     * @param token necesario para poder realizar las peticiones e identificar al usuario
     */
    public static void setToken(Object token) {
        JSONObject info = ParseObjects.convertObjectToJsonResponse(token);
        try {
            if (!info.isNull("externalId")) {
                String user_id = info.getString("externalId");
                setUserId(user_id);
            }
            if (!info.isNull("access_token")) {
                String infoToken = info.getString("access_token");
                Hooptap.getTinyDB().putString("ht_token", "Bearer " + infoToken);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void setUserId(String user_id){
        Hooptap.getTinyDB().putString("ht_user_id", user_id);
    }

    public static String loadJSONFromAsset(InputStream is) {
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
