package com.hooptap.sdkbrandclub.Models;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapWeb extends HooptapItem {

    private String url_web;

    public HooptapWeb(String jsonObj) {
        super(jsonObj);
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("url"))
                this.url_web = json.getString("url");

        }catch (Exception e){}
    }

    public String getUrl_web() {
        return url_web;
    }

    public void setUrl_web(String url_web) {
        this.url_web = url_web;
    }
}
