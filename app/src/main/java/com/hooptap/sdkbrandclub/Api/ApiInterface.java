package com.hooptap.sdkbrandclub.Api;


import com.hooptap.a.Callback;
import com.hooptap.a.client.Response;
import com.hooptap.a.http.Body;
import com.hooptap.a.http.Field;
import com.hooptap.a.http.FormUrlEncoded;
import com.hooptap.a.http.GET;
import com.hooptap.a.http.Multipart;
import com.hooptap.a.http.PATCH;
import com.hooptap.a.http.POST;
import com.hooptap.a.http.PUT;
import com.hooptap.a.http.Part;
import com.hooptap.a.http.Path;
import com.hooptap.a.http.Query;
import com.hooptap.a.http.QueryMap;
import com.hooptap.a.mime.TypedFile;

import java.util.Map;

public interface ApiInterface {

    //Desarrollo
    /*String api = "//api.dev.hooptap.com/api/v1.0/";
    String api_new = "//api.dev.hooptap.com/api/v1.1.0/";
    String brandclub = "//api.dev.brandclubcorner.com/api/v1.0/";
    String brandclub_new = "//api.dev.brandclubcorner.com/api/v1.1.0/";*/

    // http://api.hooptap.com:8080/api/v1.0//pushNotifications/send/toAll
    // Produccion
    String api = "//api.hooptap.com:8080/api/v1.0/";
    String api_new = "//api.hooptap.com:8080/api/v1.1.0/";
    String brandclub = "//api.brandclubcorner.com:8080/api/v1.0/";
    String brandclub_new = "//api.brandclubcorner.com:8080/api/v1.1.0/";


    @FormUrlEncoded
    @POST(api + "Auth/")
    Response getToken(@Field("api_key") String api_key);

    @Multipart
    @PUT(api + "user/me/updateProfileImage")
    void uploadImageProfile(@Part("file") TypedFile file, Callback<Response> callback);

    @FormUrlEncoded
    @POST(brandclub_new + "{path}/game_played/{item_id}/user/{user_id}")
    void play(@Path("path") String path, @Path("item_id") String item_id, @Field("score") String puntuation,@Path("user_id") String user_id,
              Callback<Response> cb);

    @GET(brandclub + "product/{path}/config")
    void getConfig(@Path("path") String path, Callback<Response> cb);

    @FormUrlEncoded
    @POST(brandclub + "{path}/user")
    void register(@Path("path") String path, @Field("register_fields") String register_fields, Callback<Response> cb);

    @FormUrlEncoded
    @PUT(brandclub + "{path}/user/{user_id}")
    void registerEdit(@Path("path") String path, @Path("user_id") String user_id, @Field("register_fields") String register_fields, Callback<Response> cb);

    @FormUrlEncoded
    @PUT(brandclub + "{path}/user/{user_id}/guestregister")
    void registerGuest(@Path("path") String path, @Path("user_id") String user_id, @Field("register_fields") String register_fields, Callback<Response> cb);


