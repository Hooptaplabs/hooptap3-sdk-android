package com.hooptap.sdkgameroom.Models;

import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapItem implements Serializable {

    public String id;
    public String type;
    public String name;
    public String image;
    public String description;
    public String url;
    public String extra_data;

    public HooptapItem(String jsonObj) {
        try{
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("_id"))
                this.id = json.getString("_id");
            if (!json.isNull("itemType"))
                if (json.getString("itemType").contains("Quiz"))
                    this.type = "Game";
                else
                    this.type = json.getString("itemType");
            else
                this.type = "Folder";
            if (!json.isNull("name"))
                this.name = json.getString("name");
            if (!json.isNull("image"))
                this.image = json.getString("image");
            if (!json.isNull("url_detail"))
                this.url = json.getString("url_detail");
            if (!json.isNull("desc"))
                this.description = json.getString("desc");
            if (!json.isNull("extra_data")) {
                this.extra_data = json.getString("extra_data");
            }

        }catch (Exception e){e.printStackTrace();}
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtra_data() {
        return extra_data;
    }

    public void setExtra_data(String extra_data) {
        this.extra_data = extra_data;
    }
}
