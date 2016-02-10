package com.hooptap.sdkbrandclub.Api;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.hooptap.brandclub.model.FileModel;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapRanking;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
import com.hooptap.sdkbrandclub.Models.Options;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;
import com.hooptap.sdkbrandclub.Models.RegisterModel;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Constants;
import com.hooptap.sdkbrandclub.Utilities.ParseActions;
import com.hooptap.sdkbrandclub.Utilities.Utils;

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
     * Activar Notificaciones
     *
     * @param user_id         Identificador del usuario
     * @param notification_id Identificador de notificaciones
     * @param cb              Callback que recibira la informacion de la peticion
     */
    public static void activeNotifications(final String user_id, final String notification_id, final HooptapCallback<JSONObject> cb) {

        Hooptap.getClient().userUserIdNotificationNotificationIdGet(Hooptap.getApiKey(), user_id, notification_id, Hooptap.getToken());

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("notification_id", notification_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                activeNotifications(user_id, notification_id, cb);
            }
        };

        new Command("userUserIdNotificationNotificationIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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

    public static void getActions(final Options options, final HooptapCallback<ArrayList<String>> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        Log.e("APIKEY", Hooptap.getApiKey() + " / " + Hooptap.getToken() + " / " + options.getPageSize() + " / " + options.getPageNumber() + "");
        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getActions(options, cb);
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

    public static void getMatchingFieldsForAction(final String action, final Options options, final HooptapCallback<HashMap<String, String>> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getMatchingFieldsForAction(action, options, cb);
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
     * Buy Good
     *
     * @param user_id Identificador del usuario
     * @param good_id Identificador de good
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void buyGood(final String user_id, final String good_id, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("api_key", Hooptap.getApiKey());
        data.put("token", Hooptap.getToken());
        data.put("good_id", good_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                buyGood(user_id, good_id, cb);
            }
        };

        new Command("userUserIdMarketplaceGoodGoodIdPost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
     * BADGES
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getBadges(final String user_id, final Options options, final HooptapCallback<HooptapListResponse> cb) {

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
    public static void getItems(final String user_id, final Options options, final HooptapCallback<HooptapListResponse> cb) {

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
                    HooptapListResponse htResponse = ParseObjects.getObjectParse(jsonResponse, options);
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
     * LEVELS
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getLevels(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getLevels(user_id, options, cb);
            }
        };

        new Command("userUserIdLevelGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
     * MARKETPLACE
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getMarketPlace(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getMarketPlace(user_id, options, cb);
            }
        };

        new Command("userUserIdMarketplaceGoodGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
     * MY GOODS
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getGoods(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getGoods(user_id, options, cb);
            }
        };

        new Command("userUserIdMarketplacePurchaseGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
    public static void getPoints(final String user_id, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("token", Hooptap.getToken());
        data.put("api_key", Hooptap.getApiKey());
        data.put("user_id", user_id);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getPoints(user_id, cb);
            }
        };

        new Command("userUserIdPointsGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
     * MY GOODS
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getNotifications(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getNotifications(user_id, options, cb);
            }
        };

        new Command("userUserIdNotificationGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
     * URL GAME
     *
     * @param user_id Identificador del usuario
     * @param game_id Identificador del game
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getUrlGame(final String user_id, final String game_id, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("api_key", Hooptap.getApiKey());
        data.put("game_id", game_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUrlGame(user_id, game_id, cb);
            }
        };

        new Command("userUserIdItemItemIdGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
     * USERS
     *
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getUsers(final Options options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUsers(options, cb);
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
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getRankingDetail(final String ranking_id, final Options options, final HooptapCallback<HooptapRanking> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", ranking_id);
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRankingDetail(ranking_id, options, cb);
            }
        };

        new Command("userUserIdRankingGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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
    public static void getRankingList(final Options options, final HooptapCallback<HooptapListResponse> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize() + "");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber() + "");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRankingList(options, cb);
            }
        };

        new Command("rankingGet", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.getObjectJson(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.LIST, Constants.RANKING);
                HooptapListResponse listResponse= ParseObjects.getObjectParse(jsonResponse, options);
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
    public static void getUserFeed(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

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

    /**
     * REGISTER USER
     *
     * @param info_User Informacion del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     */
    public static void registerUser(final RegisterModel info_User, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        data.put("info_user", info_User);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                registerUser(info_User, cb);
            }
        };

        new Command("userPost", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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

    public static void uploadImage(final Bitmap imagen, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", Hooptap.getApiKey());
        FileModel fileModel = new FileModel();
        fileModel.setFile(Utils.parseImageToString(imagen));
        data.put("fileModel", fileModel);

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                uploadImage(imagen, cb);
            }
        };

        new Command("fileBase64Post", data, cbRetry).executeMethod(new HooptapCallback<Object>() {
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

//    private static JSONObject addValueToJson(String key, Object value, JSONObject response) {
//        try {
//            response.put(key, value);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }

    private static OptionsMapper setClassAndSubClasForMapper(String className, String subClassName ){
        OptionsMapper options = new OptionsMapper();
        options.setClassName(className);
        options.setSubClassName(subClassName);

        return options;
    }

    private static OptionsMapper setClassAndSubClasForMapper(String className){
        OptionsMapper options = new OptionsMapper();
        options.setClassName(className);
        options.setSubClassName("");

        return options;
    }

}