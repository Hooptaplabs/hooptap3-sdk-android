package com.hooptap.sdkbrandclub.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 22/12/15.
 */
public class Utils {
    static Gson g = new Gson();

    /**
     * Funcion encargada de comprobar si tiene paginacion, en caso de no tener nada
     * se le asignara valores por defecto.
     *
     * @param pagination es un hashMap con los siguiente valores "page_number","size_page"
     */
    public static HashMap<String, Object> checkPagination(HashMap<String, Object> pagination) {
        if (pagination == null) {

            pagination = new HashMap<>();
            pagination.put("page_number", 1);
            pagination.put("size_page", 100);

        }
        return pagination;
    }

    /**
     * This method returns true if the objet is null.
     *
     * @param object
     * @return true | false
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String value = (String) object;
            return isEmpty(value);
        }
        return false;
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     *
     * @param string
     * @return true | false
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            return true;
        }
        return false;
    }


    /**
     * Funcion encargada de transformar el object que devuelve la libreria en un objeto definido
     * por el usuario, en principio sera JSON, pero en el futuro aqui es donde se realizaran cambios
     * para que devuelvan los modelos especificos.
     */
    public static <T> T getObjectParse(Object o) {
        /*try {
            //Obtener hashmap con gson
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> myMap = g.fromJson("{'k1':'apple','k2':'orange'}", type);
            Class<HooptapLevel> cls ;
            String value = g.toJson(o);
            cls = (Class<HooptapLevel>) Class.forName("com.hooptap.sdkbrandclub.Models." + "HooptapLevel");
            HooptapItem cl = cls.getDeclaredConstructor(String.class).newInstance(value);
            return (T) cl;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }*/

        String value = g.toJson(o);

        try {
            return (T) new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseImageToString(Bitmap imagen) {
        String encodedImageData = getEncoded64ImageStringFromBitmap(imagen);
        return encodedImageData;
    }


    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    /**
     * Metodo que asigna un token al objeto Hooptap para permitir el acceso a la API
     *
     * @param token necesario para poder realizar las peticiones e identificar al usuario
     */
    public static void setToken(Object token) {
        JSONObject jsonObject = getObjectParse(token);
        try {
            JSONObject info = jsonObject.getJSONObject("response");
            if (!info.isNull("access_token")) {
                String infoToken = info.getString("access_token");
                Hooptap.getTinyDB().putString("ht_token", "Bearer " + infoToken);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json.json");
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
