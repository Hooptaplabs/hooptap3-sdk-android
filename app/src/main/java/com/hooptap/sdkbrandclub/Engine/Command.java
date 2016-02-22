package com.hooptap.sdkbrandclub.Engine;


import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.brandclub.model.InputRenewTokenModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by Team Hooptap on 23/12/15.
 */
public class Command<T> {

    private HooptapCallbackRetry cbRetry;
    private LinkedHashMap hashmap = new LinkedHashMap();
    private HooptapVClient client;
    private Method action;
    private Method[] declaredMethods = HooptapVClient.class.getDeclaredMethods();
    private HooptapCallback asyncRespone;
    private String errorRenewToken;
    private int attempts;

    private Command() {
    }

    public Command(String methodName, LinkedHashMap hashmap, HooptapCallbackRetry cbRetry) {
        this.hashmap = hashmap;
        this.cbRetry = cbRetry;
        client = Hooptap.getClient();

        Class[] parameters = getParametersOfReflectionMethod(methodName);
        action = getMethodByReflection(methodName, parameters);
    }

    //Funcion que se encarga de encontrar los tipos de los parametros
    private Class[] getParametersOfReflectionMethod(String methodName) {
        Class[] argTypes = new Class[hashmap.size()];
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                argTypes = declaredMethod.getParameterTypes();
                return argTypes;
            }
        }
        return argTypes;
    }

    private Method getMethodByReflection(String methodName, Class[] argTypes) {
        try {
            Class cls = HooptapVClient.class;
            return cls.getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(new Exception("El metodo que se esta llamando no existe en la interfaz que genera Amazon"));
        }
    }

    public void executeMethod(HooptapCallback asyncRespone) {
        WrapperTask wrapper = new WrapperTask(asyncRespone);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            wrapper.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            wrapper.execute();
        }
    }

    //Tranforma un hasmap en un array(que es el formato que necesita el invoke del reflection)
    private Object[] createArgumentsFromHasmap() {
        return hashmap.values().toArray();
    }

    private class WrapperTask extends AsyncTask<Void, Void, Object> {
        private Object object = null;
        private boolean errorHappends = false;
        private Exception exception;

        public WrapperTask(HooptapCallback ownCallback) {
            asyncRespone = ownCallback;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                Object[] args = createArgumentsFromHasmap();
                object = action.invoke(client, args);
            } catch (Exception e) {
                errorHappends = true;
                exception = e;
            }
            Log.e("doInBackground", object + " /");
            return object;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (errorHappends) {
                generateErrorWithCallback(exception);
            } else {
                if (result != null) {
                    asyncRespone.onSuccess(result);
                }
            }
        }
    }

    private void generateErrorWithCallback(Exception exception) {
        if (exception != null && exception.getCause() instanceof ApiClientException) {
            Log.d("generateErro", "ApiClientException ");
            ResponseError error = getError(((ApiClientException) exception.getCause()).getErrorMessage());
            Log.e("ERROR",error+" /");
            if (error != null) {
                asyncRespone.onError(error);
                //asyncRespone.onError(getError(((ApiClientException) exception.getCause()).getErrorMessage()));
            } else {
                cbRetry.retry();
            }
        } else {
            Log.d("generateErro", "NO ApiClientException ");
            ResponseError responseError = new ResponseError();
            if (exception != null) {
                exception.printStackTrace();
                Log.d("EXCEP", exception.getMessage() + " /" + exception);
                responseError.setReason(exception.getLocalizedMessage());
            } else {
                responseError.setReason("Unknow Error");
            }
            asyncRespone.onError(responseError);
        }
    }

    /**
     * Funcion encargada de obtener el mensaje de error de la llamada
     */
    private ResponseError getError(String errorMessage) {
        ResponseError responseError = new ResponseError();
        JSONObject error;
        try {
            error = new JSONObject(errorMessage);
            responseError = new ResponseError(error);
            //419 error de caducidad del token
            if (responseError.getStatus() == 419) {
                String oldToken = Hooptap.getToken().substring(("Bearer ").length());
                return renewToken(oldToken);
            }
            return responseError;
        } catch (JSONException e) {
            e.printStackTrace();
            responseError.setReason("Se ha producido un error en vuestra llamada");
            return responseError;
        }

    }

    private ResponseError renewToken(String oldToken) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        renewTokenModel.setToken(oldToken);
        try {
            Object nuevoToken = client.userIdTokenPut(Hooptap.getApiKey(), oldToken, (String) hashmap.get("id"), renewTokenModel);
            Utils.setToken(nuevoToken);
            return null;
        } catch (Exception e) {
            ResponseError responseError = new ResponseError();
            Log.e("ERROR",e.getLocalizedMessage()+" / "+e.getMessage());
            responseError.setReason(e.getMessage());
            asyncRespone.onError(responseError);
            return responseError;
        }
    }

}