package com.hooptap.sdkbrandclub.Api;

import android.graphics.Bitmap;
import android.util.Log;

import com.hooptap.brandclub.model.FileModel;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.CONSTANTS;
import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.Options;
import com.hooptap.sdkbrandclub.Models.RegisterModel;
import com.hooptap.sdkbrandclub.Utilities.Utils;
import org.json.JSONObject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;



/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApi {


    static ApiWrapper cliente = new ApiWrapper();
    static Command<JSONObject> command;

    /*public static void changeImage(String user_id, HooptapCallback<JSONObject> cb) {
        JSONObject object = getObjectParse(Hooptap.getClient().userUserIdImagePut(Hooptap.getApiKey(), user_id));
        cb.onSuccess(object);
    }*/

    /**
     *          Activar Notificaciones
     * @param user_id           Identificador del usuario
     * @param notification_id   Identificador de notificaciones
     * @param cb                Callback que recibira la informacion de la peticion
     * */
    public static void activeNotifications(String user_id, String notification_id,HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[3];
        datos[0] = user_id;
        datos[1] = notification_id;


        new Command(cliente, "activeNotifications", datos, cb).execute();
    }



    /**
     *                  ACCIONES
     * @param user_id           Identificador del usuario
     * @param interaction_data  Informacion de la accion
     * @param accion            Nombre de la accion
     * @param cb                Callback que recibira la informacion de la peticion
     * */
    public static void doAction(String user_id, String interaction_data, String accion, HooptapCallback<JSONObject> cb) {

        HooptapAccion interaction=new HooptapAccion();
        interaction.setActionData(interaction_data);

        Object[] datos = new Object[3];
        datos[0] = user_id;
        datos[1] = interaction;
        datos[2] = accion;
        new Command(cliente, "doAction", datos, cb).execute();

    }

    /**
     *              Buy Good
     * @param user_id   Identificador del usuario
     * @param good_id   Identificador de good
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void buyGood(String user_id, String good_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = good_id;
        new Command(cliente, "buyGood", datos, cb).execute();
    }

    /**
     *              BADGES
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getBadges(String user_id,Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = options;
        new Command(cliente, "getBadges", datos, cb).execute();
    }

    /**
     *              PROFILE USER
     * @param user_id   Identificador del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getProfile(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getProfile", datos, cb).execute();
    }

    /**
     *              ITEM DETAIL
     * @param user_id   Identificador del usuario
     * @param item_id   Identificador del item
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getItemDetail(String user_id, String item_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = item_id;
        new Command(cliente, "getItemDetail", datos, cb).execute();
    }

    /**
     *              ITEMS
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getItems(String user_id,Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] =options;
        new Command(cliente, "getItems", datos, cb).execute();

    }

    /**
     *              LEVELS
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getLevels(String user_id,Options options, HooptapCallback<JSONObject> cb) {
        Type[] genericInterfaces = cb.getClass().getGenericInterfaces();
        Type[] genericTypes;
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                Log.e("Generic type: ", genericTypes[0] + "");
                for (Type genericType : genericTypes) {
                    Log.e("Generic type: ", genericType + "");
                }
            }
        }
        Log.e("tipo dato", cb.getClass().getName() + "---" + cb.getClass().getCanonicalName() + "---" + cb.getClass().getSimpleName() + "--" + cb.getClass().getGenericSuperclass().getClass() + "--");
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = options;

        new Command<JSONObject>(cliente, "getLevels", datos, cb).execute();
    }

    /**
     *              REWARDS
     * @param user_id       Identificador del usuario
     * @param reward_id     Identificador del reward
     * @param cb            Callback que recibira la informacion de la peticion
     * */
    public static void getLevelReward(String user_id, String reward_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = reward_id;


        new Command(cliente, "getLevelReward", datos, cb).execute();

    }

    /**
     *              MARKETPLACE
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getMarketPlace(String user_id, Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = options;

        new Command(cliente, "getMarketPlace", datos, cb).execute();

    }

    /**
     *           MY GOODS
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getGoods(String user_id, Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = options;
        new Command(cliente, "getGoods", datos, cb).execute();
    }

    /**
     *           MY POINTS
     * @param user_id   Identificador del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getPoints(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getPoints", datos, cb).execute();
    }

    /**
     *           MY GOODS
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getNotifications(String user_id, Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[3];

        datos[0] = user_id;
        datos[1] = options;

        new Command(cliente, "getNotifications", datos, cb).execute();
    }

    /**
     *           URL GAME
     * @param user_id   Identificador del usuario
     * @param game_id   Identificador del game
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getUrlGame(String user_id, String game_id, final HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = game_id;
        new Command(cliente, "getUrlGame", datos, cb).execute();
    }

    /**
     *           USERS
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getUsers(Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = options;

        new Command(cliente, "getUsers", datos, cb).execute();
    }

    /**
     *           RANKING
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getRanking(String user_id, Options options, CONSTANTS.RANKING_FILTER ranking_filter, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[3];
        datos[0] = user_id;
        datos[1] = options;
        datos[2] = Utils.getRankingFilter(ranking_filter);
        new Command(cliente, "getRanking", datos, cb).execute();
    }

    /**
     *           FEED
     * @param user_id   Identificador del usuario
     * @param options   Opciones de configuracion
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getUserFeed(String user_id,Options options, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = options;

        new Command(cliente, "getUserFeed", datos, cb).execute();
    }

    /**
     *           REWARD COUNT
     * @param user_id   Identificador del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void getRewardsCount(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getRewardsCount", datos, cb).execute();
    }

    /**
     *           FEED
     * @param email     Email del usuario
     * @param password  Password del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void login(String email, String password, HooptapCallback<JSONObject> cb) {
        InputLoginModel info = new InputLoginModel();
        info.setEmail(email);
        info.setPassword(password);
        Object[] datos = new Object[1];
        datos[0] = info;
        new Command(cliente, "login", datos, cb).execute();
    }

    /**
     * Registrar C2DM
     *//*
    public static void registrarC2DM(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "registrarC2DM", datos, cb).execute();
    }
*/
    /*
    public static void renewToken(String user_id,String oldToken, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = oldToken;
        new Command(cliente, "renewToken", datos, cb).execute();
    }*/

    /**
     *           REGISTER USER
     * @param info_User Informacion del usuario
     * @param cb        Callback que recibira la informacion de la peticion
     * */
    public static void registerUser(RegisterModel info_User, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = info_User;
        new Command(cliente, "registerUser", datos, cb).execute();
    }

    public static void uploadImage(Bitmap imagen, HooptapCallback<JSONObject>cb){

        Object[] datos = new Object[1];
        FileModel fileModel=new FileModel();
        fileModel.setFile(Utils.parseImageToString(imagen));
        datos[0] = fileModel;
        new Command(cliente, "uploadImage", datos, cb).execute();
    }


}