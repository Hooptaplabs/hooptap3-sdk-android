package com.hooptap.sdkbrandclub.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Base64;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Api.HooptapApi;
import com.hooptap.sdkbrandclub.CONSTANTS;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 22/12/15.
 */
public class Utils {
    static Gson g = new Gson();

    static HashMap<CONSTANTS.RANKING_FILTER,String> lista=new HashMap<>();//Lista para la paginacion de usuarios
    static {
        lista.put(CONSTANTS.RANKING_FILTER.GENERAL,"general");
        lista.put(CONSTANTS.RANKING_FILTER.DAILY,"daily");
        lista.put(CONSTANTS.RANKING_FILTER.MONTHLY,"weekly");
        lista.put(CONSTANTS.RANKING_FILTER.WEEKLY,"monthly");
    }
    /* This method returns true if the collection is null or is empty.
    * @param collection
    * @return true | false
            */
    /**
     * Funcion encargada de comprobar si tiene paginacion, en caso de no tener nada
     * se le asignara valores por defecto.
     *
     * @param pagination es un hashMap con los siguiente valores "page_number","size_page"
     */
    public static HashMap<String, Object> checkPagination(HashMap<String, Object> pagination) {
        if (pagination==null) {

            pagination=new HashMap<>();
            pagination.put("page_number", 1);
            pagination.put("size_page", 100);

        }
        return  pagination;
    }
    public static boolean isEmpty( Collection<?> collection ){
        if( collection == null || collection.isEmpty() ){
            return true;
        }
        for(Object c : collection)
        {
            if (isEmpty(c))
                return true;
        }
        return false;
    }

    /**
     * This method returns true of the map is null or is empty.
     * @param map
     * @return true | false
     */
    public static boolean isEmpty( Map<?, ?> map ){
        if( map == null || map.isEmpty() ){
            return true;
        }
        Collection<?> values = map.values(); // returns a Collection with all the objects
        for(Object c : values)
        {
            if (isEmpty(c))
                return true;
        }
        return false;
    }

    /**
     * This method returns true if the objet is null.
     * @param object
     * @return true | false
     */
    public static boolean isEmpty( Object object ){
        if( object == null ){
            return true;
        }
        if(object instanceof String){
            String value= (String) object;
            return isEmpty(value);
        }
        return false;
    }

    /**
     * This method returns true if the input array is null or its length is zero.
     * @param array
     * @return true | false
     */
    public static boolean isEmpty( Object[] array ){
        if( array == null || array.length == 0 ){
            return true;
        }
        for(Object a:array){
            if(isEmpty(a)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     * @param string
     * @return true | false
     */
    public static boolean isEmpty( String string ){
        if( string == null || string.trim().length() == 0 ){
            return true;
        }
        return false;
    }

    public static void dialogInfo(Context context){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

// set title
        alertDialogBuilder.setTitle("Your Title");

// set dialog message
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

// create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

// show it
        alertDialog.show();
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
            Map<String, String> myMap = gson.fromJson("{'k1':'apple','k2':'orange'}", type);
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

    public static HashMap<String, Object> getObjectParseHashMap(JSONObject obj) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        HashMap<String, Object> myMap = g.fromJson(obj + "", type);
        return myMap;
    }

    public static Object getObjectJson(JSONObject url) {
        return g.fromJson(url+"",Object.class);
    }
    //Obtener el parametro de la paginacion
    public static String getRankingFilter(CONSTANTS.RANKING_FILTER ranking_filter) {
        return   "{\"where\": {\"type\":\""+lista.get(ranking_filter)+"\"}}";
    }

    public static String parseImageToString(Bitmap imagen) {
        String encodedImageData =getEncoded64ImageStringFromBitmap(imagen);
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
}
