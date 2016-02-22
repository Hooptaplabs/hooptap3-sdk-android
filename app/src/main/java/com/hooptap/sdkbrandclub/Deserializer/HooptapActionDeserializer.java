package com.hooptap.sdkbrandclub.Deserializer;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Models.HooptapAction;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapReward;
import com.hooptap.sdkbrandclub.Utilities.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapActionDeserializer<T> implements JsonDeserializer<HooptapAction> {

    private String itemType;

    @Override
    public HooptapAction deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonItem = json.getAsJsonObject();

        HooptapAction action = new HooptapAction();

        final JsonObject jsonObject = json.getAsJsonObject();
        JsonArray rewards = jsonObject.getAsJsonArray("rewards");
        Log.e("DESERIALIZER", rewards + " /");

        ArrayList<T> rewardsArray = new ArrayList<>();
        for (int i = 0; i < rewards.size(); i++) {
            JsonElement jsonElement = rewards.get(i);
            Log.e("ELMENT",jsonElement.getAsJsonObject().toString());
            if (jsonElement.getAsJsonObject().get("itemType") != null) {
                itemType = jsonElement.getAsJsonObject().get("itemType").getAsString();
            }
            Class cls = MapperObjects.getClassFromKey(itemType);
            rewardsArray.add((T) context.deserialize(jsonElement, cls));
        }
        action.setRewards(rewardsArray);

        JsonObject level_json = jsonItem.getAsJsonObject("level");

        Class cls = MapperObjects.getClassFromKey(Constants.LEVEL);

        action.setLevel((HooptapLevel) context.deserialize(level_json, cls));

        return action;

    }

}