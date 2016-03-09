package com.hooptap.sdkbrandclub.Api;

import android.util.Log;

import com.hooptap.brandclub.model.InputActionDoneModel;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Engine.TaskCreator;
import com.hooptap.sdkbrandclub.Engine.TaskLauncher;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Interfaces.TaskWrapperInterface;
import com.hooptap.sdkbrandclub.Models.HooptapActionResult;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapFilter;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapLogin;
import com.hooptap.sdkbrandclub.Models.HooptapOptions;
import com.hooptap.sdkbrandclub.Models.HooptapPoint;
import com.hooptap.sdkbrandclub.Models.HooptapQuest;
import com.hooptap.sdkbrandclub.Models.HooptapRanking;
import com.hooptap.sdkbrandclub.Models.HooptapRegister;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
import com.hooptap.sdkbrandclub.Models.HooptapUserUpdate;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Constants;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApi {

    //Nombre de los metodos utilizados por AWS
    private static final String USER_REGISTER_METHODNAME = "userPost";
    private static final String USER_LOGIN_METHODNAME = "userLoginPost";
    private static final String USER_UPDATE_METHODNAME = "userIdPut";
    private static final String ACTION_DO_METHODNAME = "userIdActionActionNamePost";
    private static final String ACTIONS_LIST_METHODNAME = "engineActionGet";
    private static final String QUEST_USER_METHODNAME = "userIdQuestGet";
    private static final String QUEST_DETAIL_METHODNAME = "userIdQuestQuestIdGet";
    private static final String QUEST_ACTIVE_METHODNAME = "userIdQuestQuestIdPost";
    private static final String BADGE_LIST_METHODNAME = "userIdBadgesGet";
    private static final String BADGE_DETAIL_METHODNAME = "rewardIdGet";
    private static final String PROFILE_METHODNAME = "userIdGet";
    private static final String LEVEL_LIST_METHODNAME = "levelGet";
    private static final String LEVEL_DETAIL_METHODNAME = "levelIdGet";
    private static final String LEVEL_CURRENT_METHODNAME = "userIdLevelGet";
    private static final String LEVEL_NEXT_METHODNAME = "userIdLevelGet";
    private static final String POINTS_METHODNAME = "userIdPointsGet";
    private static final String RANKING_USERS_METHODNAME = "userIdRankingRankingIdGet";
    private static final String RANKING_DETAIL = "rankingIdGet";
    private static final String RANKING_LIST = "rankingGet";
    private static final String FEED_METHODNAME = "userIdFeedGet";
    private static final String ITEM_DETAIL_METHODNAME = "userIdItemItemIdGet";
    private static final String ITEM_LIST_METHODNAME = "userIdItemGet";

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

        TaskWrapperInterface wrapperTask = new TaskCreator(USER_REGISTER_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {

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

            @Override
            public void retry() {
                registerUser(info_user, cb);
            }
        });

        new TaskLauncher(wrapperTask).executeTask();

    }

    /**
     * LOGIN
     *
     * @param userLogin Modelo de usuario para loguear
     * @param cb        Callback que recibira la informacion de la peticion
     */
    public static void login(final HooptapLogin userLogin, final HooptapCallback<HooptapUser> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("info", userLogin);

        TaskWrapperInterface wrapperTask = new TaskCreator(USER_LOGIN_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                login(userLogin, cb);
            }
        });

        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(USER_UPDATE_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                updateUser(user_id, info_user, cb);
            }
        });

        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * ACCIONES
     *
     * @param user_id          Identificador del usuario
     * @param interaction_data Informacion de la accion
     * @param accion           Nombre de la accion
     * @param cb               Callback que recibira la informacion de la peticion
     */
    public static void doAction(final String user_id, final JSONObject interaction_data,
                                final String accion, final HooptapCallback<HooptapActionResult> cb) {

        InputActionDoneModel interaction = new InputActionDoneModel();
        interaction.setActionData(interaction_data.toString());

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("actionName", accion);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);
        data.put("actionData", interaction);

        TaskWrapperInterface wrapperTask = new TaskCreator(ACTION_DO_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.ACTION_RESULT);
                HooptapActionResult action = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(action);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                doAction(user_id, interaction_data, accion, cb);
            }
        });

        new TaskLauncher(wrapperTask).executeTask();
    }

    public static void getActions(final HooptapOptions options, final HooptapFilter filter,
                                  final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());

        Log.e("APIKEY", Hooptap.getApiKey() + " / " + Hooptap.getToken() + " / " +
                options.getPageSize() + " / " + options.getPageNumber() + "");

        TaskWrapperInterface wrapperTask = new TaskCreator(ACTIONS_LIST_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                Log.e("getActions", jsonResponse + " /");
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.ACTION);
                HooptapListResponse listActions = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(listActions);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                getActions(options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * USER QUESTS
     *
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getUserQuests(final String user_id, final HooptapOptions options, final HooptapFilter filter,
                                     final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(QUEST_USER_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                Log.e("getUserQuests", jsonResponse + " /");
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.QUEST);
                HooptapListResponse listQuest = ParseObjects.getObjectParse(jsonResponse, options);

                cb.onSuccess(listQuest);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                getUserQuests(user_id, options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * QUEST DETAIL
     *
     * @param quest_id Identificador del usuario
     * @param cb       Callback que recibira la informacion de la peticion
     */
    public static void getQuestDetail(final String quest_id, final String user_id, final HooptapCallback<HooptapQuest> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("questId", quest_id);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(QUEST_DETAIL_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.QUEST);
                HooptapQuest quest = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(quest);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                getQuestDetail(quest_id, user_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * JOIN QUEST
     *
     * @param cb Callback que recibira la informacion de la peticion
     */
    public static void activeUserQuest(final String user_id, final String quest_id,
                                       final HooptapCallback<Boolean> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("questId", quest_id);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(QUEST_ACTIVE_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                boolean active = false;
                try {
                    active = jsonResponse.getBoolean("active");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cb.onSuccess(active);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                activeUserQuest(user_id, quest_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * BADGES
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getBadges(final String user_id, final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.filterToString());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(BADGE_LIST_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getBadges(user_id, options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * BADGE DETAIL
     *
     * @param badge_id Identificador del usuario
     * @param cb       Callback que recibira la informacion de la peticion
     */
    public static void getBadgeDetail(final String badge_id, final HooptapCallback<HooptapBadge> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", badge_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(BADGE_DETAIL_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.BADGE);
                HooptapBadge badge = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(badge);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                getBadgeDetail(badge_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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
        Log.e("USER_ID", user_id);
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(PROFILE_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getProfile(user_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(LEVEL_LIST_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getLevels(options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * USER CURRENT LEVEL
     *
     * @param level_id Identificador del usuario
     * @param cb       Callback que recibira la informacion de la peticion
     */
    public static void getLevelDetail(final String level_id, final HooptapCallback<HooptapLevel> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("id", level_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(LEVEL_DETAIL_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LEVEL);
                HooptapLevel level = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(level);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                getLevelDetail(level_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(LEVEL_CURRENT_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getCurrentLevelDetail(user_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(LEVEL_NEXT_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                try {
                    if (!jsonResponse.isNull("next")) {
                        JSONObject jsonCurrent = jsonResponse.getJSONObject("next");
                        OptionsMapper options = setClassAndSubClasForMapper(Constants.LEVEL);
                        HooptapLevel level = ParseObjects.getObjectParse(jsonCurrent, options);
                        cb.onSuccess(level);
                    } else {
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

            @Override
            public void retry() {
                getNextLevelDetail(user_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(POINTS_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getPoints(user_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(RANKING_LIST, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getRankingList(options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(RANKING_DETAIL, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getRankingDetail(ranking_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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


        TaskWrapperInterface wrapperTask = new TaskCreator(RANKING_USERS_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getRankingUsers(user_id, ranking_id, options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(FEED_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.FEED);
                HooptapListResponse listResponse = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(listResponse);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }

            @Override
            public void retry() {
                getUserFeed(user_id, options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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

        TaskWrapperInterface wrapperTask = new TaskCreator(ITEM_DETAIL_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getItemDetail(user_id, item_id, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
    }

    /**
     * ITEMS
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getItems(final String user_id, final HooptapOptions options, final HooptapFilter filter, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", filter.toString());
        data.put("token", Hooptap.getToken());
        data.put("id", user_id);

        TaskWrapperInterface wrapperTask = new TaskCreator(ITEM_LIST_METHODNAME, data).getWrapperTask(new TaskCallbackWithRetry() {
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

            @Override
            public void retry() {
                getItems(user_id, options, filter, cb);
            }
        });
        new TaskLauncher(wrapperTask).executeTask();
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