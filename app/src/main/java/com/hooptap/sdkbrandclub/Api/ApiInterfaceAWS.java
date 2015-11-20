package com.hooptap.sdkbrandclub.Api;


import com.hooptap.a.Callback;
import com.hooptap.a.client.Response;
import com.hooptap.a.http.Field;
import com.hooptap.a.http.FieldMap;
import com.hooptap.a.http.FormUrlEncoded;
import com.hooptap.a.http.GET;
import com.hooptap.a.http.Multipart;
import com.hooptap.a.http.PATCH;
import com.hooptap.a.http.POST;
import com.hooptap.a.http.PUT;
import com.hooptap.a.http.Part;
import com.hooptap.a.http.Path;
import com.hooptap.a.http.Query;
import com.hooptap.a.mime.TypedFile;
import com.hooptap.sdkbrandclub.AWS.model.Item;
import com.hooptap.sdkbrandclub.Interfaces.CallbackPrueba;
import com.hooptap.sdkbrandclub.Models.HooptapItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ApiInterfaceAWS {
    String url="https://afr4ap60wc.execute-api.eu-west-1.amazonaws.com/dev";
    @POST("")
    @GET("/items")
    List<Item> itemsGet();
}
