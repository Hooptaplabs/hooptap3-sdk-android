package com.hooptap.sdkbrandclub.Deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Models.HooptapFeed;

import java.lang.reflect.Type;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapRewardDeserializer<T> implements JsonDeserializer<HooptapFeed> {

    private String itemType;

    @Override
    public HooptapFeed deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonItem = json.getAsJsonObject();

        HooptapFeed reward = new HooptapFeed();

        reward.setIdentificator(jsonItem.get("_id").getAsString());
        reward.setReason(jsonItem.get("reason").getAsString());
        reward.setReason_type(jsonItem.get("reason_type").getAsString());

        JsonObject reason_data = jsonItem.getAsJsonObject("reason_data");

        if (reward.getReason_type().equals("reward")) {
            itemType = reason_data.get("type").getAsString();
        } else {
            itemType = reward.getReason_type();
        }

        itemType = itemType.substring(0, 1).toUpperCase() + itemType.substring(1);

        Class cls = MapperObjects.getClassFromKey(itemType);

        reward.setFeed((T) context.deserialize(reason_data, cls));
        return reward;

    }

}