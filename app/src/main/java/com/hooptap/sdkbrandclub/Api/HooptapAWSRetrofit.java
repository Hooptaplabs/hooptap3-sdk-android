package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.hooptap.a.RequestInterceptor;
import com.hooptap.a.RestAdapter;
import com.hooptap.a.client.OkClient;
import com.hooptap.b.OkHttpClient;

/**
 * Clase principal que crea e inicializa el objeto Hooptap, el cual será el encargado de realizar las peticiones a nuestras APIs.
 *
 * El objeto Hooptap necesita tener definido un api_key y un token para poder operar correctamente
 *
 * @author Hooptap Team
 */

public class HooptapAWSRetrofit {
    private static ApiInterface sClientService;
    private static RestAdapter restAdapter;
    private static OkHttpClient client;
    private static SharedPreferences settings;
    private static RestAdapter.LogLevel debugVariable;
    private static ApiInterface hoptapClient;
    private static Context context;
    private static SharedPreferences.Editor editor;

    private HooptapAWSRetrofit(ApiInterface sClientService) {
        this.hoptapClient = sClientService;
    }

    /**
     *
     * @return El objeto Hooptap para poder utilizarlo posteriormente
     */
    public static ApiInterface getClient(){
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

        public Builder(Context cont) {
            context = cont;
            settings = context.getSharedPreferences("preferences", 0);
            editor = settings.edit();
        }

        /**
         * Metodo para activar o desacticar el modo debug de nuestro objeto Hooptap
         * @param enable
         */
        public HooptapAWSRetrofit.Builder enableDebug(Boolean enable){
            htEnableDebug = enable;
            return this;
        }

        /**
         * Metodo para añadir el ApiKey de forma programada
         * @param apiKey
         */
        public HooptapAWSRetrofit.Builder setApiKey(String apiKey) {
            editor.putString("ht_api_key", apiKey);
            editor.apply();
            return this;
        }

        /**
         * Constructor que crea el objeto con los parametros asignados por los metodos anteriores
         * @return El objeto Hooptap
         */
        public HooptapAWSRetrofit build() {
            if (sClientService == null) {

                client = new OkHttpClient();
                //client.interceptors().add(new HooptapInterceptor(context));
                client.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                /*try {
                    ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    Bundle bundle = ai.metaData;
                    editor.putString("ht_api_key", bundle.getString("com.hooptap.Apikey"));
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/


                if (htEnableDebug != null) {
                    if (htEnableDebug) {

                        debugVariable = RestAdapter.LogLevel.FULL;
                    } else {
                        debugVariable = RestAdapter.LogLevel.NONE;
                    }
                }else{
                    debugVariable = RestAdapter.LogLevel.NONE;
                }

                restAdapter = new RestAdapter.Builder()
                        .setRequestInterceptor(new RequestInterceptor() {
                            @Override
                            public void intercept(RequestFacade request) {
                                //request.addHeader("access_token", settings.getString("ht_token", ""));
                                request.addHeader("Content-Type", "application/json");
                                request.addHeader("Accept", "application/json");
                                request.addHeader("x-api-key","mwlSjmXo983CfCrqnZXu6ayUwZ8AzH1Z2Ce22mhC" );     }
                        })
                        //.setConverter(new GsonConverter(gson))
                        .setLogLevel(debugVariable)
                        .setEndpoint("https://25unt9h64h.execute-api.us-west-2.amazonaws.com/dev")
                        .setClient(new OkClient(client))
                        .build();

                sClientService = restAdapter.create(ApiInterface.class);
            }

            return new HooptapAWSRetrofit(sClientService);
        }
    }

}