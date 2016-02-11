package com.hooptap.sdkbrandclub.Deserializer;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class HooptapResponseDeserializer<T> implements JsonDeserializer<HooptapListResponse> {

    private String itemSubtype;
    private String itemType;

    public HooptapResponseDeserializer(String subtype) {
        this.itemSubtype = subtype;
    }

    @Override
    public HooptapListResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        HooptapListResponse htResponse = new HooptapListResponse();

        final JsonObject jsonObject = json.getAsJsonObject();
        //Parseo la parte de paginacion
        if (jsonObject.getAsJsonObject("paging") != null) {
            JsonObject paging = jsonObject.getAsJsonObject("paging");

            final int current_page = paging.get("current_page").getAsInt();
            final int page_size = paging.get("page_size").getAsInt();
            final int total_pages = paging.get("total_pages").getAsInt();
            final int item_count = paging.get("item_count").getAsInt();

            htResponse.setItem_count(item_count);
            htResponse.setCurrent_page(current_page);
            htResponse.setTotal_pages(total_pages);
            htResponse.setPage_size(page_size);
        }
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