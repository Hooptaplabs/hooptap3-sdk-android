package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.hooptap.a.RequestInterceptor;
import com.hooptap.a.RestAdapter;
import com.hooptap.a.client.OkClient;
import com.hooptap.a.converter.GsonConverter;
import com.hooptap.b.OkHttpClient;
import com.hooptap.d.Gson;
import com.hooptap.d.GsonBuilder;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapFolder;
import com.hooptap.sdkbrandclub.Models.HooptapFriend;
import com.hooptap.sdkbrandclub.Models.HooptapGame;
import com.hooptap.sdkbrandclub.Models.HooptapGameStatus;
import com.hooptap.sdkbrandclub.Models.HooptapGood;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapLink;
import com.hooptap.sdkbrandclub.Models.HooptapPoint;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Models.HooptapQuestStep;
import com.hooptap.sdkbrandclub.Models.HooptapQuestUserStatus;
import com.hooptap.sdkbrandclub.Models.HooptapReward;
import com.hooptap.sdkbrandclub.Models.HooptapText;
import com.hooptap.sdkbrandclub.Models.HooptapWeb;

import org.json.JSONObject;

import java.util.ArrayList;

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
                /*Gson gson = new GsonBuilder()
                        .registerTypeAdapter(HooptapItem.class, new ConverterJSON<HooptapItem>())
                        .registerTypeAdapter(HooptapItem.class, new ConverterJSON<ArrayList<HooptapItem>>())
                        .registerTypeAdapter(HooptapBadge.class, new ConverterJSON<HooptapBadge>())
                        .registerTypeAdapter(HooptapFolder.class, new ConverterJSON<HooptapFolder>())
                        .registerTypeAdapter(HooptapFriend.class, new ConverterJSON<HooptapFriend>())
                        .registerTypeAdapter(HooptapGame.class, new ConverterJSON<HooptapGame>())
                        .registerTypeAdapter(HooptapGameStatus.class, new ConverterJSON<HooptapGameStatus>())
                        .registerTypeAdapter(HooptapGood.class, new ConverterJSON<HooptapGood>())
                        .registerTypeAdapter(HooptapLevel.class, new ConverterJSON<HooptapLevel>())
                        .registerTypeAdapter(HooptapLink.class, new ConverterJSON<HooptapLink>())
                        .registerTypeAdapter(HooptapPoint.class, new ConverterJSON<HooptapPoint>())
                        .registerTypeAdapter(HooptapQuest.class, new ConverterJSON<HooptapQuest>())
                        .registerTypeAdapter(HooptapQuestStep.class, new ConverterJSON<HooptapQuestStep>())
                        .registerTypeAdapter(HooptapQuestUserStatus.class, new ConverterJSON<HooptapQuestUserStatus>())
                        .registerTypeAdapter(HooptapReward.class, new ConverterJSON<HooptapReward>())
                        .registerTypeAdapter(HooptapText.class, new ConverterJSON<HooptapText>())
                        .registerTypeAdapter(HooptapWeb.class, new ConverterJSON<HooptapWeb>())

                        .create();*/
                restAdapter = new RestAdapter.Builder()
                        .setRequestInterceptor(new RequestInterceptor() {
                            @Override
                            public void intercept(RequestFacade request) {
                                //request.addHeader("access_token", settings.getString("ht_token", ""));
                                request.addHeader("disp_platform", "android");
                                request.addHeader("authorization", "Bearer " + settings.getString("ht_token", ""));
                                request.addHeader("api_key", settings.getString("ht_api_key", ""));
                            }
                        })
                        //.setConverter(new GsonConverter(gson))
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