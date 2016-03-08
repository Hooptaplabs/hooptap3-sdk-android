package com.hooptap.sdkbrandclub.Interfaces;

import com.hooptap.sdkbrandclub.Engine.TaskWrapper;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public interface ErrorManagerInterface {
    void setException(Exception e);

    void setCallbackResponse(TaskCallbackWithRetry callbackResponse);
}