    @GET(brandclub_new + "{path}/user/{user_id}/profile")
    void perfil(@Path("path") String path, @Path("user_id") String user_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(api + "util/createResetPasswordRequest")
    void resetPassword(@Field("email") String email, Callback<Response> callback);

    @FormUrlEncoded
    @POST(brandclub + "{path}/login")
    void login(@Path("path") String path, @Field("email") String email, @Field("password") String password,
               Callback<Response> cb);

    @FormUrlEncoded
    @POST("//{url_login}")
    void loginExternal(@Path(value="url_login", encode=false) String url_login, @Field("user_login") String user_login, @Field("password") String password,
               Callback<Response> cb);

    @GET(brandclub + "{path}/user/{user_id}/badges")
    void badges(@Path("path") String path, @Path("user_id") String user_id, Callback<Response> cb);


    @GET(brandclub + "{path}/user/{user_id}/badge/{badge_id}")
    void badge(@Path("path") String path, @Path("badge_id") String badge_id, @Path("user_id") String user_id, Callback<Response> cb);


    @GET(brandclub + "{path}/news")
    void news(@Path("path") String path, Callback<Response> cb);


    @GET(brandclub + "{path}/news/{news_id}")
    void newDetail(@Path("path") String path, @Path("news_id") String news_id, Callback<Response> cb);


    @GET(brandclub + "{path}/home/{user_id}")
    void home(@Path("path") String path,@Path("user_id") String user_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(brandclub + "{path}/socialregister")
    void socialregister(@Path("path") String path, @Field("social_network") String social_network,
                        @Field("social_params") String social_params, Callback<Response> cb);

    @FormUrlEncoded
    @POST(brandclub + "{path}/marketplace/purchase/{item_id}/user/{user_id}")
    void buyGood(@Path("path") String path, @Path("item_id") String item_id, @Path("user_id") String user_id, @Field("price_id") String price_id, @Field("shop_id") String shop_id,Callback<Response> cb);

    //Esta esta en el brand porque me hicieron una pasarelas por el tema de mandar "guest"
    @GET(brandclub + "{path}/user/{user_id}/friend/available")
    void getFriendsAvailable(@Path("path") String path, @Path("user_id") String user_id, @Query("search") String search, @Query("page_number") int num_page, @Query("page_size") int num_limit, Callback<Response> cb);

    @FormUrlEncoded
    @POST(api_new + "error_report/{user_id}")
    void reportError(@Path("user_id") String user_id, @Field("report_text") String report_text,  @Field("platform_info") String platform_info, Callback<Response> cb);

    @GET(api_new + "stringGroup/brandclub_{lenguage}")
    Response getStrings(@Path("lenguage") String lenguage);

    @BODY_DELETE(api_new + "marketplace/purchase/{item_id}/user/{user_id}")
    void deleteGood(@Path("user_id") String user_id, @Path("item_id") String item_id, Callback<Response> cb);

    @GET(api_new + "marketplace/user/{user_id}/purchase")
    void marketPlaceMine(@Path("user_id") String user_id, @Query("page_number") int num_page, @Query("page_size") int num_limit, Callback<Response> cb);

    @GET(api_new + "marketplace/good/{item_id}")
    void detailGood(@Path("item_id") String item_id, Callback<Response> cb);

    @GET(brandclub + "{path}/marketplace/user/{user_id}")
    void marketPlace(@Path("path") String path, @Path("user_id") String user_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(api_new + "engine/action/do/{action}/{user_id}")
    void doAction(@Path("action") String action, @Path("user_id") String user_id,
                  @Field("interaction_data") String interaction_data, @Field("target_id") String target_id,
                  Callback<Response> cb);


    @GET(api_new + "feed/{language}/user/{user_id}")
    void actividadReciente(@Path("language") String language, @Path("user_id") String user_id, @Query("page_number") int num_page, @Query("page_size") int num_limit, Callback<Response> cb);

    @GET(api_new + "level")
    void levels(@Query("page_number") int num_page, @Query("page_size") int num_limit, Callback<Response> cb);

    @GET(api_new + "ranking/user/{user_id}")
    void rank(@Path("user_id") String user_id, @Query("social") int social, Callback<Response> cb);

    //@GET(api_new + "user/{user_id}/quest")
    //void quests(@Path("user_id") String user_id, @Query("page_number") int num_page, @Query("page_size") int num_limit, Callback<Response> cb);

    @GET(api_new + "user/{user_id}/quest")
    void quests(@Path("user_id") String user_id, Callback<Response> cb);

    @GET(api_new + "user/{user_id}/quest/{quest_id}")
    void getQuestDetail(@Path("user_id") String user_id, @Path("quest_id") String quest_id, Callback<Response> cb);

    @POST(api_new + "quest/{quest_id}/user/{user_id}")
    void activeQuest(@Path("quest_id") String quest_id, @Path("user_id") String user_id, Callback<Response> cb);

    @GET(api_new + "item/{item_id}/user/{user_id}")
    void getItemDetail(@Path("item_id") String item_id, @Path("user_id") String user_id, Callback<Response> cb);

    @GET(api_new + "user/{user_id}/friend")
    void getFriends(@Path("user_id") String user_id, @Query("search") String search, @Query("page_number") int num_page, @Query("page_size") int num_limit, Callback<Response> cb);

    @GET(api_new + "user/{user_id}/friend/invitations")
    void getFriendsInvitations(@Path("user_id") String user_id, Callback<Response> cb);

    @GET(api_new + "user/{user_id}/friend/count")
    void getFriendsCount(@Path("user_id") String user_id, Callback<Response> cb);

    @POST(api_new + "friend/{user_id}/{friend_id}/request")
    void createRequest(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<Response> cb);

    @FormUrlEncoded
    @PUT(api_new + "friend/{user_id}/{friend_id}/request")
    void acceptRequest(@Path("user_id") String user_id, @Path("friend_id") String friend_id, @Field("friendship_code") String friendship_code,
                       Callback<Response> cb);

    @FormUrlEncoded
    @BODY_DELETE(api_new + "friend/{user_id}/{friend_id}/request")
    void deleteRequest(@Path("user_id") String user_id, @Path("friend_id") String friend_id, @Field("friendship_code") String friendship_code,
                       Callback<Response> cb);

    @PUT(api_new + "friend/{user_id}/{friend_id}")
    void friendBlock(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<Response> cb);

    @BODY_DELETE(api_new + "friend/{user_id}/{friend_id}")
    void friendDelete(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<Response> cb);

    @FormUrlEncoded
    @POST(api+"user/{user_id}/pushNotification/subscribe")
    void registerC2DM(@Path("user_id") String user_id, @Field("platform") String platform,
                      @Field("deviceToken") String deviceToken, Callback<Response> callback);

    @GET(api+"user/{user_id}/notification")
    void notificationsPage(@Path("user_id") String var1, @Query("page") int var2, @Query("limit") int var3, Callback<Response> var4);

    @PATCH(api+"user/{user_id}/notification/{id_notificacion}")
    void readNotification(@Path("user_id") String var1, @Path("id_notificacion") String var2, Callback<Response> var3);


    @GET(api+"user/{user_id}/notification/count")
    void countNotification(@Path("user_id") String var1, Callback<Response> var2);

    @GET(brandclub_new + "{path}/marketplace/shop")
    void tiendas(@Path("path") String path, Callback<Response> var2);

}
