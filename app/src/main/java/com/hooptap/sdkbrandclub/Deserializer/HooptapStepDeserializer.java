package com.hooptap.sdkbrandclub.Deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Models.HooptapAction;
import com.hooptap.sdkbrandclub.Models.HooptapStep;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapStepDeserializer<T> implements JsonDeserializer<HooptapStep> {

    @Override
    public HooptapStep deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        Gson innerGson = new Gson();
        Gson gson = ParseObjects.gsonBuilder.create();

        final JsonObject jsonStep = json.getAsJsonObject();

        HooptapStep step = innerGson.fromJson(jsonStep, HooptapStep.class);

        ArrayList<HooptapAction> actionsArray = new ArrayList<>();
        JsonArray actionsJsonArray = jsonStep.getAsJsonArray("actions");
        for (int f = 0; f < actionsJsonArray.size(); f++) {
            JsonObject actionJson = actionsJsonArray.get(f).getAsJsonObject();
            HooptapAction action = gson.fromJson(actionJson, HooptapAction.class);
            actionsArray.add(action);
        }
        step.setActions(actionsArray);

        return step;
    }
}