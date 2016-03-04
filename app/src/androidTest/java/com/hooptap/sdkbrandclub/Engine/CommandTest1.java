package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;

import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class CommandTest1 extends InstrumentationTestCase {

    private static final String BADGE_DETAIL_METHODNAME = "rewardIdGet";


    @Test
    public void testGetParametersOfReflectionMethod() {

        TaskCreator comandMock = Mockito.mock(TaskCreator.class);
        ArgumentCaptor<HooptapCallback> argument = ArgumentCaptor.forClass(HooptapCallback .class);
        //verify(comand).executeMethod(argument.capture());
        //assertEquals("John", argument.getValue().getName());

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", "46576686f6f707461702e627");
        data.put("token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiI1NmMyZmExNGM0MzNiMjllN2I4NWU0MWYiLCJhcGlLZXkiOiI0NjU3NjY4NmY2ZjcwNzQ2MTcwMmU2MjciLCJleHBpcmF0aW9uIjoxNDU1NzA3MTc0Mjk2LCJpYXQiOjE0NTU2MjA3NzQsImV4cCI6MTQ1NTcwNzE3NH0.IHV-G6bw3i1M8AoAeiSzBgSWgZOpA_9dpBOlHjxQal8");
        data.put("id", "56d5817155aa7c877a186534");

        TaskCreator comand = new TaskCreator(BADGE_DETAIL_METHODNAME, data);

        /*.executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                OptionsMapper options = setClassAndSubClasForMapper(Constants.BADGE);
                HooptapBadge badge = ParseObjects.getObjectParse(jsonResponse, options);
                cb.onSuccess(badge);
            }

            @Override
            public void onError(ResponseError var) {
                cb.onError(var);
            }
        });*/

    }


}