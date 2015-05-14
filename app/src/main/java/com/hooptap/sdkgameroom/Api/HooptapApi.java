package com.hooptap.sdkgameroom.Api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.hooptap.a.Callback;
import com.hooptap.a.RetrofitError;
import com.hooptap.a.client.Response;
import com.hooptap.a.mime.TypedInput;
import com.hooptap.sdkgameroom.Customs.HooptapGame;
import com.hooptap.sdkgameroom.Customs.HooptapGameStatus;
import com.hooptap.sdkgameroom.Customs.ResponseError;
import com.hooptap.sdkgameroom.Interfaces.HooptapCallback;

import org.json.JSONArray;
import org.json.JSONException;
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
    public static void getListGamesForId(final String id, final HooptapCallback<ArrayList<HooptapGame>> callback) {

        Hooptap.getClient().
                getListGame(id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        ArrayList<HooptapGame> games = new ArrayList<>();
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject genericJson = json.getJSONObject("response");
                            JSONArray jsonItems = genericJson.getJSONArray("items");
                            for (int i = 0; i < jsonItems.length(); i++) {
                                JSONObject jsonGame = jsonItems.getJSONObject(i);
                                HooptapGame game = new HooptapGame();
                                game.setId(jsonGame.getString("_id"));
                                game.setTitle(jsonGame.getString("name"));
                                game.setImage(jsonGame.getString("image"));
                                if (!jsonGame.isNull("url_game"))
                                    game.setUrl(jsonGame.getString("url_game"));
                                games.add(game);
                            }
                            callback.onSuccess(games);

                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError(new ResponseError(response));
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            getListGamesForId(id, callback);
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
    public static void getGameDetail(final String id, final HooptapCallback<HooptapGame> callback) {

        Hooptap.getClient().
                getList(id, new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            JSONObject jsonGame = json.getJSONObject("response");

                            HooptapGame game = new HooptapGame();
                            game.setId(jsonGame.getString("_id"));
                            game.setTitle(jsonGame.getString("name"));
                            game.setImage(jsonGame.getString("image"));
                            if (!jsonGame.isNull("url_game"))
                                game.setUrl(jsonGame.getString("url_game"));

                            callback.onSuccess(game);

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

    public static void play(final String itemId, final String puntuation, final HooptapCallback<HooptapGameStatus> callback) {

        Hooptap.getClient().
                play(itemId, puntuation, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);

                            JSONObject jsonStatus = json.getJSONObject("response");
                            Log.e("PLAY",json+" / "+jsonStatus);
                            HooptapGameStatus status = new HooptapGameStatus();
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
                            play(itemId, puntuation, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));

                    }
                });

    }

    /*public static void register(final String username, final String password, final String email,
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
                        callback.onError(e.getMessage() + "");
                    }
                }

                @Override
                public void failure(final RetrofitError retrofitError) {
                    callback.onError(retrofitError.getMessage()+"");
                }
            });

    }*/


}

