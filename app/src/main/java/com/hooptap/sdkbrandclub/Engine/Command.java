package com.hooptap.sdkbrandclub.Engine;



import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.d.Gson;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Api.ApiAWS;
import com.hooptap.sdkbrandclub.Utilities.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by root on 23/12/15.
 */
public class Command {
    static Gson g = new Gson();
    private static Object receiver;               // the "encapsulated" object
    private Method action;                 // the "pre-registered" request
    private Object[] args;                   // the "pre-registered" arg list
    Method[] declaredMethods = ApiAWS.class.getDeclaredMethods();
    HooptapCallback<JSONObject> cb;

    public Command(Object obj, String methodName, Object[] arguments, HooptapCallback<JSONObject> cb) {
        if (Hooptap.getApiKey() != null) {

            this.cb = cb;
            receiver = obj;
            args = arguments;
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
            } catch (NoSuchMethodException e) {
                System.out.println(e);
            }
        } else {
            Log.e("Request", "La peticion no se ha podido realizar porque no tienes API_KEY");

            throw new RuntimeException(new Exception("La peticion no se ha podido realizar porque no tienes API_KEY"));
        }
    }

    public Object execute() {

        try {
            Object objeto = action.invoke(receiver, args);
            Log.e("HooptapDebug", "Response: " + getObjectParse(objeto));
            cb.onSuccess(getObjectParse(objeto));
            return objeto;
        } catch (ApiClientException e) {
            Log.e("HooptapDebug", "Error: Se ha producido un error en la peticion");
            cb.onError(getError(e));
        } catch (IllegalAccessException e) {
            Log.e("HooptapDebug", "Error: Se ha producido un error en la peticion");
            cb.onError(getError((ApiClientException) e.getCause()));
        } catch (InvocationTargetException e) {
            Log.e("HooptapDebug", "Error: Se ha producido un error en la peticion");
            cb.onError(getError((ApiClientException) e.getCause()));
        }
        return null;
    }

    private static ResponseError getError(ApiClientException ae) {
        String con = "error:";
        ResponseError responseError = new ResponseError();
        JSONObject error;
        int a = ae.getErrorMessage().indexOf(con);
        try {
            error = new JSONObject(ae.getErrorMessage().substring(a + con.length()));
            responseError = new ResponseError(error);
            return responseError;
        } catch (JSONException e) {
            e.printStackTrace();
            responseError.setReason("Se ha producido un error en vuestra llamada");
            return responseError;
        }

    }

    private static JSONObject getObjectParse(Object o) {
        String value = g.toJson(o);
        try {
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}