package com.hooptap.sdkbrandclub.Interfaces;

import com.hooptap.sdkbrandclub.Engine.RenewToken;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public interface ErrorManagerInterface {
    void setException(Exception e);

    void setCallbackResponse(TaskCallbackWithRetry callbackResponse);

    void setRenewTokenTask(RenewToken renewTokenTask);
}
