package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by carloscarrasco on 26/5/15.
 */
public class HooptapReward extends HooptapItem{
    private String redeem_code;
    private int quantity;
    private String message;
    private int importance;
    private String type_reward;

    public HooptapReward(String jsonObj) {
        super(jsonObj);

        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("redeem_code"))
                this.redeem_code = json.getString("redeem_code");
            if (!json.isNull("quantity"))
                this.quantity = json.getInt("quantity");
            if (!json.isNull("message"))
                this.message = json.getString("message");
            if (!json.isNull("importance"))
                this.importance = json.getInt("importance");
            if (!json.isNull("image"))
                this.image = json.getString("image");
            if (!json.isNull("itemType"))
                this.type_reward = json.getString("itemType");
            if (!json.isNull("type"))
                this.type_reward = json.getString("type");

        }catch (Exception e){e.printStackTrace();}
    }

    public String getRedeem_code() {
        return redeem_code;
    }

    public void setRedeem_code(String redeem_code) {
        this.redeem_code = redeem_code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getType_reward() {
        return type_reward;
    }

    public void setType_reward(String type_reward) {
        this.type_reward = type_reward;
    }

    public static class CompareRewards implements Comparator<HooptapItem> {

        @Override
        public int compare(HooptapItem t0, HooptapItem t1) {
            return ((HooptapReward)t1).getImportance() - ((HooptapReward)t0).getImportance();
        }
    }
}
