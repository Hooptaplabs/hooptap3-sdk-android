/*
package com.hooptap.sdkbrandclub.Api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.google.gson.Gson;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Models.RegisterModel;
import com.hooptap.sdkbrandclub.Models.ResponseError;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;


*/
/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 *//*

public abstract class HooptapApiAWS {
    //Primer metodo Prueba
    static Gson g = new Gson();
    private static Object reciever;

    public static void getUsers(String size_page, String page_number, String filtro, HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userGet(size_page, Hooptap.getApiKey(), page_number, filtro));
            Log.e("getUsers", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getUsers", ae.getErrorMessage());
            cb.onError(getError(ae));
        }

    }

    public static void registerUser(RegisterModel info_User, HooptapCallback<JSONObject> cb) {

        try {
            Object registro=info_User;
            JSONObject object = getObjectParse(Hooptap.getClient().userPost(Hooptap.getApiKey(), info_User));
            Log.e("getUserId", object + "");
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
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdGet("46576686f6f707461702e627", user_id));
            Log.e("getUserId", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getUserId", ae.getErrorMessage());
            cb.onError(getError(ae));
        }

    }

    public static void login(String email, String password, HooptapCallback<JSONObject> cb) {

        InputLoginModel info = new InputLoginModel();
        info.setEmail(email);
        info.setPassword(password);

        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userLoginPost(Hooptap.getApiKey(), info));
            Log.e("login", object + "");
            JSONObject data = object.getJSONObject("response");
            Log.e("login", data + "");
            Hooptap.setToken(data.getString("access_token"));
            Log.e("login", data.getString("access_token") + "----"+ Hooptap.getToken());
            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("login", ae.getErrorMessage());
            cb.onError(getError(ae));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

      */
/*  InputActionDoneModel accion= new InputActionDoneModel();
        accion.setTargetId("ios");
        accion.setInteractionData("{\"a\":\"b\"}");*//*

       */
/* HooptapAccion accion1=new HooptapAccion();
        accion1.setTarget_id("android");
        accion1.setInteraction_data("{\"a\":\"b\"}");*//*

        HooptapAccion accion1 = new HooptapAccion();
        accion1.setTarget_id(target_id);
        accion1.setInteraction_data(interaction_data);
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdActionActionNamePost(Hooptap.getApiKey(), user_id, accion, accion1));
            Log.e("doAction", object + "");
            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("doAction", ae.getErrorMessage());
            cb.onError(getError(ae));
        }


        ///JSONObject object=getObjectParse(Hooptap.getClient().userUserIdActionActionNamePost(Hooptap.getApiKey(),"56547e18c2cd6614312467a5","ShareDetail",accion1));

        //Hooptap.getClient().userUserIdRewardRewardIdGet()//Get user reward
        //Hooptap.getClient().userUserIdImagePut() //Cambiar imagen de usuario
        //Hooptap.getClient().userUserIdDelete() //borrar usuario
        //Hooptap.getClient().userUserIdActionActionNamePost("46576686f6f707461702e627","56547e18c2cd6614312467a5","Play")
    }

    public static void getRewardsUser(String user_id, HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdRewardCountGet(Hooptap.getApiKey(), user_id));
            Log.e("getRewardsUser", object + "");
            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getRewardsUser", ae.getErrorMessage());
            cb.onError(getError(ae));
        }

    }

    public static void changeImage(String user_id, HooptapCallback<JSONObject> cb) {
        JSONObject object = getObjectParse(Hooptap.getClient().userUserIdImagePut(Hooptap.getApiKey(), user_id));
        cb.onSuccess(object);
    }

    */
/*public static void getActividadReciente(final String lenguage, final String user_id, final int page, final int limit, HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdFeedLangGet(lenguage, user_id, limit + "", Hooptap.getApiKey(), page + "", ""));
            Log.e("actividad_reciente", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("actividad_Error", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }*//*

    public static void getRecentActivity(final String user_id, HooptapCallback<JSONObject> cb) throws ApiClientException {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdFeedGet(Hooptap.getApiKey(), user_id));
            Log.e("actividad_reciente", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("actividad_Error", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void getMarketPlace(final String user_id, final int page, final int limit, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdMarketplaceGoodGet(user_id, limit + "", Hooptap.getApiKey(), page + "", ""));
            Log.e("market", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("actividad_Error", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void getMyMarketPlace(final String user_id, final int page, final int limit, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdMarketplacePurchaseGet(user_id, limit + "", Hooptap.getApiKey(), page + "", ""));
            Log.e("market", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("actividad_Error", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void getMyPointsById(final String user_id, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdPointsGet(Hooptap.getToken(), Hooptap.getApiKey(), user_id));
            Log.e("getMyPointsById", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getMyPointsById", ae.getErrorMessage());
            cb.onError(getError(ae));
        }

    }


    public static void getLevelReward(String user_id, String reward_id, HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdRewardRewardIdLevelGet(reward_id, user_id));
            Log.e("getLevelReward", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getLevelReward", ae.getErrorMessage());
            cb.onError(getError(ae));
        }

    }



    public static void getLevels(@NonNull String userId, HooptapCallback<JSONObject> cb) {

        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdLevelGet(Hooptap.getApiKey(), userId));
            Log.e("getLevels", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getLevels", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }
    public static Object getLevelsReturn(@NonNull String userId, HooptapCallback<JSONObject> cb) {

        return Hooptap.getClient().userUserIdLevelGet(Hooptap.getApiKey(), userId);
    }
    public static void getNotifications(final String user_id, final int page, final int limit, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdNotificationGet(user_id, page + "", Hooptap.getApiKey(), limit + "", ""));
            Log.e("getNotifications", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getNotifications", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void activeNotifications(final String user_id, final String notification_id, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdNotificationNotificationIdGet(Hooptap.getApiKey(), user_id, notification_id));
            Log.e("activeNotifications", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("activeNotifications", ae.getErrorMessage());
            cb.onError(getError(ae));
        }

    }

    public static void registrarC2DM(final String user_id, final HooptapCallback<JSONObject> cb) {

        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdPushNotificationSubscriptionPost(Hooptap.getApiKey(), user_id));
            Log.e("registrarC2DM", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("registrarC2DM", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void getBadges(final String user_id, final HooptapCallback<JSONObject> cb) {


        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id));
            Log.e("getBadges", object + "");
            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getBadges", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void getRanking(final String user_id, final int page, final int limit, String filter, final HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdRankingGet(user_id, limit + "", Hooptap.getApiKey(), page + "", filter + ""));
            Log.e("getRanking", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getRanking", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void buyGood(String user_id, String good_id, HooptapCallback<JSONObject> cb) {
        try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdMarketplaceGoodGoodIdPost("55daee50f95836fb6de15862", user_id + "", good_id));
            Log.e("getNotifications", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getNotifications", ae.getErrorMessage());
            cb.onError(getError(ae));
        }
    }

    public static void getLevel(String userId, HooptapCallback<JSONObject> cb) {

        */
