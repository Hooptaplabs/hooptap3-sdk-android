package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapFolder extends HooptapItem {

    private String itemsJson;

    public HooptapFolder(String jsonObj) {
        super(jsonObj);
    }


    public String getItemsJson() {
        return itemsJson;
    }

    public void setItemsJson(String itemsJson) {
        this.itemsJson = itemsJson;
    }

}
