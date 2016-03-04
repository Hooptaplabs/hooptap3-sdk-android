package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Interfaces.ErrorManagerInterface;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskConfiguratorInterface;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public class TaskConfiguration implements TaskConfiguratorInterface {
    private Method method;
    private LinkedHashMap hasmap_data;
    private ErrorManagerInterface errorManager;

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setHasmap_data(LinkedHashMap hasmap_data) {
        this.hasmap_data = hasmap_data;
    }


    public void setErrorManager(ErrorManagerInterface errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public LinkedHashMap getHasmap_data() {
        return hasmap_data;
    }

    @Override
    public ErrorManagerInterface getErrorManager() {
        return errorManager;
    }

}
