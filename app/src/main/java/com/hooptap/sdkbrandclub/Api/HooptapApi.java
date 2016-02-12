package com.hooptap.sdkbrandclub.Api;

import android.util.Log;

import com.google.gson.Gson;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.HooptapFilter;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapPoint;
import com.hooptap.sdkbrandclub.Models.HooptapRanking;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Models.HooptapRegister;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
import com.hooptap.sdkbrandclub.Models.HooptapOptions;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Constants;
import com.hooptap.sdkbrandclub.Utilities.ParseActions;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Filter;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApi {


    /**
     * REGISTER USER
     *
     * @param info_user Informacion del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     */
    public static void registerUser(final HooptapRegister info_user, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("info_user", info_user);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                registerUser(info_user, cb);
            }
        };

        new Command("userPost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                Utils.setToken(output);

                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.USER);
                HooptapUser user = ParseObjects.getObjectParse(jsonResponse, options);

                cb.onSuccess(user);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * LOGIN
     *
     * @param email    Email del usuario
     * @param password Password del usuario
     * @param cb       Callback que recibira la informacion de la peticion
     */
    public static void login(final String email, final String password, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        InputLoginModel info = new InputLoginModel();
        info.setEmail(email);
        info.setPassword(password);
        data.put("info", info);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                login(email, password, cb);
            }
        };

        new Command("userLoginPost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                Utils.setToken(output);

                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.USER);
                HooptapUser user = ParseObjects.getObjectParse(jsonResponse, options);

                cb.onSuccess(user);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * ACCIONES
     *
     * @param user_id          Identificador del usuario
     * @param interaction_data Informacion de la accion
     * @param accion           Nombre de la accion
     * @param cb               Callback que recibira la informacion de la peticion
     */
    public static void doAction(final String user_id, final String interaction_data, final String accion, final HooptapCallback<JSONObject> cb) {

        HooptapAccion interaction = new HooptapAccion();
        interaction.setActionData(interaction_data);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("accion", accion);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("interaction_data", interaction);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                doAction(user_id, interaction_data, accion, cb);
            }
        };

        new Command("userUserIdActionActionNamePost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess((JSONObject) ParseObjects.getObjectJson(output));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    public static void getActions(final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<ArrayList<String>> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());

        Log.e("APIKEY", Hooptap.getApiKey() + " / " + Hooptap.getToken() + " / " + options.getPageSize() + " / " + options.getPageNumber() + "");
        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getActions(options, filter, cb);
            }
        };

        new Command("engineActionGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess(ParseActions.actions((JSONObject) ParseObjects.getObjectJson(output)));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    public static void getMatchingFieldsForAction(final String action, final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<HashMap<String, String>> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.filterToString());
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getMatchingFieldsForAction(action, options, filter, cb);
            }
        };

        new Command("engineActionGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess(ParseActions.matchingFieldsForAction((JSONObject) ParseObjects.getObjectJson(output), action));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * BADGES
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getBadges(final String user_id, final HooptapOptions options, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());
        //data.put("options", options);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getBadges(user_id, options, cb);
            }
        };
        new Command("userUserIdBadgesGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                //FIX PACO - Cambiar cuando este paginado
                String value = new Gson().toJson(output);
                try {
                    JSONObject json = new JSONObject(value);
                    json.put("items", json.remove("response"));
                    OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.BADGE);
                    HooptapListResponse htResponse = ParseObjects.getObjectParse(json, options);
                    cb.onSuccess(htResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }

    /**
     * PROFILE USER
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getProfile(final String user_id, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getProfile(user_id, cb);
            }
        };

        new Command("userUserIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.USER);
                HooptapUser user = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(user);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }

    /**
     * ITEM DETAIL
     *
     * @param user_id Identificador del usuario
     * @param item_id Identificador del item
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getItemDetail(final String user_id, final String item_id, final HooptapCallback<HooptapItem> cb) {

        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("api_key", Hooptap.getApiKey());
        data.put("item_id", item_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getItemDetail(user_id, item_id, cb);
            }
        };

        new Command("userUserIdItemItemIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                try {

                    JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                    OptionsMapper options = setClassAndSubClasForMapper(jsonResponse.getString("itemType"));
                    HooptapItem item = ParseObjects.getObjectParse(jsonResponse, options);
                    cb.onSuccess(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * ITEMS
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getItems(final String user_id, final HooptapOptions options, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());
        //data.put("options", options);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getItems(user_id, options, cb);
            }
        };

        new Command("userUserIdItemGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                try {
                    JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                    OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST);
                    HooptapListResponse htListResponse = ParseObjects.getObjectParse(jsonResponse, options);
                    cb.onSuccess(htListResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }


    /**
     * USER LEVEL
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getLevelDetail(final String user_id, final HooptapCallback<HooptapLevel> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("userId", user_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getLevelDetail(user_id, cb);
            }
        };

        new Command("userUserIdLevelGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                try {
                    JSONObject jsonCurrent = jsonResponse.getJSONObject("current");
                    OptionsMapper options = setClassAndSubClasForMapper(Constants.LEVEL);
                    HooptapLevel level = ParseObjects.getObjectParse(jsonCurrent, options);
                    cb.onSuccess(level);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }

    /**
     * LEVELS
     *
     * @param options Pagination options
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getLevels(final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.filterToString());
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getLevels(options, filter, cb);
            }
        };

        new Command("levelGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.LEVEL);
                HooptapListResponse htListResponse = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(htListResponse);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }

    /**
     * REWARDS
     *
     * @param user_id   Identificador del usuario
     * @param reward_id Identificador del reward
     * @param cb        Callback que recibira la informacion de la peticion
     */
    public static void getLevelReward(final String user_id, final String reward_id, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("reward_id", reward_id);
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());
        data.put("api_key", Hooptap.getApiKey());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getLevelReward(user_id, reward_id, cb);
            }
        };

        new Command("userUserIdRewardRewardIdLevelGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess((JSONObject) ParseObjects.getObjectJson(output));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * MY POINTS
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getPoints(final String user_id, final HooptapCallback<HooptapPoint> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getPoints(user_id, cb);
            }
        };

        new Command("userUserIdPointsGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.POINT);
                HooptapPoint point = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(point);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }


    /**
     * USERS
     *
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getUsers(final HooptapOptions options, final Filter filter, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUsers(options, filter, cb);
            }
        };

        new Command("userGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess((JSONObject) ParseObjects.getObjectJson(output));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }

    /**
     * RANKING DETAIL
     *
     * @param ranking_id Identificador del ranking
     * @param options    Opciones de configuracion
     * @param cb         Callback que recibira la informacion de la peticion
     */
    public static void getRankingDetail(final String user_id, final String ranking_id, final HooptapOptions options, final HooptapCallback<HooptapRanking> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("userId", user_id);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", ranking_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRankingDetail(user_id, ranking_id, options, cb);
            }
        };

        new Command("userUserIdRankingIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.RANKING);
                HooptapRanking rankingDetail = ParseObjects.getObjectParse(jsonResponse, options);

                cb.onSuccess(rankingDetail);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * RANKING LIST
     *
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getRankingList(final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRankingList(options, filter, cb);
            }
        };

        new Command("rankingGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.RANKING);
                HooptapListResponse listResponse = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(listResponse);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }

    /**
     * FEED
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getUserFeed(final String user_id, final HooptapOptions options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUserFeed(user_id, options, cb);
            }
        };

        new Command("userUserIdFeedGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess((JSONObject) ParseObjects.getObjectJson(output));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * REWARD COUNT
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getRewardsCount(final String user_id, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRewardsCount(user_id, cb);
            }
        };

        new Command("userUserIdRewardCountGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                cb.onSuccess((JSONObject) ParseObjects.getObjectJson(output));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });
    }


    private static OptionsMapper setClassAndSubClasForMapper(String className, String subClassName) {
        OptionsMapper options = new OptionsMapper();
        options.setClassName(className);
        options.setSubClassName(subClassName);

        return options;
    }

    private static OptionsMapper setClassAndSubClasForMapper(String className) {
        OptionsMapper options = new OptionsMapper();
        options.setClassName(className);
        options.setSubClassName("");

        return options;
    }

}