package com.hooptap.sdkbrandclub.Models;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapBadge extends HooptapItem {

    private int progress;
    private String image_on;
    private String image_off;
    private int importance = 2;

    public HooptapBadge(String jsonObj) {
        super(jsonObj);
        try{
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("progress"))
                this.progress = json.getInt("progress");
            if (!json.isNull("image_on"))
                this.image_on = json.getString("image_on");
            if (!json.isNull("image_off"))
                this.image_off = json.getString("image_off");
            if (!json.isNull("importance"))
                this.importance = json.getInt("importance");

        }catch (Exception e){e.printStackTrace();}
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getImage_on() {
        return image_on;
    }

    public void setImage_on(String image_on) {
        this.image_on = image_on;
    }

    public String getImage_off() {
        return image_off;
    }

    public void setImage_off(String image_off) {
        this.image_off = image_off;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
