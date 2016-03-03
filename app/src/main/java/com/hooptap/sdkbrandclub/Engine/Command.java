package com.hooptap.sdkbrandclub.Engine;


import android.os.AsyncTask;
import android.os.Build;

import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by Team Hooptap on 23/12/15.
 */
public class Command<T> {

    private LinkedHashMap hashmap_data = new LinkedHashMap();
    private Method method;
    private Method[] declaredMethods = HooptapVClient.class.getDeclaredMethods();

    public Command(String methodName, LinkedHashMap hashmap_data) {
        this.hashmap_data = hashmap_data;

        Class[] parameters = getParametersOfReflectionMethod(methodName);
        method = getMethodByReflection(methodName, parameters);
    }

    //Funcion que se encarga de encontrar los tipos de los parametros
    private Class[] getParametersOfReflectionMethod(String methodName) {
        Class[] argTypes = new Class[hashmap_data.size()];
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
        WrapperTask wrapper = new WrapperTask(asyncRespone, method, hashmap_data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            wrapper.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            wrapper.execute();
        }
    }

}