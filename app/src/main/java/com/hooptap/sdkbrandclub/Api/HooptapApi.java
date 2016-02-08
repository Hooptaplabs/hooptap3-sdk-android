package com.hooptap.sdkbrandclub.Api;

import android.graphics.Bitmap;
import android.util.Log;

import com.hooptap.brandclub.model.FileModel;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Interfaces.AsyncResponse;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Models.Options;
import com.hooptap.sdkbrandclub.Models.RegisterModel;
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

        new Command("userUserIdNotificationNotificationIdGet", data, cb, cbRetry).executeMethod();

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

        new Command("userUserIdActionActionNamePost", data, cb, cbRetry).executeMethod();

    }

    public static void getActions(final Options options, final HooptapCallback<ArrayList<String>> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize()+"");
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

        new Command("engineActionGet", data, null, cbRetry).executeMethod(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                cb.onSuccess(ParseActions.actions((JSONObject) Utils.getObjectParse(output)));
            }
        });

    }

    public static void getMatchingFieldsForAction(final String action, final Options options, final HooptapCallback<HashMap<String, String>> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize()+"");
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

        new Command("engineActionGet", data, null, cbRetry).executeMethod(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                cb.onSuccess(ParseActions.matchingFieldsForAction((JSONObject) Utils.getObjectParse(output), action));
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
        data.put("good_id", good_id);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                buyGood(user_id, good_id, cb);
            }
        };

        new Command("userUserIdMarketplaceGoodGoodIdPost", data, cb, cbRetry).executeMethod();
    }

    /**
     * BADGES
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getBadges(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

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
        new Command("userUserIdBadgesGet", data, cb, cbRetry).executeMethod();
    }

    /**
     * PROFILE USER
     *
     * @param user_id Identificador del usuario
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getProfile(final String user_id, final HooptapCallback<JSONObject> cb) {

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

        new Command("userUserIdGet", data, cb, cbRetry).executeMethod();
    }

    /**
     * ITEM DETAIL
     *
     * @param user_id Identificador del usuario
     * @param item_id Identificador del item
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getItemDetail(final String user_id, final String item_id, final HooptapCallback<JSONObject> cb) {

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

        new Command("userUserIdItemItemIdGet", data, cb, cbRetry).executeMethod();

    }

    /**
     * ITEMS
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getItems(final String user_id, final Options options, final HooptapCallback<JSONObject> cb) {

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

        new Command("userUserIdItemGet", data, cb, cbRetry).executeMethod();

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
                getItems(user_id, options, cb);
            }
        };

        new Command("userUserIdLevelGet", data, cb, cbRetry).executeMethod();
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

        new Command("userUserIdRewardRewardIdLevelGet", data, cb, cbRetry).executeMethod();

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
        data.put("page_size", options.getPageSize()+"");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber()+"");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getMarketPlace(user_id, options, cb);
            }
        };

        new Command("userUserIdMarketplaceGoodGet", data, cb, cbRetry).executeMethod();

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
        data.put("page_size", options.getPageSize()+"");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber()+"");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getGoods(user_id, options, cb);
            }
        };

        new Command("userUserIdMarketplacePurchaseGet", data, cb, cbRetry).executeMethod();
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

        new Command("userUserIdPointsGet", data, cb, cbRetry).executeMethod();
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
        data.put("page_size", options.getPageSize()+"");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber()+"");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getNotifications(user_id, options, cb);
            }
        };

        new Command("userUserIdNotificationGet", data, cb, cbRetry).executeMethod();
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

        new Command("userUserIdItemItemIdGet", data, cb, cbRetry).executeMethod();
    }

    /**
     * USERS
     *
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getUsers(final Options options, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", options.getPageSize()+"");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber()+"");
        data.put("filter", "");
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getUsers(options, cb);
            }
        };

        new Command("userGet", data, cb, cbRetry).executeMethod();
    }

    /**
     * RANKING
     *
     * @param user_id Identificador del usuario
     * @param options Opciones de configuracion
     * @param cb      Callback que recibira la informacion de la peticion
     */
    public static void getRanking(final String user_id, final Options options, final String ranking_filter, final HooptapCallback<JSONObject> cb) {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("user_id", user_id);
        data.put("page_size", options.getPageSize()+"");
        data.put("api_key", Hooptap.getApiKey());
        data.put("page_number", options.getPageNumber()+"");
        data.put("filter", ranking_filter);
        data.put("token", Hooptap.getToken());

        HooptapCallbackRetry cbRetry = new HooptapCallbackRetry() {
            @Override
            public void retry() {
                getRanking(user_id, options, ranking_filter, cb);
            }
        };

        new Command("userUserIdRankingGet", data, cb, cbRetry).executeMethod();

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

        new Command("userUserIdFeedGet", data, cb, cbRetry).executeMethod();

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

        new Command("userUserIdRewardCountGet", data, cb, cbRetry).executeMethod();
    }

    /**
     * FEED
     *
     * @param email    Email del usuario
     * @param password Password del usuario
     * @param cb       Callback que recibira la informacion de la peticion
     */
    public static void login(final String email, final String password, final HooptapCallback<JSONObject> cb) {

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

        new Command("userLoginPost", data, cb, cbRetry).executeMethod(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                Utils.setToken(output);
            }
        });

    }


    /**
     * Registrar C2DM
     *//*
    public static void registrarC2DM(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command( "registrarC2DM", datos, cb).executeMethod();
    }
*/
    /*
    public static void renewToken(String user_id,String oldToken, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = oldToken;
        new Command( "renewToken", datos, cb).executeMethod();
    }*/

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

        new Command("userPost", data, cb, cbRetry).executeMethod();

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

        new Command("fileBase64Post", data, cb, cbRetry).executeMethod();

    }


}