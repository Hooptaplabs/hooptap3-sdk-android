package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 26/5/15.
 */
public class HooptapLevel extends HooptapItem{
    private int number;

    public HooptapLevel(String jsonObj) {
        super(jsonObj);

        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("number"))
                this.number = json.getInt("number");
        }catch (Exception e){e.printStackTrace();}
    }

    public int getQuantity() {
        return number;
    }

    public void setQuantity(int number) {
        this.number = number;
    }
}
