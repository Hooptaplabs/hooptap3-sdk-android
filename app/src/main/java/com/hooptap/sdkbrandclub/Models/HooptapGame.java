package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

/**
 * Clase custom para encapsular los datos de un juego y facilitar su uso
 *
 * @author Hooptap Team
 */
public class HooptapGame extends HooptapItem {

    private String end_msgOK;
    private String end_msgNO;
    private String url_game;
    private String orientation;

    public HooptapGame(String jsonObj) {
        super(jsonObj);
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("end_msgOK"))
                this.end_msgOK = json.getString("end_msgOK");
            if (!json.isNull("end_msgNO"))
                this.end_msgNO = json.getString("end_msgNO");
            if (!json.isNull("url_game"))
                this.url_game = json.getString("url_game");
            if (!json.isNull("orientation"))
                this.orientation = json.getString("orientation");
            else
                this.orientation = "portrait";

        }catch (Exception e){e.printStackTrace();}
    }

    public String getEnd_msgOK() {
        return end_msgOK;
    }

    public void setEnd_msgOK(String end_msgOK) {
        this.end_msgOK = end_msgOK;
    }

    public String getEnd_msgNO() {
        return end_msgNO;
    }

    public void setEnd_msgNO(String end_msgNO) {
        this.end_msgNO = end_msgNO;
    }

    public String getUrl_game() {
        return url_game;
    }

    public void setUrl_game(String url_game) {
        this.url_game = url_game;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}
