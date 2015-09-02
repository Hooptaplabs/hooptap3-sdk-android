package com.hooptap.sdkbrandclub.Models;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapGood extends HooptapItem  {

    private String code_type;
    private String code;
    private String price;
    private String id_compra;
    private boolean shop_redeem;

    public HooptapGood(String jsonObj) {
        super(jsonObj);
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("price"))
                this.price = json.getJSONArray("price").toString();
            if (!json.isNull("code_type"))
                this.code_type = json.getString("code_type");
            if (!json.isNull("code"))
                this.code = json.getString("code");
            if (!json.isNull("id_compra"))
                this.id_compra = json.getString("id_compra");
            if (!json.isNull("shop_redeem"))
                this.shop_redeem = json.getBoolean("shop_redeem");

        }catch (Exception e){e.printStackTrace();}
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId_compra() {
        return id_compra;
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public boolean isShop_redeem() {
        return shop_redeem;
    }

    public void setShop_redeem(boolean shop_redeem) {
        this.shop_redeem = shop_redeem;
    }
}
