package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;


import com.hooptap.brandclub.HooptapAPIvClient;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase principal que crea e inicializa el objeto Hooptap, el cual será el encargado de realizar las peticiones a nuestras APIs.
 * <p/>
 * El objeto Hooptap necesita tener definido un api_key y un token para poder operar correctamente
 *
 * @author Hooptap Team
 */

public class Hooptap {
    private static HooptapAPIvClient sClientService;
    private static SharedPreferences settings;
    public static Context context;
    private static SharedPreferences.Editor editor;

    /**
     * @return El objeto Hooptap para poder utilizarlo posteriormente
     */
    public static HooptapAPIvClient getClient() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return sClientService;
    }

    /**
     * Metodo que asigna un token al objeto Hooptap para permitir el acceso a la API
     *
     * @param token necesario para poder realizar las peticiones e identificar al usuario
     */
    public static void setToken(String token) {
        editor.putString("ht_token", token);
        editor.apply();
    }

    /**
     * Metodo que asigna un token al objeto Hooptap para permitir el acceso a la API
     *
     * @param token necesario para poder realizar las peticiones e identificar al usuario
     */
    public static void setToken(Object token) {
        JSONObject jsonObject = Utils.getObjectParse(token);
        try {
            JSONObject info=jsonObject.getJSONObject("response");
            if (!info.isNull("access_token")) {
                String infoToken=info.getString("access_token");
                editor.putString("ht_token", "Bearer " + infoToken);
                editor.apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que asigna el Api Key al objeto Hooptap para permitir el acceso a la API
     *
     * @param apiKey necesario para poder realizar las peticiones e identificar al cliente
     */
    public static void setApiKey(String apiKey) {
        editor.putString("ht_api_key", apiKey);
        editor.apply();
    }

    /**
     * @return El token almacenado para poder utilizarlo posteriormente
     */
    public static String getToken() {
        return settings.getString("ht_token", "");
    }

    //46576686f6f707461702e627

    /**
     * @return El Api Key  para poder utilizarlo posteriormente
     */
    public static String getApiKey() {
        return "46576686f6f707461702e627";
    }

    public static int getTypeClass() {
        return settings.getInt("type_class", 0);
    }

    public static void setTypeClass(int apiKey) {
        editor.putInt("type_class", apiKey);
        editor.apply();
    }

    /**
     * Constructor generico en el cual podremos configurar ciertos parametros
     */
    public static class Builder {

        public static Boolean htEnableDebug;
        private ApiClientFactory apiClientFactory;


        public Builder(Context cont) {
            context = cont;
            settings = context.getSharedPreferences("preferences", 0);
            editor = settings.edit();
        }

        /**
         * Metodo para activar o desacticar el modo debug de nuestro objeto Hooptap
         *
         * @param enable
         */
        public Hooptap.Builder enableDebug(Boolean enable) {
            htEnableDebug = enable;
            return this;
        }

        /**
         * Metodo para añadir el ApiKey de forma programada
         *
         * @param apiKey
         */
        public Hooptap.Builder setApiKey(String apiKey) {
            editor.putString("ht_api_key", apiKey);
            editor.apply();
            return this;
        }

        public Hooptap.Builder setTypeClass(int typeClass) {
            editor.putInt("type_class", typeClass);
            editor.apply();
            return this;
        }

        /**
         * Constructor que crea el objeto con los parametros asignados por los metodos anteriores
         *
         * @return El objeto Hooptap
         */
        public Hooptap build() {
            if (sClientService == null) {
                apiClientFactory = new ApiClientFactory();
                //apiClientFactory.apiKey(settings.getString("ht_api_key", ""));
                sClientService = apiClientFactory.build(HooptapAPIvClient.class);
            }

            return new Hooptap();
        }
    }

}