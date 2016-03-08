package com.hooptap.sdkbrandclub.Engine;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.brandclub.HooptapVClient;
import com.hooptap.brandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.BuildConfig;
import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Models.HooptapLogin;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * Created by carloscarrasco on 8/3/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class ErrorManagerTest {

    private Exception exception;
    private static final String BADGE_DETAIL_METHODNAME = "rewardIdGet";
    private static final String USER_LOGIN_METHODNAME = "userLoginPost";
    private static final String API_KEY = "56cd7ef8a933937c403897c8";
    private static final String TOKEN_EXPIRED = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiI1NmQ0NzgzMzhkOWEwOTMxNWQ3MDU3NDAiLCJhcGlLZXlzIjpbIjU2Y2Q3ZWY4YTkzMzkzN2M0MDM4OTdjOCJdLCJleHBpcmF0aW9uIjoxNDU2ODUxMzc5NzE2LCJpYXQiOjE0NTY3NjQ5NzksImV4cCI6MTQ1Njg1MTM3OX0.xq9cxfoO_Tbd7uQekAdbYbBMphhYekr0omXvRKCltkA";
    private static String TOKEN_VALID;

    @Before
    public void inicializeSDKWithGoodApiKey() {
        new Hooptap.Builder(RuntimeEnvironment.application).setApiKey(API_KEY).build();
    }

    public void doLoginForGetValidToken() {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", API_KEY);
        HooptapLogin login = new HooptapLogin();
        login.setEmail("ca");
        login.setPassword("ca");
        data.put("info", login);

        try {
            Class[] args = {String.class, InputLoginModel.class};
            Class cls = HooptapVClient.class;
            Method method = cls.getMethod(USER_LOGIN_METHODNAME, args);
            Object responseFromServer = method.invoke(Hooptap.getClient(), data.values().toArray());
            JSONObject info = ParseObjects.convertObjectToJsonResponse(responseFromServer);
            try {
                if (!info.isNull("access_token")) {
                    String infoToken = info.getString("access_token");
                    TOKEN_VALID =  "Bearer " + infoToken;
                }
            } catch (JSONException e) {
                Assert.fail("Exception " + e);
            }
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    public void generateExceptionWithTokenExpired(){

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", API_KEY);
        data.put("token", TOKEN_EXPIRED);
        data.put("id", "Badge_Id");
        try {
            Class[] args = {String.class, String.class, String.class};
            Class cls = HooptapVClient.class;
            Method method = cls.getMethod(BADGE_DETAIL_METHODNAME, args);
            try {
                method.invoke(Hooptap.getClient(), data.values().toArray());
            } catch (Exception e) {
                exception = e;
            }
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void testTokenExpiredError(){
        generateExceptionWithTokenExpired();
        System.out.print(exception.getCause().getClass().getSimpleName() + " / " + ((ApiClientException) exception.getCause()).getErrorMessage());
        ErrorManager errorManager = new ErrorManager();
        errorManager.setCallbackResponse(new TaskCallbackWithRetry() {
            @Override
            public void retry() {

            }

            @Override
            public void onSuccess(Object var) {

            }

            @Override
            public void onError(ResponseError var) {

            }
        });
        errorManager.setException(exception);
    }

    @Test
    public void testGenericError(){

    }
}
