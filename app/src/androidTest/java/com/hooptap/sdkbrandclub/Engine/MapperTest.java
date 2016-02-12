package com.hooptap.sdkbrandclub.Engine;

import android.test.InstrumentationTestCase;

import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapGame;
import com.hooptap.sdkbrandclub.Models.HooptapGood;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapPoint;
import com.hooptap.sdkbrandclub.Models.HooptapRanking;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
import com.hooptap.sdkbrandclub.Utilities.Constants;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class MapperTest extends InstrumentationTestCase {

    public void createMapperObject() {
        new MapperObjects();
    }

    @Test
    public void testCheckBadgeClass() {
        createMapperObject();
        String nameClassToTest = HooptapBadge.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.BADGE).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckGoodClass() {
        createMapperObject();
        String nameClassToTest = HooptapGood.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.GOOD).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckGameClass() {
        createMapperObject();
        String nameClassToTest = HooptapGame.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.GAME).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckUserClass() {
        createMapperObject();
        String nameClassToTest = HooptapUser.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.USER).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckRankingClass() {
        createMapperObject();
        String nameClassToTest = HooptapRanking.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.RANKING).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }
    @Test
    public void testCheckLevelClass() {
        createMapperObject();
        String nameClassToTest = HooptapLevel.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.LEVEL).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }
    @Test
    public void testCheckPointsClass() {
        createMapperObject();
        String nameClassToTest = HooptapPoint.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.POINT).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }
    @Test
    public void testCheckListClass() {
        createMapperObject();
        String nameClassToTest = HooptapListResponse.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.LIST).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckOtherKeyClass() {
        createMapperObject();
        String nameClassToTest = HooptapItem.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey("Item").getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

}