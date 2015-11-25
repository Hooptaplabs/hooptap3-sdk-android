package com.hooptap.sdkbrandclub.AWS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.hooptap.SDK.MobileAPIClient;
import com.hooptap.a.RequestInterceptor;
import com.hooptap.a.RestAdapter;
import com.hooptap.a.client.OkClient;
import com.hooptap.b.OkHttpClient;
import com.hooptap.sdkbrandclub.Api.ApiInterface;

/**
 * Clase principal que crea e inicializa el objeto Hooptap, el cual será el encargado de realizar las peticiones a nuestras APIs.
 *
 * El objeto Hooptap necesita tener definido un api_key y un token para poder operar correctamente
 *
 * @author Hooptap Team
 */

public class HooptapAWS {
    private static MobileAPIClient sClientService;
    private static RestAdapter restAdapter;
    private static OkHttpClient client;
    private static SharedPreferences settings;
    private static RestAdapter.LogLevel debugVariable;
    private static MobileAPIClient hoptapClient;
    private static Context context;
    private static SharedPreferences.Editor editor;

    private HooptapAWS(MobileAPIClient sClientService) {
        this.hoptapClient = sClientService;
    }

    /**
     *
     * @return El objeto Hooptap para poder utilizarlo posteriormente
     */
    public static MobileAPIClient getClient(){
        return hoptapClient;
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
        private ApiClientFactory apiClientFactory;

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
            if (sClientService == null) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                apiClientFactory = new ApiClientFactory();
                apiClientFactory.apiKey(settings.getString("ht_api_key", ""));

                sClientService = apiClientFactory.build(MobileAPIClient.class);
            }

            return new HooptapAWS(sClientService);
        }
    }

}