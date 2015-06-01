package com.hooptap.sdkgameroom.Api;

import android.util.Log;

import com.hooptap.a.Callback;
import com.hooptap.a.RetrofitError;
import com.hooptap.a.client.Response;
import com.hooptap.a.mime.TypedInput;
import com.hooptap.sdkgameroom.Models.HooptapGameStatus;
import com.hooptap.sdkgameroom.Models.HooptapItem;
import com.hooptap.sdkgameroom.Models.ResponseError;
import com.hooptap.sdkgameroom.Engine.ItemParse;
import com.hooptap.sdkgameroom.Engine.ItemParseFolder;
import com.hooptap.sdkgameroom.Interfaces.HooptapCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApi {
    //Primer commit Development
    private static int retry = 0;

    private static JSONObject generateJsonToResponse(Response response) throws Exception {
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

    /**
     * Metodo para obtener una lista de juegos
     *
     * @param id       Identificador del recurso
     * @param callback Callback donde recebiremos el resultado
     */
    public static void getListItemsForId(final String path, final String id, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                getListGame(path, id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        ArrayList<HooptapItem> arrayItems;

                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            JSONArray jsonItems = genericJson.getJSONArray("items");

                            arrayItems = new ItemParse().convertJson(jsonItems);

                            callback.onSuccess(arrayItems);

                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getListItemsForId(path, id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));

                    }
                });
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

    /**
     * Metodo para obtener la url de un determinado juego
     *
     * @param id       Identificador del juego
     * @param callback Callback donde recebiremos el resultado
     */
    public static void getGameDetail(final String id, final HooptapCallback<HooptapItem> callback) {

        Hooptap.getClient().
                getList(id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonGame = json.getJSONObject("response");

                            HooptapItem item =new ItemParseFolder().convertJson(jsonGame);

                            callback.onSuccess(item);

                        } catch (Exception e) {
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getGameDetail(id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });
    }

    private static ResponseError generateError(Response error) {
        ResponseError newError = new ResponseError(error);
        try {
            JSONObject jsonerror = generateJsonToResponse(error);
            newError.setReason(jsonerror.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newError;
    }

    public static void play(final String path,final String itemId, final String puntuation, final HooptapCallback<HooptapGameStatus> callback) {

        Hooptap.getClient().
                play(path, itemId, puntuation, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonStatus = json.getJSONObject("response");
                            Log.e("PLAY", json + " / " + jsonStatus);
                            HooptapGameStatus status = new HooptapGameStatus();
                            status.setResult(jsonStatus.getBoolean("result"));
                            status.setRewards(jsonStatus.getJSONArray("rewards"));
                            callback.onSuccess(status);
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

    public static void getConfig(final String path, final HooptapCallback<JSONObject> callback) {
        Log.e("CONFIG",path+" /");
        Hooptap.getClient().
                getConfig(path, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {
                            JSONObject json = generateJsonToResponse(response);
                            callback.onSuccess(json);

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
        }catch(Exception e){e.printStackTrace();}

        return null;
    }

    public static void redem(final String path, final String id, final String name, final String email, final HooptapCallback<Boolean> callback) {

        Hooptap.getClient().
                redem(path, id, name, email, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        callback.onSuccess(true);
                    }

                    @Override
                    public void failure(final RetrofitError retrofitError) {

                        if (retry < 1) {
                            retry++;
                            redem(path, id, name, email, callback);
                        } else
                            callback.onSuccess(false);

                    }
                });

    }

    public static void register(final String username, final String password, final String email,
                         final String birth,  final String lang, final int gender,
                                final HooptapCallback<JSONObject> callback){

        Hooptap.getClient().
            register(username, password, email, birth, lang, gender, new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {
                    try {
                        JSONObject json = generateJsonToResponse(response);
                        callback.onSuccess(json);
                    } catch (Exception e) {
                        callback.onError(new ResponseError(response));
                    }
                }

                @Override
                public void failure(final RetrofitError retrofitError) {
                    if (retry < 1) {
                        retry++;
                        register(username, password, email, birth, lang, gender, callback);
                    } else
                        callback.onError(generateError(retrofitError.getResponse()));
                }
            });

    }


}

