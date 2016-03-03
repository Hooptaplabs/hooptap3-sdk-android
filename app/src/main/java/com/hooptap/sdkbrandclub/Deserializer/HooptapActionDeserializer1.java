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
import com.hooptap.sdkbrandclub.Models.HooptapActionFields;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapActionDeserializer1<T> implements JsonDeserializer<HooptapAction> {

    @Override
    public HooptapAction deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        Gson gson = ParseObjects.gsonBuilder.create();

        final JsonObject jsonAction = json.getAsJsonObject();

        HooptapAction action = new HooptapAction();
        action.setName(jsonAction.get("name").getAsString());

        ArrayList<HooptapActionFields> actionsFieldArray = new ArrayList<>();
        JsonArray matchingFieldsJsonArray = jsonAction.getAsJsonArray("matching_fields");
        for (int i = 0; i < matchingFieldsJsonArray.size(); i++) {
            JsonObject matchingFieldJson = matchingFieldsJsonArray.get(i).getAsJsonObject();
            HooptapActionFields actionField = gson.fromJson(matchingFieldJson, HooptapActionFields.class);
            actionsFieldArray.add(actionField);
        }

        action.setActionFields(actionsFieldArray);

        return action;
    }
}