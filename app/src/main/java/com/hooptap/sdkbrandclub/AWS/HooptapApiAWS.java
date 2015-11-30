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
    public static void getUsers(String size_page, String page_number,String filtro, HooptapCallback<JSONObject> cb){

        HooptapAWS.getClient().userGet(size_page,HooptapAWS.getApiKey(),page_number,filtro);
        Log.e("getUser", HooptapAWS.getClient().userGet(size_page,HooptapAWS.getApiKey(),page_number,filtro)+"");

        String value=g.toJson(HooptapAWS.getClient().userGet(size_page,HooptapAWS.getApiKey(),page_number,filtro));
        try {
            JSONObject objeto=new JSONObject(value);
            cb.onSuccess(objeto.getJSONObject("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void registerUser(JSONObject info_User,HooptapCallback<JSONObject>cb){

      //Log.e("registrar",value);
        //Toca tratar el error
        try {
            UserModel modelo=g.fromJson(info_User + "", UserModel.class);
            String value=g.toJson(HooptapAWS.getClient().userPost("46576686f6f707461702e627", modelo));

            JSONObject objeto=new JSONObject(value);
            Log.e("registrar",value);
            cb.onSuccess(objeto.getJSONObject("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (ApiClientException ae){
            Log.e("registrar",ae.getErrorMessage());
            Log.e("registrar", g.toJson(ae.getErrorMessage()));

        }

        /*catch (Exception e)
        {
            //cb.onError();
            String object=g.toJson(e.getMessage());
            Log.e("registrarObj",object);
            try {
                JSONObject jsonObject=new JSONObject(object);
                Log.e("registrarObj",jsonObject+"");

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("registrar",e.getMessage());
            Log.e("registrar",e.toString());
        }*/

       /* JSONObject object=getObjectParse(HooptapAWS.getClient().userPost("46576686f6f707461702e627", modelo));
        cb.onSuccess(object);*/
    }


    public static void getUserId(String user_id,HooptapCallback<JSONObject>cb){
        JSONObject object=getObjectParse(HooptapAWS.getClient().userUserIdGet("46576686f6f707461702e627", user_id));

        cb.onSuccess(object);
    }
    public static void login(String email, String password , HooptapCallback<JSONObject>cb){

        InputLoginModel info=new InputLoginModel();
        info.setEmail(email);
        info.setPassword(password);
        JSONObject object=getObjectParse(HooptapAWS.getClient().userLoginPost(HooptapAWS.getApiKey(), info));
        cb.onSuccess(object);
    }

    private static JSONObject getObjectParse(Object o) {
        String value=g.toJson(o);
        try {
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void doAction(String user_id, HooptapCallback<JSONObject> cb ){

        InputActionDoneModel accion= new InputActionDoneModel();
        accion.setTargetId("android");
        accion.setInteractionData("{\"a\":\"b\"}");
        JSONObject object=getObjectParse(HooptapAWS.getClient().userUserIdActionActionNamePost(HooptapAWS.getApiKey(),user_id,"Play",accion));
        cb.onSuccess(object);
        //HooptapAWS.getClient().userUserIdRewardRewardIdGet()//Get user reward
        //HooptapAWS.getClient().userUserIdImagePut() //Cambiar imagen de usuario
        //HooptapAWS.getClient().userUserIdDelete() //borrar usuario
        //HooptapAWS.getClient().userUserIdActionActionNamePost("46576686f6f707461702e627","56547e18c2cd6614312467a5","Play")
    }
    public static void getRewardsUser(String user_id,HooptapCallback<JSONObject>cb)
    {
        JSONObject object=getObjectParse(HooptapAWS.getClient().userUserIdRewardCountGet(HooptapAWS.getApiKey(),user_id));
        cb.onSuccess(object);
    }

    public static void changeImage(String user_id,HooptapCallback<JSONObject>cb){
        JSONObject object=getObjectParse(HooptapAWS.getClient().userUserIdImagePut(HooptapAWS.getApiKey(), user_id));
        cb.onSuccess(object);
    }

}

