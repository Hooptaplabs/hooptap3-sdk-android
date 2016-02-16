package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Utilities.TinyDB;
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
    private static HooptapVClient sClientService;
    public static Context context;
    private static Boolean htEnableDebug;
    private static TinyDB tinydb;

    /**
     * @return El objeto Hooptap para poder utilizarlo posteriormente
     */
    public static HooptapVClient getClient() {
        return sClientService;
    }

    public static TinyDB getTinyDB() {
        if (tinydb == null) {
            tinydb = new TinyDB(context);
        }
        return tinydb;
    }

    /**
     * @return El token almacenado para poder utilizarlo posteriormente
     */
    public static String getToken() {
        return getTinyDB().getString("ht_token");
    }

    /**
     * Metodo que asigna el Api Key al objeto Hooptap para permitir el acceso a la API
     *
     * @param apiKey necesario para poder realizar las peticiones e identificar al cliente
     */
    public static void setApiKey(String apiKey) {
        getTinyDB().putString("ht_api_key", apiKey);
    }

    /**
     * @return El Api Key  para poder utilizarlo posteriormente
     */
    public static String getApiKey() {
        return getTinyDB().getString("ht_api_key");
    }

    /**
     * @return Si el modo debug está habilitado
     */
    public static Boolean getEnableDebug() {
        return htEnableDebug;
    }

    /**
     * Constructor generico en el cual podremos configurar ciertos parametros
     */
    public static class Builder {


        private ApiClientFactory apiClientFactory;

        public Builder(Context cont) {
            context = cont;
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
            getTinyDB().putString("ht_api_key", apiKey);
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
                sClientService = apiClientFactory.build(HooptapVClient.class);
                //Inicializo el mapeador
                new MapperObjects();
            }

            return new Hooptap();
        }
    }

}