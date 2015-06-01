package com.hooptap.sdkgameroom.Engine;

import com.hooptap.sdkgameroom.Models.HooptapItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class ItemParse  {

    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();
    private String type;

    public ArrayList<HooptapItem> convertJson(JSONArray jsonItems) {

        try {

            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject json1 = jsonItems.getJSONObject(i);

                type = json1.getString("itemType");
                if (!type.contains("AdBanner")){
                    //El itemQuiz es lo mismo que el itemGame, por lo que si me llega ese tipo, lo cambiarea Game y arreando
                    if (type.contains("Quiz"))
                        type = "Game";

                    //Instancio el item segun el tipo, y busco el constructor que necesita un
                    //string(getDeclaredConstructor(String.class)), el constructor del hijo
                    //llamara primero al padre, el cual seteara los parametros genericos, luego
                    //el constructor del hijo seteara los parametros de su propia clase -> Magia Potagia fuck the police
                    Class<HooptapItem> cls;
                    cls = (Class<HooptapItem>) Class.forName("com.hooptap.sdkgameroom.Models.Hooptap" + type);
                    HooptapItem cl = cls.getDeclaredConstructor(String.class).newInstance(json1.toString());
                    arrayItems.add(cl);
                }
            }

        } catch (Exception e) {
            arrayItems.clear();
            e.printStackTrace();
        }


        return arrayItems;
    }
}
