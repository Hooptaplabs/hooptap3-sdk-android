package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.hooptap.a.RequestInterceptor;
import com.hooptap.a.RestAdapter;
import com.hooptap.a.client.OkClient;
import com.hooptap.b.OkHttpClient;
import com.hooptap.sdkbrandclub.AWS.MobileAPIClient;

/**
 * Clase principal que crea e inicializa el objeto Hooptap, el cual será el encargado de realizar las peticiones a nuestras APIs.
 *
 * El objeto Hooptap necesita tener definido un api_key y un token para poder operar correctamente
 *
 * @author Hooptap Team
 */

public class HooptapAWS {
    private static ApiInterface sClientService;
    private static RestAdapter restAdapter;
    //private static OkHttpClient client;
    private static SharedPreferences settings;
    private static RestAdapter.LogLevel debugVariable;
    private static ApiInterface hoptapClient;
    private static Context context;
    private static SharedPreferences.Editor editor;
    static MobileAPIClient client;
    static ApiClientFactory apiClientFactory;

    private HooptapAWS(MobileAPIClient cliente) {
        this.client = cliente;
    }

    /**
     *
     * @return El objeto Hooptap para poder utilizarlo posteriormente
     */
    public static MobileAPIClient getClient(){
        return client;
    }

    /**
     * Metodo que asigna un token al objeto Hooptap para permitir el acceso a la API
     * @param token necesario para poder realizar las peticiones e identificar al usuario
     */
    public static void setToken(String token){
        editor.putString("ht_token", token);
        editor.apply();
    }

    public static void setApiKey(String apiKey) {
        editor.putString("ht_api_key", apiKey);
        editor.apply();
    }


    /**
     * Constructor generico en el cual podremos configurar ciertos parametros
     */
    public static class Builder {

        public static Boolean htEnableDebug;

        public Builder(Context cont) {
            context = cont;
            settings = context.getSharedPreferences("preferences", 0);
            editor = settings.edit();
        }

        /**
         * Metodo para activar o desacticar el modo debug de nuestro objeto Hooptap
         * @param enable
         */
        public HooptapAWS.Builder enableDebug(Boolean enable){
            htEnableDebug = enable;
            return this;
        }

        /**
         * Metodo para añadir el ApiKey de forma programada
         * @param apiKey
         */
        public HooptapAWS.Builder setApiKey(String apiKey) {
            editor.putString("ht_api_key", apiKey);
            editor.apply();
            return this;
        }

        /**
         * Constructor que crea el objeto con los parametros asignados por los metodos anteriores
         * @return El objeto Hooptap
         */
        public HooptapAWS build() {

            apiClientFactory=new ApiClientFactory();
            apiClientFactory.apiKey(settings.getString("ht_api_key", ""));
            return new HooptapAWS(apiClientFactory.build(MobileAPIClient.class));
        }
    }

}