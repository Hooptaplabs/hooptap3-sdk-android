package com.hooptap.sdkbrandclub.Api;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.hooptap.sdkbrandclub.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by carloscarrasco on 5/2/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")

public class HooptapTest extends AndroidTestCase {

    private String TAG_STRING = "Hooptap";

    public Hooptap.Builder createHooptapObject() {
        return new Hooptap.Builder(RuntimeEnvironment.application);
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

}