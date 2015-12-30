package com.hooptap.sdkbrandclub.Api;

import android.support.annotation.NonNull;

import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Models.HooptapAccion;
import com.hooptap.sdkbrandclub.Models.RegisterModel;


/**
 * API principal para realizar las peticiones
 *
 * @author Hooptap Team
 */
public class ApiAWS {

    private static Object reciever;

    public Object getUsers(String size_page, String page_number, String filtro) {
        return Hooptap.getClient().userGet(size_page, Hooptap.getApiKey(), page_number, filtro);
    }

    public Object registerUser(RegisterModel info_User) {
        return Hooptap.getClient().userPost(Hooptap.getApiKey(), info_User);
    }


    public Object getUserId(String user_id) {
        return Hooptap.getClient().userUserIdGet(Hooptap.getApiKey(), user_id);
    }

    public Object login(InputLoginModel info) {
        return Hooptap.getClient().userLoginPost(Hooptap.getApiKey(), info);
    }


    public Object doAction(String user_id, HooptapAccion interaction_data, String accion) {
        return Hooptap.getClient().userUserIdActionActionNamePost(Hooptap.getApiKey(), user_id, accion, interaction_data);
    }

    public Object getRewardsUser(String user_id) {
        return Hooptap.getClient().userUserIdRewardCountGet(Hooptap.getApiKey(), user_id);
    }

    public Object changeImage(String user_id) {
        return Hooptap.getClient().userUserIdImagePut(Hooptap.getApiKey(), user_id);
    }


    public Object getRecentActivity(String user_id) {
        return Hooptap.getClient().userUserIdFeedGet(Hooptap.getApiKey(), user_id);
    }

    public Object getMarketPlace(String user_id, int page, int limit) {
        return Hooptap.getClient().userUserIdMarketplaceGoodGet(user_id, limit + "", Hooptap.getApiKey(), page + "", "");

    }

    public Object getMyMarketPlace(String user_id, int page, int limit) {
        return Hooptap.getClient().userUserIdMarketplacePurchaseGet(user_id, limit + "", Hooptap.getApiKey(), page + "", "");
    }

    public Object getMyPointsById(String user_id) {
        return Hooptap.getClient().userUserIdPointsGet(Hooptap.getToken(), Hooptap.getApiKey(), user_id);
    }


    public Object getLevelReward(String user_id, String reward_id) {
        return Hooptap.getClient().userUserIdRewardRewardIdLevelGet(reward_id, user_id);
    }


    public Object getLevels(@NonNull String userId) {
        return Hooptap.getClient().userUserIdLevelGet(Hooptap.getApiKey(), userId);
    }

    public Object getNotifications(String user_id, int page, int limit) {
        return Hooptap.getClient().userUserIdNotificationGet(user_id, page + "", Hooptap.getApiKey(), limit + "", "");

    }

    public Object activeNotifications(String user_id, String notification_id) {
        return Hooptap.getClient().userUserIdNotificationNotificationIdGet(Hooptap.getApiKey(), user_id, notification_id);
    }

    public Object registrarC2DM(String user_id) {

        return Hooptap.getClient().userUserIdPushNotificationSubscriptionPost(Hooptap.getApiKey(), user_id);
    }

    public Object getBadges(String user_id) {
        return Hooptap.getClient().userUserIdBadgesGet(Hooptap.getApiKey(), user_id);
    }

    public Object getRanking(String user_id, int page, int limit, String filter) {
        return Hooptap.getClient().userUserIdRankingGet(user_id, limit + "", Hooptap.getApiKey(), page + "", filter + "");
    }

    public Object buyGood(String user_id, String good_id) {
        return Hooptap.getClient().userUserIdMarketplaceGoodGoodIdPost(Hooptap.getApiKey(), user_id + "", good_id);
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