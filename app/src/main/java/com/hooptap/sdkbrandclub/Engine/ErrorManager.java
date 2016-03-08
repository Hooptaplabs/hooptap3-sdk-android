package com.hooptap.sdkbrandclub.Engine;

import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.sdkbrandclub.Interfaces.ErrorManagerInterface;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Models.ResponseError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 2/3/16.
 */
public class ErrorManager implements ErrorManagerInterface {

    private TaskCallbackWithRetry callbackResponse;

    @Override
    public void setCallbackResponse(TaskCallbackWithRetry callbackResponse) {
        this.callbackResponse = callbackResponse;
    }

    public void setException(Exception exception) {
        if (exception != null && exception.getCause() instanceof ApiClientException) {
            String jsonStringError = ((ApiClientException) exception.getCause()).getErrorMessage();
            Log.e("ERORMANAGEr", jsonStringError + " (/");
            if (isErrorAboutTokenExpired(jsonStringError)) {
                new RenewToken(new HooptapCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean var) {
                        callbackResponse.retry();
                    }

                    @Override
                    public void onError(ResponseError var) {
                        callbackResponse.onError(var);
                    }
                });
            } else {
                callbackResponse.onError(generateError(jsonStringError));
            }
        } else {
            Log.d("generateErro", "NO ApiClientException ");
            ResponseError responseError = new ResponseError();
            if (exception != null) {
                Log.d("generateErrorWithCallNO", exception.getMessage() + " /" + exception);
                responseError.setData(exception.getMessage());
            } else {
                responseError.setData("");
            }
            callbackResponse.onError(responseError);
        }
    }

    private boolean isErrorAboutTokenExpired(String errorMessage) {
        ResponseError responseError = new ResponseError();
        responseError.setData(errorMessage);
        if (responseError.getStatus() == 419) {
            return true;
        }
        return false;
    }

    private ResponseError generateError(String errorMessage) {
        ResponseError responseError = new ResponseError();
        responseError.setData(errorMessage);
        return responseError;
    }

}
