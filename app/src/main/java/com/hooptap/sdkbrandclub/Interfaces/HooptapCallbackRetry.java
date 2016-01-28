package com.hooptap.sdkbrandclub.Interfaces;

/**
 * Interfaz general que utilizaremos para las peticiones que realizamos a la API
 */
public interface HooptapCallbackRetry<T> {

    void retry();
}