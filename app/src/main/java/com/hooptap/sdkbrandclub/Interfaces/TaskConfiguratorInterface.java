package com.hooptap.sdkbrandclub.Interfaces;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public interface TaskConfiguratorInterface {
    Method getMethod();

    LinkedHashMap getHasmap_data();

    ErrorManagerInterface getErrorManager();

}
