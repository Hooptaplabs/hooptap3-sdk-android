package com.hooptap.sdkgameroom.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

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

public class Hooptap {
    private static ApiInterface sClientService;
    private static RestAdapter restAdapter;
    private static OkHttpClient client;
    private static SharedPreferences settings;
    private static RestAdapter.LogLevel debugVariable;
    private static ApiInterface hoptapClient;
    private static Context context;
    private static SharedPreferences.Editor editor;

    private Hooptap(ApiInterface sClientService) {
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

    /**
     * Constructor generico en el cual podremos configurar ciertos parametros
     */
    public static class Builder {

        private Boolean htEnableDebug;

        public Builder(Context cont) {
            context = cont;
            settings = context.getSharedPreferences("preferences", 0);
            editor = settings.edit();
        }

        /**
         * Metodo para activar o desacticar el modo debug de nuestro objeto Hooptap
         * @param enable
         */
        public Hooptap.Builder enableDebug(Boolean enable){
            htEnableDebug = enable;
            return this;
        }

        /**
         * Metodo para añadir el ApiKey de forma programada
         * @param apiKey
         */
        public Hooptap.Builder setApiKey(String apiKey) {
            editor.putString("ht_api_key", apiKey);
            editor.apply();
            return this;
        }

        /**
         * Constructor que crea el objeto con los parametros asignados por los metodos anteriores
         * @return El objeto Hooptap
         */
        public Hooptap build() {
            if (sClientService == null) {

                client = new OkHttpClient();
                client.interceptors().add(new HooptapInterceptor(context));
                client.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                try {
                    ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    Bundle bundle = ai.metaData;
                    editor.putString("ht_api_key", bundle.getString("com.hooptap.Apikey"));
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }


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
                                request.addHeader("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI1NTI2M2E5NTYwYTE0NjU5MDU1NTNkZDAiLCJpYXQiOjE0Mjg1Njg3MjV9.1TA54l7zksq1LjuOo0BQV7quoaLDXR9Z3qfgGgKrZzw");
                                if (settings.getString("ht_api_key", "").equals("")) {
                                    try {
                                        throw new Exception("ApiKey cant be empty");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else
                                    request.addHeader("api_key", settings.getString("ht_api_key", ""));
                            }
                        })
                        .setLogLevel(debugVariable)
                        .setEndpoint("http:/")
                        .setClient(new OkClient(client))
                        .build();

                sClientService = restAdapter.create(ApiInterface.class);
            }

            return new Hooptap(sClientService);
        }
    }

}