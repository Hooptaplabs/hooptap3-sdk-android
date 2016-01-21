package com.hooptap.sdkbrandclub.Api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hooptap.brandclub.model.FileModel;
import com.hooptap.brandclub.model.InputActionDoneModel;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.brandclub.model.InputRenewTokenModel;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Models.Options;
import com.hooptap.sdkbrandclub.Models.RegisterModel;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public class ApiWrapper {


    public Object activeNotifications(String user_id, String notification_id) {
        return Hooptap.getClient().userUserIdNotificationNotificationIdGet(Hooptap.getApiKey(), user_id, notification_id, Hooptap.getToken());
    }

    public Object buyGood(String user_id, String good_id) {
        return Hooptap.getClient().userUserIdMarketplaceGoodGoodIdPost(Hooptap.getApiKey(), user_id + "", good_id, Hooptap.getToken());
    }

    public Object changeImage(String user_id) {
        return Hooptap.getClient().userUserIdImagePut(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object doAction(String user_id, HooptapAccion interaction_data, String accion) {
        return Hooptap.getClient().userUserIdActionActionNamePost(user_id, accion, Hooptap.getApiKey(), Hooptap.getToken(), interaction_data);
    }

    public Object getBadges(String user_id, Options options) {
        return Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object getItemDetail(String user_id, String item_id) {
        return Hooptap.getClient().userUserIdItemItemIdGet(user_id, Hooptap.getApiKey(), item_id, Hooptap.getToken());
    }

    public Object getItems(String user_id,Options options) {
        return Hooptap.getClient().userUserIdItemGet(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object getLevelReward(String user_id, String reward_id) {
        return Hooptap.getClient().userUserIdRewardRewardIdLevelGet(reward_id, user_id, Hooptap.getToken());
    }

    public Object getLevels(@NonNull String userId,Options options) {
        return Hooptap.getClient().userUserIdLevelGet(Hooptap.getApiKey(), userId, Hooptap.getToken());
    }

    public Object getMarketPlace(String user_id,Options options) {
        return Hooptap.getClient().userUserIdMarketplaceGoodGet(user_id, options.getPageSize() + "", Hooptap.getApiKey(), options.getPageNumber() + "", "", Hooptap.getToken());
    }

    public Object getGoods(String user_id, Options options) {
        return Hooptap.getClient().userUserIdMarketplacePurchaseGet(user_id, options.getPageSize() + "", Hooptap.getApiKey(), options.getPageNumber() + "", "", Hooptap.getToken());
    }

    public Object getPoints(String user_id) {
        return Hooptap.getClient().userUserIdPointsGet(Hooptap.getToken(), Hooptap.getApiKey(), user_id);
    }

    public Object getNotifications(String user_id, Options options) {
        return Hooptap.getClient().userUserIdNotificationGet(user_id, options.getPageSize() + "", Hooptap.getApiKey(), options.getPageNumber() + "", "", "");
    }

    public Object getRanking(String user_id, Options options, String filter) {
        return Hooptap.getClient().userUserIdRankingGet(user_id, options.getPageSize() + "", Hooptap.getApiKey(), options.getPageNumber() + "", filter + "", Hooptap.getToken());
    }

    public Object getUserFeed(String user_id,Options options) {

        return Hooptap.getClient().userUserIdFeedGet(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object getRewardsCount(String user_id) {
        return Hooptap.getClient().userUserIdRewardCountGet(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object login(InputLoginModel info) {
        Object data = Hooptap.getClient().userLoginPost(Hooptap.getApiKey(), info);
        Log.e("datos", Utils.getObjectParse(data) + "");
        Hooptap.setToken(data);
        return Hooptap.getClient().userLoginPost(Hooptap.getApiKey(), info);
    }

    public Object getUrlGame(String user_id, String game_id) {
        JSONObject response = Utils.getObjectParse(Hooptap.getClient().userUserIdItemItemIdGet(user_id, Hooptap.getApiKey(), game_id, Hooptap.getToken()));
        try {
            Log.e("url",response+"");
            JSONObject url = new JSONObject();
            //assert response != null;
            JSONObject data = response.getJSONObject("response");
            Log.e("url1",data+"");

            if (!data.isNull("url_game")) {
                url.put("url", data.getString("url_game"));
                Log.e("url2", url + "");

                return Utils.getObjectJson(url);
            } else {
                url.put("url", "");
                return Utils.getObjectJson(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getUsers(Options options) {
        return Hooptap.getClient().userGet(options.getPageSize()+"", Hooptap.getApiKey(), options.getPageNumber()+"", "", Hooptap.getToken());
    }

    public Object getProfile(String user_id) {
        return Hooptap.getClient().userUserIdGet(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object registerUser(RegisterModel info_User) {
        return Hooptap.getClient().userPost(Hooptap.getApiKey(), info_User);
    }

    public Object registrarC2DM(String user_id) {

        return Hooptap.getClient().userUserIdPushNotificationSubscriptionPost(Hooptap.getApiKey(), user_id, Hooptap.getToken());
    }

    public Object renewToken(String user_id, String oldToken) {
        InputRenewTokenModel renewTokenModel = new InputRenewTokenModel();
        renewTokenModel.setToken(oldToken);
        Object nuevoToken = Hooptap.getClient().userUserIdTokenPut(Hooptap.getApiKey(), user_id, oldToken, renewTokenModel);
        Hooptap.setToken(nuevoToken);
        return nuevoToken;
    }

    public Object uploadImage(FileModel fileModel){
        return Hooptap.getClient().fileBase64Post(Hooptap.getApiKey(),fileModel);
    }
   /* public void getLevel(String userId, HooptapCallback<JSONObject> cb) {

        *//*try {
            JSONObject object = getObjectParse(Hooptap.getClient().userUserIdLevelGet(Hooptap.getApiKey(), userId));
            Log.e("getLevels", object + "");

            cb.onSuccess(object);
        } catch (ApiClientException ae) {
            Log.e("getLevels", ae.getErrorMessage());
            cb.onError(getError(ae));
        }*//*

        // get the "Method" data structure with the correct name and signature
        Class clase=Hooptap.getClient().getClass();
        Method[] declaredMethods = clase.getDeclaredMethods();
        Class[] parameterTypes = new Class[2];
      *//*  parameterTypes[0] = String.class;
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
            datos[1]=Hooptap.getApiKey();
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
   /* public void  call( Activity activity,   String user_id,   HooptapCallback<JSONObject> cb) {
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
    public void thread(  Activity activity,  String user_id,   HooptapCallback<JSONObject> cb){
        new Thread(new Runnable() {
            public void run() {


                  JSONObject object = getObjectParse(Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id));


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
    public void asyntask(  String user_id,   HooptapCallback<JSONObject> cb){
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
    public class WorkerTask extends AsyncTask<Object,Object,Object>{
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
    }*/
}