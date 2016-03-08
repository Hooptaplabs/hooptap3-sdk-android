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

public class Hooptap {
    private static HooptapVClient sClientService;
    public static Context context;
    private static TinyDB tinydb;

    public static HooptapVClient getClient() {
        return sClientService;
    }

    public static TinyDB getTinyDB() {
        if (tinydb == null) {
            tinydb = new TinyDB(context);
        }
        return tinydb;
    }

    public static String getToken() {
        return getTinyDB().getString("ht_token");
    }

    public static String getUserId() {
        return getTinyDB().getString("ht_user_id");
    }

    public static void setApiKey(String apiKey) {
        getTinyDB().putString("ht_api_key", apiKey);
    }

    public static String getApiKey() {
        return getTinyDB().getString("ht_api_key");
    }

    public static class Builder {


        private ApiClientFactory apiClientFactory;

        public Builder(Context cont) {
            context = cont;
        }

        public Hooptap.Builder setApiKey(String apiKey) {
            getTinyDB().putString("ht_api_key", apiKey);
            return this;
        }

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