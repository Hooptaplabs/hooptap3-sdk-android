package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.model.InputLoginModel;
import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.BuildConfig;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.TaskCallbackWithRetry;
import com.hooptap.sdkbrandclub.Models.HooptapLogin;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static junit.framework.Assert.assertTrue;

/**
 * Created by carloscarrasco on 7/3/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class RenewTokenTest {
    private static final String USER_LOGIN_METHODNAME = "userLoginPost";
    private static final String API_KEY = "56cd7ef8a933937c403897c8";
    private static final String BAD_API_KEY = "Hooptap";
    private static final String ERROR_BAD_APIKEY = "Error: Invalid api key";

    @Before
    public void inicializeSDKWithGoodApiKey() {
        new Hooptap.Builder(RuntimeEnvironment.application).setApiKey(API_KEY).build();
    }

    public void doLoginForGetValidToken() {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", API_KEY);
        HooptapLogin login = new HooptapLogin();
        login.setLogin("ca");
        login.setPassword("ca");
        data.put("info", login);

        try {
            Class[] args = {String.class, InputLoginModel.class};
            Class cls = HooptapVClient.class;
            Method method = cls.getMethod(USER_LOGIN_METHODNAME, args);
            Object responseFromServer = method.invoke(Hooptap.getClient(), data.values().toArray());
            Utils.setToken(responseFromServer);
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void testFailWhenRenewTokenWithBadApiKey() {
        doLoginForGetValidToken();
        Hooptap.setApiKey(BAD_API_KEY);
        new RenewToken().renewToken(new TaskCallbackWithRetry() {
            @Override
            public void retry() {
                Assert.fail("Retry Renew Token, when it should fail");
            }

            @Override
            public void onSuccess(Object var) {
                Assert.fail("Success Renew Token, when it should fail");
            }

            @Override
            public void onError(ResponseError var) {
                System.out.print(var.getReason());
                assertTrue(var.getReason().equals(ERROR_BAD_APIKEY));
            }
        });
    }

    @Test
    public void testSuccessWhenRenewToken() {
        doLoginForGetValidToken();
        new RenewToken().renewToken(new TaskCallbackWithRetry() {
            @Override
            public void retry() {
                assertTrue(true);
            }

            @Override
            public void onSuccess(Object var) {
                Assert.fail("onSuccess has been called, when it should retry");
            }

            @Override
            public void onError(ResponseError var) {
                Assert.fail("Exception " + var.getReason());
            }
        });
    }
}