package com.hooptap.sdkgameroom.Api;


import com.hooptap.a.Callback;
import com.hooptap.a.client.Response;
import com.hooptap.a.http.Field;
import com.hooptap.a.http.FormUrlEncoded;
import com.hooptap.a.http.GET;
import com.hooptap.a.http.POST;
import com.hooptap.a.http.Path;

public interface ApiInterface {

    String url = "//api.hooptap.com:8080/api/v1.1.0";

    @GET(url+"/Item/{item_id}")
    void getList(@Path("item_id") String item_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(url+"/Auth/")
    Response getToken(@Field("api_key") String api_key);

    /*@FormUrlEncoded
    @POST(url+"/Auth/")
    Response getToken(@Field("api_key") String api_key, @Field("expiration_unit") String expiration_unit,
                      @Field("expiration_quantity") Integer expiration_quantity);*/

    /*@FormUrlEncoded
    @POST(url+"/user/")
    void register(@Field("username") String username, @Field("password") String password, @Field("email") String email, @Field("birth") String birth,
                  @Field("lang") String lang, @Field("gender") int gender, Callback<Response> cb);*/
}
