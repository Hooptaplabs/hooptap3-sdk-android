package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapReward;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class ItemParseReward {

    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();

    public ArrayList<HooptapItem> convertJson(JSONArray jsonItems) {

        try {

            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject json1 = jsonItems.getJSONObject(i);

                //Instancio el item segun el tipo, y busco el constructor que necesita un
                //string(getDeclaredConstructor(String.class)), el constructor del hijo
                //llamara primero al padre, el cual seteara los parametros genericos, luego
                //el constructor del hijo seteara los parametros de su propia clase -> Magia Potagia fuck the police
                Class<HooptapItem> cls;
                cls = (Class<HooptapItem>) Class.forName("com.hooptap.sdkbrandclub.Models.HooptapReward");
                HooptapItem cl = cls.getDeclaredConstructor(String.class).newInstance(json1.toString());

                if (json1.getString("itemType").contains("Badge")){
                    if (json1.getInt("progress") == 100) {
                        cl.setImage(json1.getString("image_on"));
                    }else{
                        cl.setImage(json1.getString("image_off"));
                    }
                }

                arrayItems.add(cl);

            }

        } catch (Exception e) {
            arrayItems.clear();
            e.printStackTrace();
        }

        Collections.sort(arrayItems, new HooptapReward.CompareRewards());

        return arrayItems;
    }
}
