package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 26/5/15.
 */
public class HooptapPoint extends HooptapItem{
    private int quantity;

    public HooptapPoint(String jsonObj) {
        super(jsonObj);

        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("quantity"))
                this.quantity = json.getInt("quantity");
        }catch (Exception e){e.printStackTrace();}
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
