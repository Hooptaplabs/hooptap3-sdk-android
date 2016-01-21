package com.hooptap.sdkbrandclub.Engine;


import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Api.ApiWrapper;
import com.hooptap.sdkbrandclub.Utilities.Log;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Team Hooptap on 23/12/15.
 */
public class Command<T> {

    private static Object receiver;               // the "encapsulated" object
    private Method action;                 // the "pre-registered" request
    private Object[] args;                   // the "pre-registered" arg list
    Method[] declaredMethods = ApiWrapper.class.getDeclaredMethods();
    String user_id;
    int attempts = 0;
    HooptapCallback<T> cb;
    boolean stateValues = false;
    //int responseClass = Hooptap.getTypeClass();

    /**
     *
     * */
    public Command(Object obj, String methodName, Object[] arguments, HooptapCallback cb) {
        if (Hooptap.getApiKey() != null) {
            this.cb = cb;
            receiver = obj;
            args = arguments;
            user_id = String.valueOf(arguments[0]);
            Class cls = obj.getClass();                 //Clase de las llamadas a api
            Class[] argTypes = new Class[args.length];  //Para reflection es necesarior el tipo de los parametros del metodo
            //Funcion que se encarga de encontrar los tipos de los parametros
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equals(methodName)) {
                    argTypes = declaredMethod.getParameterTypes();
                    break;
                }
            }
            Log.e("HooptapDebug", "Method: " + methodName);
            try {
                action = cls.getMethod(methodName, argTypes);
                //Comprobacion para cuando no tiene el user_id o es vacio
                if (Utils.isEmpty(arguments[0])) {
                    Log.e("HooptapDebug: Error", "La peticion \"" + methodName + "\" no se ha podido realizar porque los parametros obligatorios son null");
                    stateValues = true;
                }
            } catch (NoSuchMethodException e) {
                System.out.println(e);
            }
        } else {
            Log.e("Request", "La peticion no se ha podido realizar porque no tienes API_KEY");
            throw new RuntimeException(new Exception("La peticion no se ha podido realizar porque no tienes API_KEY"));
        }
    }

    public Object execute() {
        if (!stateValues) {
            try {
                Object objeto = action.invoke(receiver, args);
                Log.e("HooptapDebug", "Response: " + Utils.getObjectParse(objeto));
                cb.onSuccess((T) Utils.getObjectParse(objeto));
                return objeto;
            } catch (ApiClientException e) {
                Log.e("HooptapDebug", "Error: Se ha producido un error en la peticion1");
                cb.onError(getError(e.getErrorMessage()));
            } catch (IllegalAccessException e) {
                Log.e("HooptapDebug", "Error: Se ha producido un error en la peticion2");
                cb.onError(getError(((ApiClientException) e.getCause()).getErrorMessage()));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Log.e("HooptapDebug", "Error: Se ha producido un error en la peticion3");
                if (e.getCause() instanceof ApiClientException) {
                    cb.onError(getError(((ApiClientException) e.getCause()).getErrorMessage()));
                }
            } catch (ClassCastException e) {
                //Log.e("HooptapDebug", e.getCause().getMessage() + "---" + e.getMessage());
            }
        }

        return null;
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
            ApiWrapper apiWrapper = new ApiWrapper();
            apiWrapper.renewToken(user_id, oldToken);
            execute();

        } else {
            attempts = 0;
            ResponseError responseError = new ResponseError();
            responseError.setReason("No se ha podido renovar token");
            cb.onError(responseError);
        }

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
            if (responseError.getStatus() == 419) {
                updateToken();
            }
            return responseError;
        } catch (JSONException e) {
            e.printStackTrace();
            responseError.setReason("Se ha producido un error en vuestra llamada");
            return responseError;
        }

    }

    /* private static JSONObject getObjectParse(Object o) {
         String value = g.toJson(o);
         try {
             return new JSONObject(value);
         } catch (JSONException e) {
             e.printStackTrace();
             return null;
         }
     }*/


}