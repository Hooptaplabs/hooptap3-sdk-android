package com.hooptap.sdkgameroom.Models;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 26/5/15.
 */
public class HooptapReward extends HooptapItem{
    private String redeem_code;

    public HooptapReward(String jsonObj) {
        super(jsonObj);

        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("redeem_code"))
                this.redeem_code = json.getString("redeem_code");
        }catch (Exception e){e.printStackTrace();}
    }

    public String getRedeem_code() {
        return redeem_code;
    }

    public void setRedeem_code(String redeem_code) {
        this.redeem_code = redeem_code;
    }
}
