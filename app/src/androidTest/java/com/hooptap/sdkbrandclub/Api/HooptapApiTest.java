package com.hooptap.sdkbrandclub.Api;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.hooptap.sdkbrandclub.Engine.Command;
import com.hooptap.sdkbrandclub.Engine.MapperObjects;
import com.hooptap.sdkbrandclub.Engine.ParseObjects;
import com.hooptap.sdkbrandclub.Interfaces.HooptapCallback;
import com.hooptap.sdkbrandclub.Models.ResponseError;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class HooptapApiTest extends InstrumentationTestCase {

    private String outputBadges = "{code=0000, message=Success, time=2016-02-16T12:53:36.262Z, timestamp=1.455627216262E12, response={items=[{_id=557019c2a5a27f5815eb75d9, name=Burger Lover, desc=Mmmmm Tasty!, image_on=https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png, image_off=https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png, itemType=Badge, progress=0.0}], paging={current_page=1.0, page_size=10.0, total_pages=1.0, item_count=1.0}, next=?page_number=2}}";
    private

    @Before
    public void setUp(){
        new Hooptap.Builder(getInstrumentation().getContext()).build();
        new MapperObjects();
    }

    @Test
    public void testHooptapApiBadges(){
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("api_key", "46576686f6f707461702e627");
        data.put("user_id", "55827fb88d1d8e411aa06c13");
        data.put("token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiI1NmMyZmExNGM0MzNiMjllN2I4NWU0MWYiLCJhcGlLZXkiOiI0NjU3NjY4NmY2ZjcwNzQ2MTcwMmU2MjciLCJleHBpcmF0aW9uIjoxNDU1NzA3MTc0Mjk2LCJpYXQiOjE0NTU2MjA3NzQsImV4cCI6MTQ1NTcwNzE3NH0.IHV-G6bw3i1M8AoAeiSzBgSWgZOpA_9dpBOlHjxQal8");

        new Command("userUserIdBadgesGet", data).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {
                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                JSONObject jsonResponseStatic = ParseObjects.convertObjectToJsonResponse(output);
                try {
                    JSONAssert.assertEquals(jsonResponse, jsonResponseStatic, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ResponseError var) {
                Log.d("OUTPUTERROR", var.getReason() + "");
                assertThat(var.getReason(), is("3"));
            }
        });
    }

    @Test
    public void testHooptapApiQuests(){

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("page_size", "0");
        data.put("api_key", "56d404ab36815b2972abb1fc");
        data.put("page_number", "0");
        data.put("filter", "");
        data.put("token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiI1NmQ0MDRhYjM2ODE1YjI5NzJhYmIxZmMiLCJhcGlLZXlzIjpbIjU2Y2Q3ZWY4YTkzMzkzN2M0MDM4OTdjOCJdLCJleHBpcmF0aW9uIjoxNDU2ODIxODAzMzI4LCJpYXQiOjE0NTY3MzU0MDMsImV4cCI6MTQ1NjgyMTgwM30.Ll8pI1b_uIADn2OMtpi-pz9sOD-SPwVrGDJhUQtuvlg\n");
        data.put("user_id", "55827fb88d1d8e411aa06c13");

        Log.d("testHooptapApiQuests", "testHooptapApiQuests");
        new Command("userIdQuestGet", data).executeMethod(new HooptapCallback<Object>() {
            @Override
            public void onSuccess(Object output) {

                JSONObject jsonResponse = ParseObjects.convertObjectToJsonResponse(output);
                Log.e("onSuccess",jsonResponse+"");
                JSONObject jsonResponseStatic = ParseObjects.convertObjectToJsonResponse(output);
                try {
                    JSONAssert.assertEquals(jsonResponse, jsonResponseStatic, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ResponseError var) {
                Log.d("OUTPUTERROR", var.getReason() + "");
                assertThat(var.getReason(), is("3"));
            }
        });
    }


}