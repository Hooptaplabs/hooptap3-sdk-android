package com.hooptap.sdkbrandclub.Utilities;

import com.hooptap.a.client.Response;
import com.hooptap.a.mime.TypedByteArray;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 3/11/15.
 */
public class Result<T> {
    public  T data;
    public final Response response;

    public Result(T data, Response response) {

        try {
            String s = new String(((TypedByteArray) response.getBody()).getBytes());
            JSONObject jsonObject=new JSONObject(s);
            this.data= (T) jsonObject  ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //this.data = data;
        this.response = response;
    }
}