/*try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdLevelGet(Hooptap.getApiKey(), userId));
            Log.e("getLevels", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getLevels", ae.getErrorMessage());
            cb.onError(getError(ae));
        }*//*


        // get the "Method" data structure with the correct name and signature
        Class clase= Hooptap.getClient().getClass();
        Method[] declaredMethods = clase.getDeclaredMethods();
        Class[] parameterTypes = new Class[2];
      */
/*  parameterTypes[0] = String.class;
        parameterTypes[1]=HooptapCallback.class;*//*

        for (Method declaredMethod : declaredMethods) {
            Log.e("metodo",declaredMethod.getName());
            if(declaredMethod.getName().equals("userUserIdLevelGet")){
                parameterTypes= declaredMethod.getParameterTypes();
                break;
            }



        }

        Log.e("datosss",parameterTypes[0]+"----"+parameterTypes[1]);
        Method metodo= null;
        try {

            metodo = clase.getMethod("userUserIdLevelGet", parameterTypes);
            Log.e("infor",metodo.getName());
            Object[] datos = new Object[2];
            datos[0]=userId;
            datos[1]= Hooptap.getApiKey();
            metodo.invoke(Hooptap.getClient(), datos);

        }catch (ApiClientException e){
            Log.e("tipo",e.getErrorMessage());
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }
         catch (Exception e) {

            // generic exception handling
            e.printStackTrace();
        }
        //cambio(metodo,userId,cb);
    }

    private static void cambio(Method o, String userId, HooptapCallback<JSONObject> cb)  {
        Object objecto = null;

    }

    private void cambio() {
    }


   */
/* public static void  call( Activity activity, final String user_id, final HooptapCallback<JSONObject> cb) {
      new Handler().post(new Runnable() {

            @Override
            public void run() {
                try {
                    JSONObject object = getObjectParse(Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id));
                    Log.e("getBadges", object + "");

                    cb.onSuccess(object);
                } catch (ApiClientException ae) {
                    Log.e("getBadges", ae.getErrorMessage());
                    cb.onError(getError(ae));
                }

            }
        });

    }
    public static void thread(final Activity activity,final String user_id, final HooptapCallback<JSONObject> cb){
        new Thread(new Runnable() {
            public void run() {


                final JSONObject object = getObjectParse(Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id));


                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        cb.onSuccess(object);
                    }
                });
            }
        }).start();
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    JSONObject object = getObjectParse(Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id));
                    Log.e("getBadges", object + "");

                    cb.onSuccess(object);
                } catch (ApiClientException ae) {
                    Log.e("getBadges", ae.getErrorMessage());
                    cb.onError(getError(ae));
                }
            }
        });
        thread.start();
    }
    public static void asyntask(final String user_id, final HooptapCallback<JSONObject> cb){
        new WorkerTask(new AsyncResponse() {
            @Override
            public void processFinish(Object object) {
                cb.onSuccess((JSONObject) object);
            }

            @Override
            public void processCancel(Object object) {
                cb.onError((ResponseError) object);
            }
        }).execute();
    }
    public static class WorkerTask extends AsyncTask<Object,Object,Object>{
        public AsyncResponse delegate=null;
        public WorkerTask(AsyncResponse asyncResponse){
            delegate= asyncResponse;
            
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                JSONObject object = getObjectParse(Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), "56547e18c2cd6614312467a5"));
                return object;
            } catch (ApiClientException ae) {
                Log.e("getBadges", ae.getErrorMessage());
                delegate.processCancel(getError(ae));
               return (null);
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            if(o!=null)
             delegate.processFinish(o);
        }
    }
    public interface AsyncResponse{
        void processFinish(Object object);
        void processCancel(Object object);
    }*//*

}*/
