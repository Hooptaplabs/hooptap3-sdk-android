package com.hooptap.sdkbrandclub.Deserializer;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Models.HooptapActionResult;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Utilities.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapActionResultDeserializer<T> implements JsonDeserializer<HooptapActionResult> {

    private String itemType;

    @Override
    public HooptapActionResult deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        Gson gson = ParseObjects.gsonBuilder.create();

        final JsonObject jsonItem = json.getAsJsonObject();

        HooptapActionResult action = new HooptapActionResult();

        final JsonObject jsonObject = json.getAsJsonObject();
        JsonArray rewards = jsonObject.getAsJsonArray("rewards");

        ArrayList<T> rewardsArray = new ArrayList<>();
        for (int i = 0; i < rewards.size(); i++) {
            JsonElement jsonElement = rewards.get(i);
            if (jsonElement.getAsJsonObject().get("itemType") != null) {
                itemType = jsonElement.getAsJsonObject().get("itemType").getAsString();
            }
            Class cls = MapperObjects.getClassFromKey(itemType);
            rewardsArray.add((T) context.deserialize(jsonElement, cls));
        }
        action.setRewards(rewardsArray);

        JsonObject level_json = jsonItem.getAsJsonObject("level");
        Class cls_level = MapperObjects.getClassFromKey(Constants.LEVEL);
        action.setLevel((HooptapLevel) gson.fromJson(level_json, cls_level));

        JsonObject quest_json = jsonItem.getAsJsonObject("quest");
        Class cls_quest = MapperObjects.getClassFromKey(Constants.QUEST);
        action.setQuest((HooptapQuest) gson.fromJson(quest_json, cls_quest));

        return action;

    }

}