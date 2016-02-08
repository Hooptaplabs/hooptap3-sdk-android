package com.hooptap.sdkbrandclub;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Utilities.Log;
import com.hooptap.sdkbrandclub.Utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class HooptapTest extends InstrumentationTestCase {

    private String TAG_STRING = "Hooptap";

    public Hooptap.Builder createHooptapObject() {
        return new Hooptap.Builder(getInstrumentation().getTargetContext());
    }

    @Test
    public void testSetGetApiKey() {
        Hooptap.Builder hooptapObject = createHooptapObject();
        Hooptap hooptap = hooptapObject.setApiKey(TAG_STRING).build();
        assertThat(hooptap.getApiKey(), is(TAG_STRING));

        Hooptap.Builder hooptapObject1 = createHooptapObject();
        Hooptap hooptap1 = hooptapObject1.build();
        hooptap1.setApiKey(TAG_STRING + "1");
        assertThat(hooptap1.getApiKey(), is(TAG_STRING + "1"));
    }

    @Test
    public void testSetGetToken(){
        Hooptap.getTinyDB().putString("ht_token", "Bearer " + TAG_STRING);
        assertThat(Hooptap.getToken(), is("Bearer " + TAG_STRING));
    }

    @Test
    public void testSetGetEnableDebug() {
        Hooptap.Builder hooptapObject = createHooptapObject();
        Hooptap hooptap = hooptapObject.enableDebug(true).build();
        assertThat(hooptap.getEnableDebug(), is(true));
    }
}