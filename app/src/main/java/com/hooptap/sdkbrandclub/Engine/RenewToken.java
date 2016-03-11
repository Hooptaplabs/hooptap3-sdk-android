package com.hooptap.sdkbrandclub.Engine;

import android.os.StrictMode;
import android.util.Log;

import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;
import com.hooptap.sdkbrandclub.model.InputRenewTokenModel;

/**
 * Created by carloscarrasco on 8/3/16.
 */
public class RenewToken {

    public void renewToken(TaskCallbackWithRetry callback) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HooptapVClient client = Hooptap.getClient();

        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        String oldToken = Hooptap.getToken().substring(("Bearer ").length());
        renewTokenModel.setToken(oldToken);
        try {
            Object newToken = client.userIdTokenPut(Hooptap.getApiKey(), oldToken, Hooptap.getUserId(), renewTokenModel);
            Utils.setToken(newToken);
            callback.retry();
        } catch (Exception e) {
            ResponseError responseError = new ResponseError();
            responseError.setData(e.getMessage());
            callback.onError(responseError);
        }
    }

}
