package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Models.HooptapItem;



import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


import java.util.ArrayList;

/**
 * Esta clase esta porque JSON  ordenaba el contenido,solo se utiliza en la home porque viene contenido
 * variariado
 * "jettison"
 *
 */
public class ItemParseLibrary {

    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();
    private String type;

    public ArrayList<HooptapItem> convertJson(JSONArray jsonItems) {

        try {

            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject json1 = jsonItems.getJSONObject(i);

                if (!json1.isNull("itemType")){
                    type = json1.getString("itemType");
                }else{
                    type = "Link";
                }
                if (!type.contains("AdBanner")){
                    //El itemQuiz es lo mismo que el itemGame, por lo que si me llega ese tipo, lo cambiarea Game y arreando
                    if (type.contains("Quiz"))
                        type = "Game";

                    type = type.substring(0, 1).toUpperCase() + type.substring(1);
                    //Instancio el item segun el tipo, y busco el constructor que necesita un
                    //string(getDeclaredConstructor(String.class)), el constructor del hijo
                    //llamara primero al padre, el cual seteara los parametros genericos, luego
                    //el constructor del hijo seteara los parametros de su propia clase -> Magia Potagia fuck the police
                    Class<HooptapItem> cls;
                    cls = (Class<HooptapItem>) Class.forName("com.hooptap.sdkbrandclub.Models.Hooptap" + type);
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