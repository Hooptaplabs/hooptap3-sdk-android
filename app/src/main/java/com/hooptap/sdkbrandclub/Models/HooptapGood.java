package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

/**
 * Created by root on 5/01/16.
 */
public class HooptapGood extends HooptapItem{
    private String code_type;
    private JSONObject[] price;

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public JSONObject[] getPrice() {
        return price;
    }

    public void setPrice(JSONObject[] price) {
        this.price = price;
    }
}
