package com.hooptap.sdkbrandclub.Api;

import android.util.Log;

import com.hooptap.brandclub.model.InputActionDoneModel;
import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.HooptapActionResult;
import com.hooptap.sdkbrandclub.Models.HooptapFilter;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapLogin;
import com.hooptap.sdkbrandclub.Models.HooptapOptions;
import com.hooptap.sdkbrandclub.Models.HooptapPoint;
import com.hooptap.sdkbrandclub.Models.HooptapRanking;
import com.hooptap.sdkbrandclub.Models.HooptapRegister;
import com.hooptap.sdkbrandclub.Models.HooptapUserUpdate;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
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
        Log.e("APIKEY", Hooptap.getApiKey() + " /");
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

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
     * UPDATE USER
     *
     * @param info_user Informacion del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     */
    public static void updateUser(final String user_id, final HooptapUserUpdate info_user, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);
        data.put("info_user", info_user);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                updateUser(user_id, info_user, cb);
            }
        };

        new Command("userIdPut", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                Utils.setToken(output);

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
     * @param userLogin Modelo de usuario para loguear
     * @param cb       Callback que recibira la informacion de la peticion
     */
    public static void login(final HooptapLogin userLogin, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("info", userLogin);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                login(userLogin, cb);
            }
        };

        new Command("userLoginPost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                Utils.setToken(output);

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
    public static void doAction(final String user_id, final JSONObject interaction_data, final String accion, final HooptapCallback<HooptapActionResult> cb) {

        InputActionDoneModel interaction = new InputActionDoneModel();
        interaction.setActionData(interaction_data.toString());

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("actionName", accion);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);
        data.put("actionData", interaction);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                doAction(user_id, interaction_data, accion, cb);
            }
        };

        new Command("userIdActionActionNamePost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.ACTION);
                HooptapActionResult action = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(action);
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
                cb.onSuccess(ParseActions.actions((JSONObject) ParseObjects.convertObjectToJsonResponse(output)));
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
                cb.onSuccess(ParseActions.matchingFieldsForAction((JSONObject) ParseObjects.convertObjectToJsonResponse(output), action));
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });

    }

    /**
     * USER QUESTS
     *
     * @param options    Opciones de configuracion
     * @param cb         Callback que recibira la informacion de la peticion
     */
    public static void getUserQuests(final String user_id, final HooptapOptions options, final HooptapFilter filter,
                                     final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("token", Hooptap.getToken());
        data.put("filter", filter.toString());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUserQuests(user_id, options, filter, cb);
            }
        };

        new Command("userIdQuestGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.QUEST);
                HooptapListResponse listQuest = ParseObjects.getObjectParse(jsonResponse, options);

                cb.onSuccess(listQuest);
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
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getBadges(user_id, options, cb);
            }
        };
        new Command("userIdBadgesGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.BADGE);
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
     * PROFILE USER
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getProfile(final String user_id, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getProfile(user_id, cb);
            }
        };

        new Command("userIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
        data.put("api_key", Hooptap.getApiKey());
        data.put("item_id", item_id);
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getItemDetail(user_id, item_id, cb);
            }
        };

        new Command("userIdItemItemIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                try {
                    JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getItems(user_id, options, cb);
            }
        };

        new Command("userIdItemGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST);
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
     * USER CURRENT LEVEL
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getCurrentLevelDetail(final String user_id, final HooptapCallback<HooptapLevel> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getCurrentLevelDetail(user_id, cb);
            }
        };

        new Command("userIdLevelGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
     * USER NEXT LEVEL
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getNextLevelDetail(final String user_id, final HooptapCallback<HooptapLevel> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getNextLevelDetail(user_id, cb);
            }
        };

        new Command("userIdLevelGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                try {
                    if (!jsonResponse.isNull("next")){
                        JSONObject jsonCurrent = jsonResponse.getJSONObject("next");
                        OptionsMapper options = setClassAndSubClasForMapper(Constants.LEVEL);
                        HooptapLevel level = ParseObjects.getObjectParse(jsonCurrent, options);
                        cb.onSuccess(level);
                    }else{
                        ResponseError error = new ResponseError();
                        error.setReason("You have reach the max level");
                        cb.onError(error);
                    }
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
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
     * MY POINTS
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getPoints(final String user_id, final HooptapCallback<HooptapPoint> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getPoints(user_id, cb);
            }
        };

        new Command("userIdPointsGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
     * RANKING USERS
     *
     * @param ranking_id Identificador del ranking
     * @param options    Opciones de configuracion
     * @param cb         Callback que recibira la informacion de la peticion
     */
    public static void getRankingUsers(final String user_id, final String ranking_id, final HooptapOptions options,
                                        final HooptapFilter filter, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("rankingId", ranking_id);
        data.put("page_number", options.getPageNumber() + "");
        data.put("token", Hooptap.getToken());
        data.put("filter", filter.toString());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRankingUsers(user_id, ranking_id, options, filter, cb);
            }
        };

        new Command("userIdRankingRankingIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.USER_RANKING);
                HooptapListResponse listUSers = ParseObjects.getObjectParse(jsonResponse, options);

                cb.onSuccess(listUSers);
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
     * @param cb         Callback que recibira la informacion de la peticion
     */
    public static void getRankingDetail(final String ranking_id, final HooptapCallback<HooptapRanking> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("rankingId", ranking_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRankingDetail(ranking_id, cb);
            }
        };

        new Command("rankingIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
    public static void getRankingList(final HooptapOptions options, final HooptapFilter filter,
                                      final HooptapCallback<HooptapListResponse> cb) {

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
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
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
    public static void getUserFeed(final String user_id, final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUserFeed(user_id, options, filter, cb);
            }
        };

        new Command("userIdFeedGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.REWARD);
                HooptapListResponse listResponse = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(listResponse);
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