package com.hooptap.sdkbrandclub.Api;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.hooptap.a.Callback;
import com.hooptap.a.RetrofitError;
import com.hooptap.a.client.Response;
import com.hooptap.a.mime.TypedFile;
import com.hooptap.a.mime.TypedInput;
import com.hooptap.sdkbrandclub.Engine.ItemParse;
import com.hooptap.sdkbrandclub.Models.HooptapFriend;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Models.HooptapQuestStep;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Models.HooptapGameStatus;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApi {
    //Primer commit Development
    private static int retry = 0;


    public static JSONObject generateJsonToResponse(Response response) throws Exception {
        TypedInput body = response.getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }

        return new JSONObject(out.toString());
    }

    public static String getToken(final String apikey) {
        Response response = Hooptap.getClient().getToken(apikey);
        try {
            JSONObject json = generateJsonToResponse(response);
            String newToken = json.getString("token");
            Hooptap.setToken(newToken);
            return newToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static ResponseError generateError(Response error) {
        ResponseError newError;
        if (error != null) {
            newError = new ResponseError(error);
            JSONObject jsonerror = new JSONObject();
            try {
                jsonerror = generateJsonToResponse(error);
                newError.setReason(jsonerror.getString("message"));
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    newError.setReason(jsonerror.getJSONObject("status").getString("message"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            newError = new ResponseError();
        }

        return newError;
    }

    public static void play(final String path, final String itemId, final String puntuation,
                            final HooptapCallback<HashMap<String, Object>> callback) {

        Hooptap.getClient().
                play(path, itemId, puntuation,"me", new Callback<Response>() {
                    public ArrayList<HooptapItem> arrayItems;

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            Log.e("play", json + " / " + jsonStatus);
                            JSONArray rewards = jsonStatus.getJSONArray("rewards");
                            arrayItems = new ItemParse().convertJson(rewards);

                            HashMap<String, Object> hasmap = new HashMap<>();

                            if (!jsonStatus.isNull("passed")) {
                                hasmap.put("passed", jsonStatus.getBoolean("passed"));
                            }
                            if (arrayItems.size() > 0) {
                                hasmap.put("reward", arrayItems);
                            }

                            if (!jsonStatus.isNull("quest")) {
                                JSONObject jsonQuest = jsonStatus.getJSONObject("quest");
                                jsonQuest.put("itemType", "Quest");
                                hasmap.put("quest", jsonQuest);
                            }

                            callback.onSuccess(hasmap);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {

                        if (retry < 1) {
                            retry++;
                            play(path, itemId, puntuation, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));

                    }
                });

    }

    //En este metodo devolvemos un array de items, estos items seran o baged o points, en el codigo pasaremos este array a una funcion
    //para que muestre el toast correspondiente, o la nota fin si es un juego
    public static void doAction(final String action, final String user_id, final String interaction_data,
                                final String target_id, final HooptapCallback<HashMap<String, Object>> callback) {

        Hooptap.getClient().
                doAction(action, user_id, interaction_data, target_id, new Callback<Response>() {
                    public ArrayList<HooptapItem> arrayItems;

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            Log.e("doAction", json + " / " + jsonStatus);
                            JSONArray rewards = jsonStatus.getJSONArray("rewards");
                            arrayItems = new ItemParse().convertJson(rewards);

                            HashMap<String, Object> hasmap = new HashMap<>();

                            if (!jsonStatus.isNull("passed")) {
                                hasmap.put("passed", jsonStatus.getBoolean("passed"));
                            }
                            if (arrayItems.size() > 0) {
                                hasmap.put("reward", arrayItems);
                            }

                            if (!jsonStatus.isNull("quest")) {
                                JSONObject jsonQuest = jsonStatus.getJSONObject("quest");
                                jsonQuest.put("itemType", "Quest");
                                hasmap.put("quest", jsonQuest);
                            }

                            callback.onSuccess(hasmap);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            doAction(action, user_id, interaction_data, target_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));

                    }
                });

    }

    public static void reportarError(final String report_text, final String user_id, final String info_mobile, final HooptapCallback<JSONObject> callback) {
        Hooptap.getClient().reportError(user_id, report_text, info_mobile, new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                try {
                    JSONObject json = generateJsonToResponse(response);
                    callback.onSuccess(json);
                } catch (Exception e) {
                    callback.onError(new ResponseError(response));
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retry < 1) {
                    retry++;
                    reportarError(report_text, user_id, info_mobile, callback);
                } else
                    callback.onError(generateError(retrofitError.getResponse()));
            }
        });
    }

    public static void resetPassword(final String email, final HooptapCallback<String> callback) {

        Hooptap.getClient().
                resetPassword(email, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        callback.onSuccess("");
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {

                        if (retry < 1) {
                            retry++;
                            resetPassword(email, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));

                    }
                });

    }

    public static void getConfig(final String path, final HooptapCallback<JSONObject> callback) {
        Log.e("CONFIG", path + " /");
        Hooptap.getClient().
                getConfig(path, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus);

                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getConfig(path, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));

                    }
                });
    }

    public static JSONObject getStrings(final String language) {

        Response response = Hooptap.getClient().getStrings(language);
        try {
            JSONObject json = generateJsonToResponse(response);
            JSONObject jsonStatus = json.getJSONObject("response");
            return jsonStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void register(final String path, final String json, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                register(path, json, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            register(path, json, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void registerEdit(final String path, final String user_id, final String json, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                registerEdit(path, user_id, json, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            registerEdit(path, user_id, json, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void registerGuest(final String path, final String user_id, final String json, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                registerGuest(path, user_id, json, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            registerGuest(path, user_id, json, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void login(final String path, final String username, final String password, final HooptapCallback<JSONObject> callback) {
        Hooptap.getClient().
                login(path, username, password, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            login(path, username, password, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void socialRegister(final String path, final String social_network, final String social_params, final HooptapCallback<JSONArray> callback) {
        Hooptap.getClient().
                socialregister(path, social_network, social_params, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus.getJSONArray("register_fields"));
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            socialRegister(path, social_network, social_params, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void uploadPhotoProfile(final Bitmap bitmap, final HooptapCallback<JSONObject> callback) {

        File imageFileFolder = new File(Environment.getExternalStorageDirectory(), "/BrandClub/image/");
        if (!imageFileFolder.exists()) {
            imageFileFolder.mkdir();
        }

        FileOutputStream out = null;

        File imageFileName = new File(imageFileFolder, "photoUpload.jpg");
        try {
            out = new FileOutputStream(imageFileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (IOException e) {
            ResponseError response = new ResponseError();
            response.setReason(e.getMessage());
            callback.onError(response);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e("Failed", "Failed to close output stream", e);
            }
        }

        TypedFile image = new TypedFile("image/jpeg", imageFileName);


        Hooptap.getClient().uploadImageProfile(image, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    JSONObject json = generateJsonToResponse(response);
                    JSONObject jsonStatus = json.getJSONObject("response");
                    callback.onSuccess(jsonStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (retry < 1) {
                    retry++;
                    uploadPhotoProfile(bitmap, callback);
                } else
                    callback.onError(generateError(error.getResponse()));
            }
        });
    }

    public static void getPerfil(final String path, final String user_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                perfil(path, user_id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            callback.onSuccess(jsonStatus);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (retry < 1) {
                            retry++;
                            getPerfil(path, user_id, callback);
                        } else
                            callback.onError(generateError(error.getResponse()));
                    }
                });

    }

    public static void getActividadReciente(final String lenguage, final String user_id, final int page, final int limit, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient()
                .actividadReciente(lenguage, user_id, page, limit, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            //JSONArray jsonItems = json.getJSONArray("response");
                            JSONObject genericJson = json.getJSONObject("response");
                            JSONArray jsonItems = genericJson.getJSONArray("items");
                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (retry < 1) {
                            retry++;
                            getActividadReciente(lenguage, user_id, page, limit, callback);
                        } else
                            callback.onError(generateError(error.getResponse()));
                    }
                });

    }

    public static void getMyMarketPlace(final String user_id, final int page, final int limit, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                marketPlaceMine(user_id, page, limit, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            JSONArray jsonItems = genericJson.getJSONArray("items");
                            JSONArray newJsonItems = new JSONArray();

                            //Esto es una pequeño fix que debemos hacer porque el good que llega no es el mismo
                            for (int i = 0; i < jsonItems.length(); i++) {
                                JSONObject js = (JSONObject) jsonItems.get(i);
                                JSONObject jo = (JSONObject) js.get("good");
                                jo.put("code", js.get("code"));
                                jo.put("id_compra", js.get("_id"));
                                newJsonItems.put(jo);
                            }

                            arrayItems = new ItemParse().convertJson(newJsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getMyMarketPlace(user_id, page, limit, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });
    }

    public static void getMarketPlace(final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                marketPlace(new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);

                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getMarketPlace(callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });
    }

    public static void buyGood(final String path, final String item_id, final String user_id, final String price_id, final HooptapCallback<HashMap<String, Object>> callback) {

        Hooptap.getClient().
                buyGood(path, item_id, user_id, price_id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            Log.e("play", json + " / " + jsonStatus);
                            JSONArray rewards = jsonStatus.getJSONArray("rewards");
                            ArrayList<HooptapItem> arrayItems = new ItemParse().convertJson(rewards);

                            HashMap<String, Object> hasmap = new HashMap<>();

                            hasmap.put("jsonObject", jsonStatus);

                            if (arrayItems.size() > 0) {
                                hasmap.put("reward", arrayItems);
                            }

                            if (!jsonStatus.isNull("quest")) {
                                JSONObject jsonQuest = jsonStatus.getJSONObject("quest");
                                jsonQuest.put("itemType", "Quest");
                                hasmap.put("quest", jsonQuest);
                            }

                            callback.onSuccess(hasmap);
                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                            e.printStackTrace();
                        }
                        /*try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);
                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }*/
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            buyGood(path, item_id, user_id, price_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void deleteGood(final String user_id, final String item_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                deleteGood(user_id, item_id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);
                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            deleteGood(user_id, item_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void detailGood(final String item_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                detailGood(item_id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);
                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            detailGood(item_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getBadges(final String path, final String user_id, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                badges(path, user_id, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONArray jsonItems = json.getJSONArray("response");

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getBadges(path, user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getBadge(final String path, final String badge_id, final String user_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                badge(path, badge_id, user_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getBadge(path, badge_id, user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getNews(final String path, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                news(path, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonGeneric = json.getJSONObject("response");
                            JSONArray jsonItems = jsonGeneric.getJSONArray("items");

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getNews(path, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getNew(final String path, final String new_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                newDetail(path, new_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getNew(path, new_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getHome(final String path, final String user_id, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                home(path, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");

                            JSONArray jsonGoods = genericJson.getJSONArray("goods");
                            JSONArray jsonGames = genericJson.getJSONArray("games");

                            ArrayList<HooptapItem> arrayGoods = new ItemParse().convertJson(jsonGoods);
                            ArrayList<HooptapItem> arrayGames = new ItemParse().convertJson(jsonGames);

                            arrayItems.addAll(arrayGames);
                            arrayItems.addAll(arrayGoods);

                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getHome(path, user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getLevels(final int page, final int limit, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                levels(page, limit, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");

                            JSONArray jsonItems = genericJson.getJSONArray("items");
                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getLevels(page, limit, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getRank(final String user_id, final int social, final HooptapCallback<JSONArray> callback) {

        Hooptap.getClient().
                rank(user_id, social, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONArray genericJson = json.getJSONArray("response");
                            callback.onSuccess(genericJson);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getRank(user_id, social, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

/*    public static void getQuests(final String user_id, final int page, final int limit, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                quests(user_id, page, limit, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONArray jsonItems = json.getJSONArray("response");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                jsonItems.getJSONObject(i).put("itemType", "Quest");
                            }

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getQuests(user_id, page, limit, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }*/

    public static void getQuests(final String user_id, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                quests(user_id, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONArray jsonItems = json.getJSONArray("response");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                jsonItems.getJSONObject(i).put("itemType", "Quest");
                            }

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getQuests(user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getQuest(final String user_id, final String quest_id, final HooptapCallback<HooptapQuest> callback) {

        Hooptap.getClient().
                getQuestDetail(user_id, quest_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            HooptapQuest quest = new HooptapQuest(genericJson + "");
                            callback.onSuccess(quest);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getQuest(user_id, quest_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void activeQuest(final String quest_id, final String user_id, final HooptapCallback<HooptapQuest> callback) {

        Hooptap.getClient().
                activeQuest(quest_id, user_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            HooptapQuest quest = new HooptapQuest(genericJson + "");
                            callback.onSuccess(quest);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            activeQuest(quest_id, user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getItemDetail(final String item_id, final String user_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                getItemDetail(item_id, user_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            callback.onSuccess(genericJson);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getItemDetail(item_id, user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getFriends(final String user_id, final String search, final int page_number, final int page_size, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                getFriends(user_id, search, page_number, page_size, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            /*JSONObject json = generateJsonToResponse(response);
                            JSONArray jsonItems = json.getJSONArray("response");
                            JSONArray finalArray = new JSONArray();
                            for (int i = 0; i < jsonItems.length(); i++) {
                                JSONObject json1 = jsonItems.getJSONObject(i).getJSONObject("friend").getJSONObject("info");
                                json1.put("itemType", "Friend");
                                json1.put("username", jsonItems.getJSONObject(i).getJSONObject("friend").getString("username"));
                                json1.put("_id", jsonItems.getJSONObject(i).getJSONObject("friend").getString("_id"));
                                json1.put("code", jsonItems.getJSONObject(i).getString("friendship_code"));
                                json1.put("invitation", 0);
                                finalArray.put(json1);
                            }*/

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItemsR = json.getJSONObject("response");
                            JSONArray jsonItems = jsonItemsR.getJSONArray("items");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                jsonItems.getJSONObject(i).put("itemType", "Friend");
                                jsonItems.getJSONObject(i).put("invitation", 0);
                                jsonItems.getJSONObject(i).put("image", jsonItems.getJSONObject(i).getJSONObject("info").getString("image"));
                            }

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getFriends(user_id, search, page_number, page_size, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getFriendsInvitation(final String user_id, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                getFriendsInvitations(user_id, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            /*JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItemsR = json.getJSONObject("response");
                            JSONArray jsonItems = jsonItemsR.getJSONArray("items");
                            JSONArray finalArray = new JSONArray();
                            for (int i = 0; i < jsonItems.length(); i++) {
                                JSONObject json1 = jsonItems.getJSONObject(i).getJSONObject("friend").getJSONObject("info");
                                json1.put("itemType", "Friend");
                                json1.put("username", jsonItems.getJSONObject(i).getJSONObject("friend").getString("username"));
                                json1.put("_id", jsonItems.getJSONObject(i).getJSONObject("friend").getString("_id"));
                                json1.put("code", jsonItems.getJSONObject(i).getString("friendship_code"));
                                json1.put("invitation", 1);
                                finalArray.put(json1);
                            }*/

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItemsR = json.getJSONObject("response");
                            JSONArray jsonItems = jsonItemsR.getJSONArray("items");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                jsonItems.getJSONObject(i).put("itemType", "Friend");
                                jsonItems.getJSONObject(i).put("invitation", 1);
                                jsonItems.getJSONObject(i).put("image", jsonItems.getJSONObject(i).getJSONObject("info").getString("image"));
                            }

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getFriendsInvitation(user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getFriendsAvailable(final String path, final String user_id, final String search, final int page_number, final int page_size, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                getFriendsAvailable(path, user_id, search, page_number, page_size, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItemsR = json.getJSONObject("response");
                            JSONArray jsonItems = jsonItemsR.getJSONArray("items");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                jsonItems.getJSONObject(i).put("itemType", "Friend");
                                jsonItems.getJSONObject(i).put("invitation", 2);
                                jsonItems.getJSONObject(i).put("image", jsonItems.getJSONObject(i).getJSONObject("info").getString("image"));
                            }

                            arrayItems = new ItemParse().convertJson(jsonItems);
                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getFriendsAvailable(path, user_id, search, page_number, page_size, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getFriendsCount(final String user_id, final HooptapCallback<Integer> callback) {

        Hooptap.getClient().
                getFriendsCount(user_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            int count = json.getInt("response");
                            callback.onSuccess(count);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getFriendsCount(user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void createRequest(final String user_id, final String friend_id, final HooptapCallback<JSONArray> callback) {

        Hooptap.getClient().
                createRequest(user_id, friend_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONArray jsonItems = json.getJSONArray("response");
                            callback.onSuccess(jsonItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            createRequest(user_id, friend_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void acceptRequest(final String user_id, final String friend_id, final String friendship_code, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                acceptRequest(user_id, friend_id, friendship_code, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItems = json.getJSONObject("response");
                            callback.onSuccess(jsonItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            acceptRequest(user_id, friend_id, friendship_code, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void deleteRequest(final String user_id, final String friend_id, final String friendship_code, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                deleteRequest(user_id, friend_id, friendship_code, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItems = json.getJSONObject("response");
                            callback.onSuccess(jsonItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            deleteRequest(user_id, friend_id, friendship_code, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void deleteFriend(final String user_id, final String friend_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                friendDelete(user_id, friend_id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItems = json.getJSONObject("response");
                            callback.onSuccess(jsonItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            deleteFriend(user_id, friend_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }
    public static void registrarC2DM(final String user_id, final String deviceToken, final HooptapCallback<Boolean> callback) {

        Hooptap.getClient().
                registerC2DM(user_id, "android", deviceToken, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            callback.onSuccess(json.getBoolean("subscribed"));

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            registrarC2DM(user_id, deviceToken, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }
    public static void getNotifications(final int  page, final int limit, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().notificationsPage("me", page, limit, new Callback<Response>() {
            private ArrayList<HooptapItem> arrayItems = new ArrayList<HooptapItem>();

            @Override
            public void success(Response result, Response response) {
                try {


                    JSONObject json = generateJsonToResponse(response);
                    JSONObject jsonItemsR = json.getJSONObject("response");
                    JSONArray jsonItems = jsonItemsR.getJSONArray("items");
                    arrayItems = new ItemParse().convertJson(jsonItems);
                    callback.onSuccess(arrayItems);

                } catch (Exception e) {
                    callback.onError(generateError(response));
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retry < 1) {
                    retry++;
                    getNotifications(page, limit, callback);
                } else
                    callback.onError(generateError(retrofitError.getResponse()));
            }
        });

    }
    public static void activeNotifications(final String id_item,  final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                readNotification("me", id_item, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItems = json.getJSONObject("response");
                            callback.onSuccess(jsonItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            activeNotifications(id_item, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void getCountNotification(final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                countNotification("me", new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonItems = json.getJSONObject("response");
                            Log.e("numeroNotifi","valor de respuest."+response);
                            callback.onSuccess(jsonItems);

                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getCountNotification( callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }
    public static void getCountNotificationNew(final Callback callback){
        Hooptap.getClient().countNotification("me", new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                callback.success(response,response2);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retry < 1) {
                    retry++;
                    getCountNotificationNew(callback);
                }

            }
        });
    }

}

