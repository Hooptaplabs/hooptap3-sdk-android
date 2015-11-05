package com.hooptap.sdkbrandclub.Interfaces;

import android.util.Log;

import com.hooptap.a.Callback;
import com.hooptap.a.RetrofitError;
import com.hooptap.sdkbrandclub.Utilities.HooptapException;

/**
 * Created by root on 3/11/15.
 */
abstract class HooptapCallbackRequest<T> extends CallbackPrueba<T> {
    private final CallbackPrueba cb;
    HooptapCallbackRequest(CallbackPrueba cb){
        this.cb=cb;
    }

    @Override
    public void failure(HooptapException exception) {
        if (cb != null) {
            cb.failure(exception);
        }
    }
}
