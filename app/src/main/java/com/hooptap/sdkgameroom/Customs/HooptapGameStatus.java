package com.hooptap.sdkgameroom.Customs;

import java.io.Serializable;

/**
 * Clase custom para recoger el estado del juego una vez terminado
 *
 * @author Hooptap Team
 */
public class HooptapGameStatus implements Serializable{
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
