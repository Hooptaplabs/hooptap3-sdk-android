package com.hooptap.sdkbrandclub.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 13/7/15.
 */
public class HooptapQuestStep implements Serializable {
    private String name;
    private String desc;
    private String link_type;
    private String link_value;
    private String image;
    //private ArrayList<HooptapReward> rewards = new ArrayList<>();

    public HooptapQuestStep(String jsonObj) {
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("name"))
                this.name = json.getString("name");
            if (!json.isNull("desc"))
                this.desc = json.getString("desc");
            if (!json.isNull("link_type"))
                this.link_type = json.getString("link_type");
            if (!json.isNull("link_value"))
                this.link_value = json.getString("link_value");
            if (!json.isNull("image"))
                this.image = json.getString("image");
            /*if (!json.isNull("rewards")) {
                JSONArray rewardsArray = json.getJSONArray("rewards");
                for (int i = 0; i < rewardsArray.length(); i++) {
                    rewards.add(new HooptapReward(rewardsArray.getJSONObject(i).toString()));
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getLink_value() {
        return link_value;
    }

    public void setLink_value(String link_value) {
        this.link_value = link_value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}