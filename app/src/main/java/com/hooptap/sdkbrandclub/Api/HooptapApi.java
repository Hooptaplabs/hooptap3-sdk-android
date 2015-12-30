package com.hooptap.sdkbrandclub.Api;

import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.d.Gson;
import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.RegisterModel;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApi {

    static ApiWrapper cliente = new ApiWrapper();

    public static void getLevels(String userId, HooptapCallback<JSONObject> cb) {

        Object[] datos = new Object[1];
        datos[0] = userId;
        new Command(cliente, "getLevels", datos, cb).execute();
    }

    public static void getUsers(HashMap<String, Object> pagination, String filter, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[3];
        checkPagination(pagination);
        datos[0] = pagination.get("size_page");
        datos[1] = pagination.get("page_number");
        datos[2] = filter;
        new Command(cliente, "getUsers", datos, cb).execute();
    }

    private static void checkPagination(HashMap<String, Object> pagination) {
        if (Utils.isEmpty(pagination)) {
            pagination.put("page_number", "1");
            pagination.put("size_page", "100");
        }
    }

    public static void registerUser(RegisterModel info_User, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = info_User;
        new Command(cliente, "registerUser", datos, cb).execute();
    }

    public static void getUserId(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getUserId", datos, cb).execute();
    }

    public static void login(String email, String password, HooptapCallback<JSONObject> cb) {

        InputLoginModel info = new InputLoginModel();
        info.setEmail(email);
        info.setPassword(password);

        Object[] datos = new Object[1];
        datos[0] = info;
        new Command(cliente, "login", datos, cb).execute();

    }

    public static void doAction(String user_id, String target_id, String interaction_data, String accion, HooptapCallback<JSONObject> cb) {

        HooptapAccion interaction = new HooptapAccion();
        interaction.setTarget_id(target_id);
        interaction.setInteraction_data(interaction_data);

        Object[] datos = new Object[3];
        datos[0] = user_id;
        datos[1] = interaction;
        datos[2] = accion;
        new Command(cliente, "doAction", datos, cb).execute();

    }

    public static void getRewardsUser(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getRewardsUser", datos, cb).execute();
    }

    /*public static void changeImage(String user_id, HooptapCallback<JSONObject> cb) {
        JSONObject object = getObjectParse(Hooptap.getClient().userUserIdImagePut(Hooptap.getApiKey(), user_id));
        cb.onSuccess(object);
    }*/

    public static void getRecentActivity(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getRecentActivity", datos, cb).execute();
    }

    public static void getMarketPlace(String user_id, HashMap<String, Object> pagination, String filter, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[4];
        checkPagination(pagination);
        datos[0] = user_id;
        datos[1] = pagination.get("size_page");
        datos[2] = pagination.get("page_number");
        datos[3] = filter + "";
        new Command(cliente, "getMarketPlace", datos, cb).execute();

    }

    public static void getMyMarketPlace(String user_id, HashMap<String, Object> pagination, String filter, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[4];
        checkPagination(pagination);
        datos[0] = user_id;
        datos[1] = pagination.get("size_page");
        datos[2] = pagination.get("page_number");
        datos[3] = filter + "";
        new Command(cliente, "getMyMarketPlace", datos, cb).execute();
    }

    public static void getMyPointsById(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getMyPointsById", datos, cb).execute();
    }


    public static void getLevelReward(String user_id, String reward_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = reward_id;
        new Command(cliente, "getLevelReward", datos, cb).execute();

    }


    public static void getNotifications(String user_id, HashMap<String, Object> pagination, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[3];
        checkPagination(pagination);
        datos[0] = user_id;
        datos[1] = pagination.get("size_page");
        datos[2] = pagination.get("page_number");

        new Command(cliente, "getNotifications", datos, cb).execute();
    }

    public static void activeNotifications(String user_id, String notification_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = notification_id;
        new Command(cliente, "activeNotifications", datos, cb).execute();
    }

    public static void registrarC2DM(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "registrarC2DM", datos, cb).execute();
    }

    public static void getBadges(String user_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[1];
        datos[0] = user_id;
        new Command(cliente, "getBadges", datos, cb).execute();
    }

    public static void getRanking(String user_id, HashMap<String, Object> pagination, String filter, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[4];
        checkPagination(pagination);
        datos[0] = user_id;
        datos[1] = pagination.get("size_page");
        datos[2] = pagination.get("page_number");
        datos[3] = filter + "";
        new Command(cliente, "getRanking", datos, cb).execute();
    }

    public static void buyGood(String user_id, String good_id, HooptapCallback<JSONObject> cb) {
        Object[] datos = new Object[2];
        datos[0] = user_id;
        datos[1] = good_id;
        new Command(cliente, "buyGood", datos, cb).execute();
    }

}