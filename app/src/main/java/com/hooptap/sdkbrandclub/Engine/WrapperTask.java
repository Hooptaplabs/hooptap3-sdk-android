package com.hooptap.sdkbrandclub.Engine;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.brandclub.model.InputRenewTokenModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackError;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by carloscarrasco on 2/3/16.
 */
class WrapperTask extends AsyncTask<Void, Void, Object> {
    private final HooptapCallback asyncRespone;
    private final Method method;
    private final LinkedHashMap hasmap_data;
    private boolean errorHappends = false;
    private Exception exception;
    private HooptapVClient client;

    public WrapperTask(HooptapCallback asyncRespone, Method method, LinkedHashMap hasmap_data) {
        this.asyncRespone = asyncRespone;
        this.method = method;
        this.hasmap_data = hasmap_data;
        client = Hooptap.getClient();
    }

    @Override
    protected Object doInBackground(Void... params) {
        Object object = null;
        try {
            object = method.invoke(client, createArgumentsFromHasmap(hasmap_data));
        } catch (Exception e) {
            e.printStackTrace();
            errorHappends = true;
            exception = e;
        }
        Log.e("doInBackground", object + " /");
        return object;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (errorHappends) {
            new ErrorManager(this, exception, new HooptapCallbackError() {
                @Override
                public void onError(ResponseError error) {
                    asyncRespone.onError(error);
                }
                @Override
                public void retry() {
                    retryTask();
                }
            });
        } else {
            if (result != null) {
                asyncRespone.onSuccess(result);
            }
        }
    }

    //Tranforma un hasmap en un array(que es el formato que necesita el invoke del reflection)
    private Object[] createArgumentsFromHasmap(LinkedHashMap hasmap_data) {
        return hasmap_data.values().toArray();
    }

    private void retryTask() {
        new WrapperTask(asyncRespone, method, hasmap_data).execute();
    }

    public boolean renewToken() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HooptapVClient client = Hooptap.getClient();

        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        String oldToken = Hooptap.getToken().substring(("Bearer ").length());
        renewTokenModel.setToken(oldToken);
        try {
            Object newToken = client.userIdTokenPut(Hooptap.getApiKey(), oldToken, Hooptap.getUserId(), renewTokenModel);
            Utils.setToken(newToken);
            changeValueOfTokenOnHasmapData(Hooptap.getToken());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("RENEWTOKEN-ERROR", e.getLocalizedMessage()+" /");
            ResponseError responseError = new ResponseError();
            responseError.setReason(e.getLocalizedMessage());
            asyncRespone.onError(responseError);
            return false;
        }
    }

    private void changeValueOfTokenOnHasmapData(String newToken){
        hasmap_data.put("token", newToken);
    }

}
