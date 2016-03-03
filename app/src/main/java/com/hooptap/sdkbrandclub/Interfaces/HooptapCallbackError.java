package com.hooptap.sdkbrandclub.Interfaces;

import com.hooptap.sdkbrandclub.Models.ResponseError;

/**
 * Interfaz general que utilizaremos para las peticiones que realizamos a la API
 */
public interface HooptapCallbackError {

    void onError(ResponseError var);

    void retry();
}