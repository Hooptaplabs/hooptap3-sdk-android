package com.hooptap.sdkbrandclub.Engine;


import android.os.AsyncTask;
import android.os.Build;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.brandclub.HooptapApivClient;
import com.hooptap.brandclub.model.InputRenewTokenModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.AsyncResponse;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Log;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by Team Hooptap on 23/12/15.
 */
public class Command<T> extends AsyncTask {

    private HooptapCallbackRetry cbRetry;
    private LinkedHashMap hashmap = new LinkedHashMap();
    private HooptapApivClient client;
    private Method action;
    private Method[] declaredMethods = HooptapApivClient.class.getDeclaredMethods();
    int attempts = 0;
    private HooptapCallback<T> cb;

    public Command(String methodName, LinkedHashMap hashmap, HooptapCallback cb, HooptapCallbackRetry cbRetry) {
        this.hashmap = hashmap;
        this.cb = cb;
        this.cbRetry = cbRetry;
        client = Hooptap.getClient();
        //Clase donde vamos a buscar nuestros metodos
        Class cls = HooptapApivClient.class;
        //Para reflection es necesarior el tipo de los parametros del metodo
        Class[] argTypes = new Class[hashmap.size()];
        //Funcion que se encarga de encontrar los tipos de los parametros
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                argTypes = declaredMethod.getParameterTypes();
                break;
            }
        }
        try {
            //Metodo que estabamos buscando
            action = cls.getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(new Exception("El metodo que se esta llamando no existe en la interfaz que genera Amazon"));
        }

    }

    public void executeMethod(AsyncResponse asyncRespone) {
        WrapperTask wrapper = new WrapperTask(asyncRespone);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            wrapper.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            wrapper.execute();
        }
    }

    public void executeMethod() {
        WrapperTask wrapper = new WrapperTask(null);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            wrapper.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            wrapper.execute();
        }
    }

    //Tranforma un hasmap en un array(que es el formato que necesita el invoke del reflection)
    private Object[] createArgumentsFromHasmap() {
        return hashmap.values().toArray();
    }

    private class WrapperTask extends AsyncTask<Void, Void, Object> {
        private AsyncResponse asyncRespone = null;
        private Object object = null;

        public WrapperTask(AsyncResponse asyncRespone) {
            this.asyncRespone = asyncRespone;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                Object[] args = createArgumentsFromHasmap();
                object = action.invoke(client, args);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("doInBackground-ERROR", e.getLocalizedMessage()+" /");
                if (e.getCause() instanceof ApiClientException) {
                    Log.e("HooptapDebugError", ((ApiClientException) e.getCause()).getErrorMessage());
                    ResponseError error = getError(((ApiClientException) e.getCause()).getErrorMessage());
                    if (error != null && cb != null) {
                        cb.onError(getError(((ApiClientException) e.getCause()).getErrorMessage()));
                    } else {
                        cbRetry.retry();
                    }
                } else {
                    ResponseError responseError = new ResponseError();
                    responseError.setReason(e.getCause().getLocalizedMessage());
                    cb.onError(responseError);
                    //throw new RuntimeException(new Exception("Al ejecutar el metodo me da un error desconocido que no es instancia de ApiClientException: " + e.getClass()));
                }
            }
            Log.e("doInBackground", object+" /");
            return object;
        }

        @Override
        protected void onPostExecute(Object result) {
            //Devolvemos el resultado al usuario
            if (result != null && cb != null)
                cb.onSuccess((T) Utils.getObjectParse(result));
            if (asyncRespone != null)
                asyncRespone.processFinish(result);
        }
    }

    /**
     * Funcion encargada de renovar el token, realizara tres intentos, en caso de que no se
     * pueda realizar se enviara un error.
     */
    private void updateToken() {
        Log.e("prueba", "renovar token");
        attempts++;
        if (attempts <= 3) {
            String oldToken = Hooptap.getToken().substring(("Bearer ").length());
            renewToken(String.valueOf(oldToken));
        } else {
            attempts = 0;
            ResponseError responseError = new ResponseError();
            responseError.setReason("No se ha podido renovar token");
            if (cb != null)
                cb.onError(responseError);
        }

    }

    private Object renewToken(String oldToken) {
        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        renewTokenModel.setToken(oldToken);
        Object nuevoToken = client.userUserIdTokenPut(Hooptap.getApiKey(), (String) hashmap.get("user_id"), oldToken, renewTokenModel);
        Utils.setToken(nuevoToken);
        return nuevoToken;
    }

    /**
     * Funcion encargada de obtener el mensaje de error de la llamada
     */
    private ResponseError getError(String errorMessage) {
        Log.e("HooptapDebug", "Error: " + errorMessage);
        ResponseError responseError = new ResponseError();
        JSONObject error;
        try {
            error = new JSONObject(errorMessage);
            responseError = new ResponseError(error);
            //419 error de caducidad del token
            if (responseError.getStatus() == 419) {
                updateToken();
                return null;
            }
            return responseError;
        } catch (JSONException e) {
            e.printStackTrace();
            responseError.setReason("Se ha producido un error en vuestra llamada");
            return responseError;
        }

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}