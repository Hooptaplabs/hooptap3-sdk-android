package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by carloscarrasco on 26/5/15.
 */
public class HooptapReward extends HooptapItem{
    private String message;
    private int importance;
    private int quantity;

    public HooptapReward(String jsonObj) {
        super(jsonObj);

        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("message"))
                this.message = json.getString("message");
            if (!json.isNull("importance"))
                this.importance = json.getInt("importance");
            if (!json.isNull("image"))
                this.image = json.getString("image");
            if (!json.isNull("quantity"))
                this.quantity = json.getInt("quantity");

        }catch (Exception e){e.printStackTrace();}
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class CompareRewards implements Comparator<HooptapItem> {

        @Override
        public int compare(HooptapItem t0, HooptapItem t1) {
            return ((HooptapReward)t1).getImportance() - ((HooptapReward)t0).getImportance();
        }
    }
}
