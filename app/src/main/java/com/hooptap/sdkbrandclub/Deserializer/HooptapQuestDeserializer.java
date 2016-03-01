package com.hooptap.sdkbrandclub.Deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Models.HooptapStep;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapQuestDeserializer<T> implements JsonDeserializer<HooptapQuest> {

    @Override
    public HooptapQuest deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        Gson innerGson = new Gson();
        Gson gson = ParseObjects.gsonBuilder.create();

        final JsonObject jsonItem = json.getAsJsonObject();

        HooptapQuest hooptapQuest = innerGson.fromJson(jsonItem, HooptapQuest.class);
        hooptapQuest.setNumCompletedSteps(jsonItem.get("completed_steps").getAsJsonArray().size());

        ArrayList<HooptapStep> arrayListSteps = new ArrayList<>();
        if (jsonItem.has("steps")) {
            JsonArray stepsJsonArray = jsonItem.getAsJsonArray("steps");
            for (int i = 0; i < stepsJsonArray.size(); i++) {
                JsonObject stepJson = stepsJsonArray.get(i).getAsJsonObject();
                HooptapStep step = gson.fromJson(stepJson, HooptapStep.class);
                arrayListSteps.add(step);
            }
        }
        hooptapQuest.setSteps(arrayListSteps);

        return hooptapQuest;
    }
}