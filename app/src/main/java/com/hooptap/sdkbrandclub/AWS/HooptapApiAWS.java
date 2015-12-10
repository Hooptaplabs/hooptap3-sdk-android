package com.hooptap.sdkbrandclub.AWS;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.brandclub.HooptapAPIvClient;
import com.hooptap.brandclub.model.InputActionDoneModel;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.brandclub.model.UserModel;
import com.hooptap.d.Gson;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.ResponseError;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public abstract class HooptapApiAWS {
    //Primer metodo Prueba
    static Gson g = new Gson();

    public static void getUsers(String size_page, String page_number, String filtro, HooptapCallback<JSONObject> cb) {

        HooptapAWS.getClient().userGet(size_page, HooptapAWS.getApiKey(), page_number, filtro);
        Log.e("getUser", HooptapAWS.getClient().userGet(size_page, HooptapAWS.getApiKey(), page_number, filtro) + "");

        String value = g.toJson(HooptapAWS.getClient().userGet(size_page, HooptapAWS.getApiKey(), page_number, filtro));
        try {
            JSONObject objeto = new JSONObject(value);
            cb.onSuccess(objeto.getJSONObject("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(JSONObject info_User, HooptapCallback<JSONObject> cb) {

        try {
            UserModel modelo = g.fromJson(info_User + "", UserModel.class);
            String value = g.toJson(HooptapAWS.getClient().userPost("46576686f6f707461702e627", modelo));
            JSONObject objeto = new JSONObject(value);
            Log.e("registrar", value);
            cb.onSuccess(objeto.getJSONObject("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ApiClientException ae) {
            Log.e("registrar", ae.getErrorMessage());
            cb.onError(getError(ae));
        }


    }

    private static ResponseError getError(ApiClientException ae) {
        String con = "error:";
        ResponseError responseError = new ResponseError();
        JSONObject error;
        int a = ae.getErrorMessage().indexOf(con);
        try {
            error = new JSONObject(ae.getErrorMessage().substring(a + con.length()));
            responseError.setReason(error.getString("message"));
            responseError.setStatus(error.getInt("httpErrorCode"));
            return responseError;
        } catch (JSONException e) {
            e.printStackTrace();
            responseError.setReason("Se ha producido un error en vuestra llamada");
            return responseError;
        }

    }


    public static void getUserId(String user_id, HooptapCallback<JSONObject> cb) {
        JSONObject object = getObjectParse(HooptapAWS.getClient().userUserIdGet("46576686f6f707461702e627", user_id));

        cb.onSuccess(object);
    }

    public static void login(String email, String password, HooptapCallback<JSONObject> cb) {

        InputLoginModel info = new InputLoginModel();
        info.setEmail(email);
        info.setPassword(password);
        JSONObject object = getObjectParse(HooptapAWS.getClient().userLoginPost(HooptapAWS.getApiKey(), info));
        cb.onSuccess(object);
    }

    private static JSONObject getObjectParse(Object o) {
        String value = g.toJson(o);
        try {
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void doAction(String user_id, String target_id, String interaction_data, String accion, HooptapCallback<JSONObject> cb) {

      /*  InputActionDoneModel accion= new InputActionDoneModel();
        accion.setTargetId("ios");
        accion.setInteractionData("{\"a\":\"b\"}");*/
       /* HooptapAccion accion1=new HooptapAccion();
        accion1.setTarget_id("android");
        accion1.setInteraction_data("{\"a\":\"b\"}");*/
        HooptapAccion accion1 = new HooptapAccion();
        accion1.setTarget_id(target_id);
        accion1.setInteraction_data(interaction_data);
        JSONObject object = getObjectParse(HooptapAWS.getClient().userUserIdActionActionNamePost(HooptapAWS.getApiKey(), user_id, accion, accion1));

        ///JSONObject object=getObjectParse(HooptapAWS.getClient().userUserIdActionActionNamePost(HooptapAWS.getApiKey(),"56547e18c2cd6614312467a5","ShareDetail",accion1));
        cb.onSuccess(object);
        //HooptapAWS.getClient().userUserIdRewardRewardIdGet()//Get user reward
        //HooptapAWS.getClient().userUserIdImagePut() //Cambiar imagen de usuario
        //HooptapAWS.getClient().userUserIdDelete() //borrar usuario
        //HooptapAWS.getClient().userUserIdActionActionNamePost("46576686f6f707461702e627","56547e18c2cd6614312467a5","Play")
    }

    public static void getRewardsUser(String user_id, HooptapCallback<JSONObject> cb) {
        JSONObject object = getObjectParse(HooptapAWS.getClient().userUserIdRewardCountGet(HooptapAWS.getApiKey(), user_id));
        cb.onSuccess(object);
    }

    public static void changeImage(String user_id, HooptapCallback<JSONObject> cb) {
        JSONObject object = getObjectParse(HooptapAWS.getClient().userUserIdImagePut(HooptapAWS.getApiKey(), user_id));
        cb.onSuccess(object);
    }

    public static void getActividadReciente(final String lenguage, final String user_id, final int page, final int limit, HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(HooptapAWS.getClient().userUserIdFeedLangGet(lenguage, user_id, limit + "", HooptapAWS.getApiKey(), page + "", ""));
            Log.e("actividad_reciente", object+"");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("actividad_Error", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }
    public static void getMyMarketPlace( final String user_id, final int page, final int limit, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(HooptapAWS.getClient().userUserIdMarketplaceGoodGet( user_id, limit + "", HooptapAWS.getApiKey(), page + "", ""));
            Log.e("actividad_reciente", object+"");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("actividad_Error", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }
}

