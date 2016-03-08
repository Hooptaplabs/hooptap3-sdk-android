package com.hooptap.sdkbrandclub.Engine;


import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by Team Hooptap on 23/12/15.
 */
public class TaskCreator {

    private LinkedHashMap hashmap_data = new LinkedHashMap();
    private Method method;
    private Method[] declaredMethods = HooptapVClient.class.getDeclaredMethods();

    public TaskCreator(String methodName, LinkedHashMap hashmap_data) {
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
            throw new RuntimeException(new Exception("This method dosn't exist"));
        }
    }

    public TaskWrapperInterface getWrapperTask() {
        TaskConfiguration taskConfigurator = new TaskConfiguration();
        taskConfigurator.setMethod(method);
        taskConfigurator.setHasmap_data(hashmap_data);
        taskConfigurator.setErrorManager(new ErrorManager());
        return new TaskWrapper(taskConfigurator).getTask();
    }

}