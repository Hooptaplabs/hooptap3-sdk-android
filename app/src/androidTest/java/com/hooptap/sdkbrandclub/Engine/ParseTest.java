package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;

import com.hooptap.sdkbrandclub.Api.Hooptap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class ParseTest extends InstrumentationTestCase {

    private String TAG_STRING = "Hooptap";

    public Hooptap.Builder createHooptapObject() {
        return new Hooptap.Builder(getInstrumentation().getTargetContext());
    }

    @Test
    public void testSetGetToken() {
        Hooptap.getTinyDB().putString("ht_token", "Bearer " + TAG_STRING);
        assertThat(Hooptap.getToken(), is("Bearer " + TAG_STRING));
    }
}