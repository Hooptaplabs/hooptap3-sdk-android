package com.hooptap.sdkgameroom.Api;


import com.hooptap.a.Callback;
import com.hooptap.a.client.Response;
import com.hooptap.a.http.Field;
import com.hooptap.a.http.FormUrlEncoded;
import com.hooptap.a.http.GET;
import com.hooptap.a.http.POST;
import com.hooptap.a.http.Path;

public interface ApiInterface {

    String url = "//api.hooptap.com:8080/api/v1.0";
    String url_old = "//api.gameroomcorner.com:8080/api/v1.0/";

    @GET(url+"/Item/{item_id}")
    void getList(@Path("item_id") String item_id, Callback<Response> cb);

    @GET(url_old+"{path}/Game/{item_id}")
    void getListGame(@Path("path") String path,@Path("item_id") String item_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(url+"/Auth/")
    Response getToken(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(url_old+"{path}/game_played/{item_id}")
    void play(@Path("path") String path, @Path("item_id") String item_id, @Field("score") String puntuation, Callback<Response> cb);

    @GET(url_old+"product/{path}/config")
    void getConfig(@Path("path") String path, Callback<Response> cb);

    @GET(url+"/stringGroup/gameroom_{lenguage}")
    Response getStrings(@Path("lenguage") String lenguage);

    @FormUrlEncoded
    @POST(url_old+"{path}/reward/{item_id}/redeem")
    void redem(@Path("path") String path, @Path("item_id") String item_id, @Field("name") String name,
               @Field("email") String email, Callback<Response> cb);

    @FormUrlEncoded
    @POST(url+"/user/")
    void register(@Field("username") String username, @Field("password") String password, @Field("email") String email, @Field("birth") String birth,
                  @Field("lang") String lang, @Field("gender") int gender, Callback<Response> cb);

    /*@FormUrlEncoded
    @POST(url+"/Auth/")
    Response getToken(@Field("api_key") String api_key, @Field("expiration_unit") String expiration_unit,
                      @Field("expiration_quantity") Integer expiration_quantity);*/

}
