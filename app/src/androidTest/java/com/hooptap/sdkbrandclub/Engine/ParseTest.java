package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hooptap.sdkbrandclub.Api.HooptapApi;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;
import com.hooptap.sdkbrandclub.Utilities.Constants;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class ParseTest extends InstrumentationTestCase {

    @Before
    public void setUp(){
        new MapperObjects();
    }

    @Test
    public void testConvertObjectAWSToJsonResponse() {
        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("Badges"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);
        try {
            JSONAssert.assertEquals(parsedObject.toString(), getJsonFromAssets("BadgesResponse"), false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertObjectAWSToJsonResponseTryCatch() {
        Object parsedObject = ParseObjects.convertObjectToJsonResponse(null);
        assertEquals(parsedObject, null);
    }

    @Test
    public void testObjectParseTryCatch() {
        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.LIST);
        options.setSubClassName(Constants.BADGE);

        Object htListResponse = ParseObjects.getObjectParse(null, null);
        assertEquals(htListResponse, null);
    }

    @Test
    public void testObjectParseToBadgesList(){

        HooptapListResponse htListResponseStatic = new HooptapListResponse();
        htListResponseStatic.setCurrent_page(1);
        htListResponseStatic.setPage_size(10);
        htListResponseStatic.setTotal_pages(1);
        htListResponseStatic.setItem_count(1);

        HooptapBadge badge = new HooptapBadge();
        badge.setIdentificator("557019c2a5a27f5815eb75d9");
        badge.setName("Burger Lover");
        badge.setDescription("Mmmmm Tasty!");
        badge.setImageOff("https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png");
        badge.setImageOn("https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png");
        badge.setItemType("Badge");
        badge.setProgress(0);

        ArrayList<HooptapBadge> array = new ArrayList<HooptapBadge>();
        array.add(badge);
        htListResponseStatic.setItemArray(array);

        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("Badges"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);

        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.LIST);
        options.setSubClassName(Constants.BADGE);

        HooptapListResponse htListResponse = ParseObjects.getObjectParse(parsedObject, options);
        assertReflectionEquals(htListResponseStatic, htListResponse);

    }

    @Test
    public void testObjectParseToBadgesDetails(){

        HooptapBadge badge = new HooptapBadge();
        badge.setIdentificator("557019c2a5a27f5815eb75d9");
        badge.setName("Burger Lover");
        badge.setDescription("Mmmmm Tasty!");
        badge.setImageOff("https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png");
        badge.setImageOn("https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png");
        badge.setItemType("Badge");
        badge.setProgress(0);

        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("BadgesDetail"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);

        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.BADGE);

        HooptapBadge badgeResponse = ParseObjects.getObjectParse(parsedObject, options);
        assertReflectionEquals(badge, badgeResponse);

    }

    public String getJsonFromAssets(String nameJson) {
        String jsonString = Utils.loadJSONFromAsset(getInstrumentation().getTargetContext(), nameJson);
        return jsonString;
    }

    public LinkedTreeMap generateResponseLikeAWS(String jsonString){
        Gson g = new Gson();
        return g.fromJson(jsonString, LinkedTreeMap.class);
    }
}