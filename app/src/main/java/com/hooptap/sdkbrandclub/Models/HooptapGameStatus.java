package com.hooptap.sdkbrandclub.Models;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Clase custom para recoger el estado del juego una vez terminado
 *
 * @author Hooptap Team
 */
public class HooptapGameStatus implements Serializable{
    private Boolean result;
    private String score;
    private JSONArray rewards;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public JSONArray getRewards() {
        return rewards;
    }

    public void setRewards(JSONArray rewards) {
        this.rewards = rewards;
    }
}
