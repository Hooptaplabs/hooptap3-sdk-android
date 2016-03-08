package com.hooptap.sdkbrandclub.Interfaces;

import com.hooptap.sdkbrandclub.Models.ResponseError;

public interface HooptapCallback<T> {

    void onSuccess(T var);

    void onError(ResponseError var);
}