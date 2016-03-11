package com.hooptap.sdkbrandclub.Engine;

import com.amazonaws.mobileconnectors.apigateway.ApiClientException;
import com.hooptap.sdkbrandclub.HooptapVClient;
import com.hooptap.sdkbrandclub.model.InputLoginModel;
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
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    public void generateExceptionWithTokenExpired() {

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
    public void testErrorManagerWithTokenExperiredApiClientExceptionAndThenRenewTokenIfIsSuccesCallRetryCallback() {
        generateExceptionWithTokenExpired();

        final RenewToken renewTokenTask = Mockito.mock(RenewToken.class);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                ((TaskCallbackWithRetry) invocation.getArguments()[0]).retry();
                return null;
            }
        }).when(renewTokenTask).renewToken(any(TaskCallbackWithRetry.class));

        ErrorManager errorManager = new ErrorManager();
        errorManager.setCallbackResponse(new TaskCallbackWithRetry() {
            @Override
            public void retry() {
                verify(renewTokenTask, times(1)).renewToken(any(TaskCallbackWithRetry.class));
            }

            @Override
            public void onSuccess(Object var) {
                Assert.fail("onSuccess has been called when retry should be called");
            }

            @Override
            public void onError(ResponseError var) {
                Assert.fail("onError has been called when retry should be called");
            }
        });
        errorManager.setRenewTokenTask(renewTokenTask);
        errorManager.setException(exception);
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

    public void generateExceptionRandom() {

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", API_KEY);
        data.put("token", Hooptap.getToken());
        data.put("id", "Badge_Id");
        try {
            Class[] args = {String.class, String.class, String.class};
            Class cls = HooptapVClient.class;
            Method method = cls.getMethod(BADGE_DETAIL_METHODNAME, args);
            try {
                method.invoke(Hooptap.getClient(), data.values().toArray());
            } catch (Exception e) {
                exception = e;
                System.out.print(((ApiClientException) exception.getCause()).getErrorMessage());
            }
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void testErrorManagerWithRandomApiClientExceptionAndThenReturnTheGeneratedError() {
        doLoginForGetValidToken();
        generateExceptionRandom();

        ErrorManager errorManager = new ErrorManager();
        errorManager.setCallbackResponse(new TaskCallbackWithRetry() {
            @Override
            public void retry() {
                Assert.fail("retry has been called when onError should be called");
            }

            @Override
            public void onSuccess(Object var) {
                Assert.fail("onSuccess has been called when onError should be called");
            }

            @Override
            public void onError(ResponseError var) {
                assertThat(var.getReason(), is("Network error communicating with endpoint"));
            }
        });
        errorManager.setRenewTokenTask(null);
        errorManager.setException(exception);
    }

    @Test
    public void testErrorManagerWithNullNormalExceptionAndThenReturnTheGeneratedError() {
        ErrorManager errorManager = new ErrorManager();
        errorManager.setCallbackResponse(new TaskCallbackWithRetry() {
            @Override
            public void retry() {
                Assert.fail("retry has been called when onError should be called");
            }

            @Override
            public void onSuccess(Object var) {
                Assert.fail("onSuccess has been called when onError should be called");
            }

            @Override
            public void onError(ResponseError var) {
                assertThat(var.getReason(), is("Unknown error"));
            }
        });
        errorManager.setRenewTokenTask(null);
        errorManager.setException(null);
    }

    @Test
    public void testErrorManagerWithNoNullNormalExceptionAndThenReturnTheGeneratedError() {
        ErrorManager errorManager = new ErrorManager();
        errorManager.setCallbackResponse(new TaskCallbackWithRetry() {
            @Override
            public void retry() {
                Assert.fail("retry has been called when onError should be called");
            }

            @Override
            public void onSuccess(Object var) {
                Assert.fail("onSuccess has been called when onError should be called");
            }

            @Override
            public void onError(ResponseError var) {
                assertThat(var.getReason(), is("Custom Error"));
            }
        });
        errorManager.setRenewTokenTask(null);

        Exception e = new Exception("Custom Error");
        errorManager.setException(e);
    }
}
