package com.hooptap.sdkbrandclub.Interfaces;

/**
 * Created by root on 12/01/16.
 */
import com.hooptap.sdkbrandclub.Models.HooptapGameStatus;

/**
 * Interfaz necesaria para interactuar con los juegos HTML5
 */
public interface HooptapJSInterface {
    /** Recibe la información cuando finaliza el juego
     * @param gameStatus
     */

    void gameDidFinish(HooptapGameStatus gameStatus);
    /** Informa de que el juego va comenzar
     */
    void gameDidStart();

}



