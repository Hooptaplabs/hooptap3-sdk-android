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
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Models.HooptapGameStatus;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Engine.ItemParseFolder;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

                            HooptapItem item = new ItemParseFolder().convertJson(jsonGame);

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

        return newError;
    }

    public static void play(final String path, final String itemId, final String puntuation, final HooptapCallback<HooptapGameStatus> callback) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public static void register(final String path, final String username, final String password, final String email,
                                final String birth, final String lang, final int gender,
                                final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                register(path, username, password, email, birth, lang, gender, new Callback<Response>() {
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
                            register(path, username, password, email, birth, lang, gender, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });

    }

    public static void register2(final String json, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                register2(json, new Callback<Response>() {
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
                            register2(json, callback);
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
                            callback.onSuccess(json);
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
                    callback.onSuccess(json);
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
                            callback.onSuccess(json);
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

    public static void getMyMarketPlace(final String user_id, final HooptapCallback<ArrayList<HooptapItem>> callback) {

        Hooptap.getClient().
                marketPlaceMine(user_id, new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();

                    @Override
                    public void success(Response result, Response response) {
                        try {

                            JSONObject json = generateJsonToResponse(response);
                            JSONArray jsonItems = json.getJSONArray("items");
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
                            getMyMarketPlace(user_id, callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });
    }

    public static void getMarketPlace(final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                marketPlace(new Callback<Response>() {
                    private ArrayList<HooptapItem> arrayItems = new ArrayList<>();

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
                            getMarketPlace(callback);
                        } else
                            callback.onError(generateError(retrofitError.getResponse()));
                    }
                });
    }

    public static void buyGood(final String item_id, final HooptapCallback<JSONObject> callback) {

        Hooptap.getClient().
                buyGood(item_id, new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        try {
                            JSONObject json = generateJsonToResponse(response);
                            callback.onSuccess(json);
                        } catch (Exception e) {
                            callback.onError(generateError(response));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        if (retry < 1) {
                            retry++;
                            buyGood(item_id, callback);
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
                            callback.onSuccess(json);
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
                            callback.onSuccess(json);
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

}

