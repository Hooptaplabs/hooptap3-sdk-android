package com.hooptap.sdkbrandclub.Interfaces;

/**
 * Created by carloscarrasco on 3/3/16.
 */
public interface ErrorManagerInterface {
    void setException(Exception e);

    void setCallbackRetry(TaskRetryCallback retryCallback);

    void setCallbackResponse(HooptapCallback callbackResponse);
}
