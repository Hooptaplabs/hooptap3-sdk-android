package com.hooptap.sdkbrandclub.Deserializer;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapReward;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapRewardDeserializer<T> implements JsonDeserializer<HooptapListResponse> {

    private String itemSubtype;
    private String itemType;

    public HooptapRewardDeserializer(String subtype) {
        this.itemSubtype = subtype;
    }

    @Override
    public HooptapListResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        HooptapReward reward = new HooptapReward();

        final JsonObject jsonObject = json.getAsJsonObject();

        reward.setIdentificator(jsonObject.get("_id").getAsString());
        reward.setReason(jsonObject.get("reason").getAsString());
        reward.setReason_type(jsonObject.get("reason_type").getAsString());

        HooptapLevel item = new HooptapLevel();
        item.setDescription("desc");
        item.setMin_points(5);
        item.setNumber(1);
        item.setImage("https://hooptap.s3.amazonaws.com/images/560914db4871f62672e3ecff/item/14437809765700.png");
        item.setName("Conductor ocasional");
        item.setIdentificator("56b0a1d169b4d2970a68a580");
        reward.setReward(item);



        Log.e("DESERIALIZER0", jsonObject + " /");
        JsonArray items = jsonObject.getAsJsonArray("items");
        Log.e("DESERIALIZER", items+" /");

        ArrayList<T> itemsArray = new ArrayList<>();
        //Recorro los items, saco su tipo y genero su objeto correspondiente de forma automatica
        for (int i = 0; i < items.size(); i++) {
            JsonElement jsonElement = items.get(i);
            Log.e("ELMENT",jsonElement.getAsJsonObject().toString());
            if (jsonElement.getAsJsonObject().get("itemType") != null) {
                itemType = jsonElement.getAsJsonObject().get("itemType").getAsString();
            }else{
                itemType = itemSubtype;
            }
            Class cls = MapperObjects.getClassFromKey(itemType);
            itemsArray.add((T) context.deserialize(jsonElement, cls));
        }
        htResponse.setItemArray(itemsArray);

        return htResponse;
    }

//    public void setSubType(String itemSubtype){
//        subtype = itemSubtype;
//    }
}