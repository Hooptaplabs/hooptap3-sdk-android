package com.hooptap.sdkbrandclub.Engine;

import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackError;
import com.hooptap.sdkbrandclub.Models.ResponseError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 2/3/16.
 */
public class ErrorManager {

    public ErrorManager(WrapperTask wrapperTask, Exception exception, HooptapCallbackError hooptapCallbackError) {
        if (exception != null && exception.getCause() instanceof ApiClientException) {
            exception.printStackTrace();
            String jsonStringError = ((ApiClientException) exception.getCause()).getErrorMessage();
            if (isErrorAboutTokenExpired(jsonStringError)) {
                boolean tokenRenew = wrapperTask.renewToken();
                if (tokenRenew){
                    hooptapCallbackError.retry();
                }
            } else {
                hooptapCallbackError.onError(generateError(jsonStringError));
            }
        } else {
            Log.d("generateErro", "NO ApiClientException ");
            ResponseError responseError = new ResponseError();
            if (exception != null) {
                exception.printStackTrace();
                Log.d("generateErrorWithCallNO", exception.getMessage() + " /" + exception);
                responseError.setReason(exception.getLocalizedMessage());
            } else {
                responseError.setReason("Unknow Error");
            }
            hooptapCallbackError.onError(responseError);
        }
    }

    private boolean isErrorAboutTokenExpired(String errorMessage) {
        try {
            JSONObject jsonError = new JSONObject(errorMessage);
            ResponseError responseError = new ResponseError(jsonError);
            if (responseError.getStatus() == 419) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Funcion encargada de obtener el mensaje de error de la llamada
     */
    private ResponseError generateError(String errorMessage) {
        ResponseError responseError = new ResponseError();
        JSONObject error;
        try {
            error = new JSONObject(errorMessage);
            responseError = new ResponseError(error);
            return responseError;
        } catch (JSONException e) {
            e.printStackTrace();
            responseError.setReason("Se ha producido un error en la llamada");
            return responseError;
        }

    }


}
