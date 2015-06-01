package com.hooptap.sdkgameroom.Engine;

import com.hooptap.sdkgameroom.Models.HooptapFolder;
import com.hooptap.sdkgameroom.Models.HooptapItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class ItemParseFolder {

    private String type;
    private HooptapItem item;

    public HooptapItem convertJson(JSONObject json) {

        try {

            if (type.contains("Quiz"))
                type = "Game";

            Class<HooptapItem> clsItem = (Class<HooptapItem>) Class.forName("com.hooptap.sdkgameroom.Models.Hooptap" + type);
            item = clsItem.getDeclaredConstructor(String.class).newInstance(json.toString());

            if (!json.isNull("items")) {
                JSONArray jsonItems = json.getJSONArray("items");
                ((HooptapFolder) item).setItemsJson(jsonItems.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return item;
    }
}
