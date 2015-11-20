package com.hooptap.sdkbrandclub.Api;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.google.gson.Gson;
import com.hooptap.a.Callback;
import com.hooptap.a.RetrofitError;
import com.hooptap.a.client.Response;
import com.hooptap.a.mime.TypedByteArray;
import com.hooptap.a.mime.TypedFile;
import com.hooptap.sdkbrandclub.AWS.model.Folder;
import com.hooptap.sdkbrandclub.AWS.model.Item;
import com.hooptap.sdkbrandclub.Engine.ItemParse;
import com.hooptap.sdkbrandclub.Engine.ItemParseLibrary;
import com.hooptap.sdkbrandclub.Interfaces.CallbackPrueba;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.HooptapException;
import com.hooptap.sdkbrandclub.Utilities.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApiAWS {
    //Primer metodo Prueba
    public static void getItems(final String path, final String user_id, final HooptapCallback<List<Item>> callback) {
       /* HooptapAWS.getClient().itemsGet(new Callback<ArrayList<Item>>() {
            @Override
            public void success(ArrayList<Item> items, Response response) {
                callback.onSuccess(items);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("retrofit", retrofitError.getMessage());
            }
        });*/

        /*Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    callback.onSuccess(HooptapAWS.getClient().itemsGet());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();*/

    }

   /* public static void getFolder(final HooptapCallback<Folder> callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(HooptapAWS.getClient().foldersGet());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", e.getMessage());
                }
            }


        });
    }

    public static void getFolder4(final HooptapCallback<List<Item>> callback) {


        List<Folder> folder = HooptapAWS.getClient().foldersGetJSon();
        Log.e("folder", folder + "");

        Log.e("folder", folder.get(0).getCategory() + "-aaaaa-" + folder.get(0).getItems());
        //Log.e("folder",HooptapAWS.getClient().foldersGet()+"");

        callback.onSuccess(HooptapAWS.getClient().folders());


    }

    public static void getElementos() {
        HooptapAWSRetrofit.getClient().itemsGet(new Callback<List<Item>>() {
            @Override
            public void success(List<Item> items, Response response) {
                Log.e("retrofit", items.get(0) + "");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("retrofit", retrofitError.getMessage());
            }
        });

    }
*/
}

