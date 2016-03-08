package com.hooptap.sdkbrandclub.Engine;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hooptap.sdkbrandclub.BuildConfig;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapFeed;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.OptionsMapper;
import com.hooptap.sdkbrandclub.Utilities.Constants;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.InputStream;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by carloscarrasco on 5/2/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class ParseTest extends AndroidTestCase {


    @Before
    public void setUp() {
        new MapperObjects();
    }

    @Test
    public void testConvertObjectAWSToJsonResponse() {
        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("Badge"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);
        try {
            JSONAssert.assertEquals(parsedObject.toString(), getJsonFromAssets("BadgesResponse"), false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertObjectAWSToJsonResponseWithNullValue() {
        Object parsedObject = ParseObjects.convertObjectToJsonResponse(null);
        assertEquals(parsedObject, null);
    }

    @Test
    public void testParseJsonAWSToComplexObjectWithNullValue() {
        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.LIST);
        options.setSubClassName(Constants.BADGE);

        Object htListResponse = ParseObjects.getObjectParse(null, null);
        assertEquals(htListResponse, null);
    }

    @Test
    public void testParseJsonAWSToBadgeDetail() {

        HooptapBadge badge = new HooptapBadge();
        badge.setIdentificator("557019c2a5a27f5815eb75d9");
        badge.setName("Burger Lover");
        badge.setDescription("Mmmmm Tasty!");
        badge.setImage("https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png");
        badge.setItemType("Badge");
        badge.setProgress(0);

        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("BadgesDetail"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);

        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.BADGE);

        HooptapBadge badgeResponse = ParseObjects.getObjectParse(parsedObject, options);
        assertReflectionEquals(badge, badgeResponse);

    }

    @Test
    public void testParseJsonAWSToBadgesList() {

        HooptapListResponse htListResponseStatic = new HooptapListResponse();
        htListResponseStatic.setCurrent_page(1);
        htListResponseStatic.setPage_size(10);
        htListResponseStatic.setTotal_pages(1);
        htListResponseStatic.setItem_count(1);

        HooptapBadge badge = new HooptapBadge();
        badge.setIdentificator("557019c2a5a27f5815eb75d9");
        badge.setName("Burger Lover");
        badge.setDescription("Mmmmm Tasty!");
        badge.setImage("https://hooptap.s3.amazonaws.com/images/55ba09e9efef5aa001428248/14383482067410.png");
        badge.setItemType("Badge");
        badge.setProgress(0);

        ArrayList<HooptapBadge> array = new ArrayList<>();
        array.add(badge);
        htListResponseStatic.setItemArray(array);

        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("Badge"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);

        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.LIST);
        options.setSubClassName(Constants.BADGE);

        HooptapListResponse htListResponse = ParseObjects.getObjectParse(parsedObject, options);
        assertReflectionEquals(htListResponseStatic, htListResponse);

    }

    @Test
    public void testParseJsonAWSToFeedListCheckingOnlyId() {

        HooptapFeed reward = new HooptapFeed();
        reward.setIdentificator("56b374b16abe38f4578734dc");

        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("Feed"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);

        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.LIST);
        options.setSubClassName(Constants.FEED);

        HooptapListResponse htListResponse = ParseObjects.getObjectParse(parsedObject, options);
        assertThat(reward.getIdentificator(), is(((HooptapFeed) htListResponse.getItemArray().get(0)).getIdentificator()));
        //assertReflectionEquals(htListResponseStatic, htListResponse);

    }

    @Test
    public void testParseJsonAWSToRewardListCheckingObjectComplete() {

        HooptapFeed reward = new HooptapFeed();
        reward.setIdentificator("56b374b16abe38f4578734dc");
        reward.setReason("level_up");
        reward.setReason_type("level");
        HooptapLevel item = new HooptapLevel();
        item.setDescription("desc");
        item.setMin_points(5);
        item.setImage("https://hooptap.s3.amazonaws.com/images/560914db4871f62672e3ecff/item/14437809765700.png");
        item.setName("Conductor ocasional");
        item.setIdentificator("56b0a1d169b4d2970a68a580");
        reward.setFeed(item);

        LinkedTreeMap objectAWSToParse = generateResponseLikeAWS(getJsonFromAssets("Feed"));
        JSONObject parsedObject = ParseObjects.convertObjectToJsonResponse(objectAWSToParse);

        OptionsMapper options = new OptionsMapper();
        options.setClassName(Constants.LIST);
        options.setSubClassName(Constants.FEED);

        HooptapListResponse htListResponse = ParseObjects.getObjectParse(parsedObject, options);

        assertReflectionEquals(reward.getFeed(), ((HooptapFeed) htListResponse.getItemArray().get(0)).getFeed());

    }

    public String getJsonFromAssets(String nameJson) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(nameJson + ".json");
        String jsonString = Utils.loadJSONFromAsset(in);
        return jsonString;
    }

    public LinkedTreeMap generateResponseLikeAWS(String jsonString) {
        Gson g = new Gson();
        return g.fromJson(jsonString, LinkedTreeMap.class);
    }
}