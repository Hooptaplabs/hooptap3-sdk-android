package com.hooptap.sdkbrandclub.Models;

/**
 * Created by root on 5/01/16.
 */
public class HooptapItem {
    private String _id;
    private String name;
    private String image;
    private String desc;
    private String itemType;

    public String getIdentificator() {
        return _id;
    }

    public void setIdentificator(String identificator) {
        this._id = identificator;
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
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
