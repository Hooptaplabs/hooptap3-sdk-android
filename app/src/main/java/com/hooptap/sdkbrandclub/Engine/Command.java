package com.hooptap.sdkbrandclub.Engine;


import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.brandclub.HooptapApivClient;
import com.hooptap.brandclub.model.Empty;
import com.hooptap.brandclub.model.FileModel;
import com.hooptap.brandclub.model.InputActionDoneModel;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.brandclub.model.InputRenewTokenModel;
import com.hooptap.brandclub.model.UserModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Api.ApiWrapper;
import com.hooptap.sdkbrandclub.Utilities.Log;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by Team Hooptap on 23/12/15.
 */
public class Command<T> {

    private HooptapCallbackRetry cbRetry;
    private LinkedHashMap hashmap = new LinkedHashMap();
    //private HooptapApivClientPrueba receiver = new HooptapApivClientPrueba();
    private HooptapApivClient client = Hooptap.getClient();
    //private static Object receiver;               // the "encapsulated" object
    private Method action;                 // the "pre-registered" request
    //private Object[] args;                   // the "pre-registered" arg list
    Method[] declaredMethods = HooptapApivClient.class.getDeclaredMethods();
    //String user_id;
    int attempts = 0;
    HooptapCallback<T> cb;
    boolean stateValues = false;
    //int responseClass = Hooptap.getTypeClass();

    /**
     *
     * */
    /*public Command(Object obj, String methodName, Object[] arguments, HooptapCallback cb) {
        if (Hooptap.getApiKey() != null) {
            this.cb = cb;
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

    public Command(String methodName, Object[] arguments, HooptapCallback cb) {
        if (Hooptap.getApiKey() != null) {
            this.cb = cb;
            //args = arguments;
            Class cls = HooptapApivClient.class;                 //Clase de las llamadas a api
            Class[] argTypes = new Class[3];  //Para reflection es necesarior el tipo de los parametros del metodo
            //Funcion que se encarga de encontrar los tipos de los parametros
            Log.e("LENGH", declaredMethods.length + " /");
            for (Method declaredMethod : declaredMethods) {
                //Log.e("NAME-METHOD", declaredMethod.getName()+" / "+(methodName));
                if (declaredMethod.getName().equals(methodName)) {
                    argTypes = declaredMethod.getParameterTypes();
                    break;
                }
            }
            Log.e("HooptapDebug", "Method: " + methodName + " / " + argTypes.length);
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

    public Command(String methodName, LinkedHashMap hashmap, HooptapCallback cb) {
        if (Hooptap.getApiKey() != null) {
            this.hashmap = hashmap;
            this.cb = cb;
            Class cls = HooptapApivClient.class;                 //Clase de las llamadas a api
            Class[] argTypes = new Class[hashmap.size()];  //Para reflection es necesarior el tipo de los parametros del metodo
            //Funcion que se encarga de encontrar los tipos de los parametros
            Log.e("LENGH", declaredMethods.length + " /");
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equals(methodName)) {
                    argTypes = declaredMethod.getParameterTypes();
                    break;
                }
            }
            Log.e("HooptapDebug", "Method: " + methodName + " / " + argTypes.length);
            try {
                action = cls.getMethod(methodName, argTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Request", "La peticion no se ha podido realizar porque no tienes API_KEY");
            throw new RuntimeException(new Exception("La peticion no se ha podido realizar porque no tienes API_KEY"));
        }
    }*/

    public Command(String methodName, LinkedHashMap hashmap, HooptapCallback cb, HooptapCallbackRetry cbRetry) {
        this.hashmap = hashmap;
        this.cb = cb;
        this.cbRetry = cbRetry;
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

    //Ejecutamos el metodo con los argumentos que le hemos pasado
    public Object executeMethod() {
        //Cuando una peticion de las que realiza amazon da error, la emite como una excepcion, de ahi
        //que tengamos el try, para poder recoger el error emitido y poder tratarlo
        try {
            Object[] args = createArgumentsFromHasmap();
            android.util.Log.i("APIKEY", args[0] + "");
            Object objeto = action.invoke(client, args);
            Log.e("HooptapDebug", "Response: " + (T) Utils.getObjectParse(objeto));
            cb.onSuccess((T) Utils.getObjectParse(objeto));
            return objeto;
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause() instanceof ApiClientException) {
                Log.e("HooptapDebugError", ((ApiClientException) e.getCause()).getErrorMessage());

                ResponseError error = getError(((ApiClientException) e.getCause()).getErrorMessage());
                if (error != null) {
                    cb.onError(getError(((ApiClientException) e.getCause()).getErrorMessage()));
                } else {
                    cbRetry.retry();
                }
            } else {
                throw new RuntimeException(new Exception("Al ejecutar el metodo me da un error desconocido que no es instancia de ApiClientException: "+ e.getClass()));
            }
        }
        return null;
    }

    //Tranforma un hasmap en un array(que es el formato que necesita el invoke del reflection)
    private Object[] createArgumentsFromHasmap() {
        return hashmap.values().toArray();
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
            cb.onError(responseError);
        }

    }

    private Object renewToken(String oldToken) {
        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        renewTokenModel.setToken(oldToken);
        Object nuevoToken = client.userUserIdTokenPut(Hooptap.getApiKey(), (String) hashmap.get("user_id"), oldToken, renewTokenModel);
        Hooptap.setToken(nuevoToken);
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

}