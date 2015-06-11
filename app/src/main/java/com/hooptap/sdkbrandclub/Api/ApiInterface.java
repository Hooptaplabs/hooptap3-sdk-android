package com.hooptap.sdkbrandclub.Api;


import com.hooptap.a.Callback;
import com.hooptap.a.client.Response;
import com.hooptap.a.http.Body;
import com.hooptap.a.http.Field;
import com.hooptap.a.http.FormUrlEncoded;
import com.hooptap.a.http.GET;
import com.hooptap.a.http.Multipart;
import com.hooptap.a.http.POST;
import com.hooptap.a.http.PUT;
import com.hooptap.a.http.Part;
import com.hooptap.a.http.Path;
import com.hooptap.a.http.Query;
import com.hooptap.a.http.QueryMap;
import com.hooptap.a.mime.TypedFile;

import java.util.Map;

public interface ApiInterface {

    String url = "//api.hooptap.com:8080/api/v1.0/";
    String url_brand = "//api.brandclubcorner.com:8080/api/v1.0/";

    @GET(url + "Item/{item_id}")
    void getList(@Path("item_id") String item_id, Callback<Response> cb);

    @GET(url_brand + "{path}/Game/{item_id}")
    void getListGame(@Path("path") String path, @Path("item_id") String item_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(url + "Auth/")
    Response getToken(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(url_brand + "{path}/game_played/{item_id}")
    void play(@Path("path") String path, @Path("item_id") String item_id, @Field("score") String puntuation, Callback<Response> cb);

    @GET(url_brand + "product/{path}/config")
    void getConfig(@Path("path") String path, Callback<Response> cb);

    @GET(url + "stringGroup/gameroom_{lenguage}")
    Response getStrings(@Path("lenguage") String lenguage);

    @FormUrlEncoded
    @POST(url_brand + "{path}/reward/{item_id}/redeem")
    void redem(@Path("path") String path, @Path("item_id") String item_id, @Field("name") String name,
               @Field("email") String email, Callback<Response> cb);

    @FormUrlEncoded
    @POST(url_brand + "{path}/user/")
    void register(@Path("path") String path, @Field("username") String username, @Field("password") String password, @Field("email") String email, @Field("birth") String birth,
                  @Field("lang") String lang, @Field("gender") int gender, Callback<Response> cb);

    @FormUrlEncoded
    @POST("//api.dev.hooptap.com:8080/api/v1.1.0/test_params")
    void register2(@Field("parameters") String parameters, Callback<Response> cb);

    @FormUrlEncoded
    @POST(url_brand + "util/createResetPasswordRequest")
    void resetPassword(@Field("email") String email, Callback<Response> callback);

    @FormUrlEncoded
    @POST(url_brand + "{path}/login")
    void login(@Path("path") String path, @Field("email") String email, @Field("password") String password, Callback<Response> cb);

    @Multipart
    @PUT(url + "user/me/updateProfileImage")
    void uploadImageProfile(@Part("file") TypedFile file, Callback<Response> callback);

    @GET(url_brand + "{path}/user/{user_id}/profile")
    void perfil(@Path("path") String path, @Path("user_id") String user_id, Callback<Response> cb);

    @POST(url + "/purchase/{item_id}")
    void buyGood(@Path("item_id") String item_id, Callback<Response> cb);

    @BODY_DELETE(url + "/user/{user_id}/purchase/{item_id}")
    void deleteGood(@Path("user_id") String user_id, @Path("item_id") String item_id, Callback<Response> cb);

    @GET(url + "good/{item_id}")
    void detailGood(@Path("item_id") String item_id, Callback<Response> cb);

    @GET(url + "good")
    void marketPlace(Callback<Response> cb);

    @GET(url + "user/{user_id}/purchase")
    void marketPlaceMine(@Path("user_id") String user_id, Callback<Response> cb);

    /*@FormUrlEncoded
    @POST(url+"/Auth/")
    Response getToken(@Field("api_key") String api_key, @Field("expiration_unit") String expiration_unit,
                      @Field("expiration_quantity") Integer expiration_quantity);*/

}
