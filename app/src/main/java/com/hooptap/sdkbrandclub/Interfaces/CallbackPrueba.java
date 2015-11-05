package com.hooptap.sdkbrandclub.Interfaces;

import android.util.Log;

import com.hooptap.a.*;
import com.hooptap.a.mime.TypedByteArray;
import com.hooptap.b.Response;
import com.hooptap.sdkbrandclub.Utilities.HooptapApiException;
import com.hooptap.sdkbrandclub.Utilities.HooptapException;
import com.hooptap.sdkbrandclub.Utilities.Result;

/**
 * Created by root on 3/11/15.
 */
public abstract class CallbackPrueba<T> implements com.hooptap.a.Callback<T> {
    @Override
    public void success(T t, com.hooptap.a.client.Response response) {
        String s = new String(((TypedByteArray) response.getBody()).getBytes());
        Log.e("prueba3",s.toString());
        Log.e("prueba4",t.toString());
        success(new Result<>(t, response));
    }



    @Override
    public final void failure(RetrofitError error) {
        failure(HooptapApiException.convert(error));
    }

    /**
     * Called when call completes successfully.
     *
     * @param result the parsed result.
     */
    public abstract void success(Result<T> result);

    /**
     * Unsuccessful call due to network failure, non-2XX status code, or unexpected
     * exception.
     */
    public abstract void failure(HooptapException exception);
}
