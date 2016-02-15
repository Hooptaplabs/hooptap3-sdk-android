package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallbackRetry;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;
import com.hooptap.sdkbrandclub.Models.ResponseError;
import com.hooptap.sdkbrandclub.Utilities.Constants;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class CommandTest extends InstrumentationTestCase {

    @Before
    public void setUp(){
        new MapperObjects();
    }

    @Test
    public void comand(){
        Command comand = new Command("userUserIdBadgesGet", null, null);

        comand.executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {

            }

            @Override
            public void onError(ResponseError var) {

            }
        });
    }
}