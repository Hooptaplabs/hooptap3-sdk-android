package com.hooptap.sdkgameroom.Interfaces;

import com.hooptap.sdkgameroom.Models.ResponseError;

/**
 * Interfaz general que utilizaremos para las peticiones que realizamos a la API
 */
public interface HooptapCallback<T> {
    /**
     * Metodo que se llama cuando la peticion ha sido satisfactoria
     * @param var Puede recibir cualquier tipo de objeto
     */
    void onSuccess(T var);

    /**
     * Metodo que se llama cuando la peticion ha sido denegada
     * @param var Error que devuelve el servidor
     */
    void onError(ResponseError var);
}