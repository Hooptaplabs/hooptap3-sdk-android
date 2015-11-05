package com.hooptap.sdkbrandclub.Api;




import android.util.Log;

import com.hooptap.d.Gson;
import com.hooptap.d.JsonDeserializationContext;
import com.hooptap.d.JsonDeserializer;
import com.hooptap.d.JsonElement;
import com.hooptap.d.JsonObject;
import com.hooptap.d.JsonParseException;
import com.hooptap.sdkbrandclub.Models.HooptapItem;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by root on 4/11/15.
 */
public class ConverterJSON <T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = jsonElement.getAsJsonObject().get("response");
        Log.e("prueba8",content.toString());
        Log.e("prueba9",content.getAsJsonObject().get("items").toString());
        //content.getAsJsonObject().get("items");
        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson( content.getAsJsonObject().get("items"), type);
    }
}
