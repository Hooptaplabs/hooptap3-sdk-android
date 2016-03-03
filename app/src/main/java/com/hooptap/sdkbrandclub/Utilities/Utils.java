package com.hooptap.sdkbrandclub.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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
            if (!info.isNull("_id")) {
                String user_id = info.getString("_id");
                Hooptap.getTinyDB().putString("ht_user_id", user_id);
            }
            if (!info.isNull("access_token")) {
                String infoToken = info.getString("access_token");
                Hooptap.getTinyDB().putString("ht_token", "Bearer " + infoToken);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String loadJSONFromAsset(Context context, String nameJson) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(nameJson + ".json");
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
