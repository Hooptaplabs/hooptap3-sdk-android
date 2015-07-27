package com.hooptap.sdkbrandclub.Models;

import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 26/6/15.
 */
public class HooptapText extends HooptapItem {

    private String creation_date;
    private String text;

    public HooptapText(String jsonObj) {
        super(jsonObj);
        try{
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("text"))
                this.text = json.getString("text");
            if (!json.isNull("creation_date"))
                this.creation_date = json.getString("creation_date");
        }catch (Exception e){e.printStackTrace();}

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
