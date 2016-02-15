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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by carloscarrasco on 5/2/16.
 */
public class MapperTest extends InstrumentationTestCase {

    @Before
    public void setUp(){
        new MapperObjects();
    }

    @Test
    public void testCheckBadgeClass() {
       
        String nameClassToTest = HooptapBadge.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.BADGE).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckGoodClass() {
        String nameClassToTest = HooptapGood.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.GOOD).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckGameClass() {
        String nameClassToTest = HooptapGame.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.GAME).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckUserClass() {
        
        String nameClassToTest = HooptapUser.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.USER).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckRankingClass() {
        
        String nameClassToTest = HooptapRanking.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.RANKING).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }
    @Test
    public void testCheckLevelClass() {
        
        String nameClassToTest = HooptapLevel.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.LEVEL).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }
    @Test
    public void testCheckPointsClass() {
        
        String nameClassToTest = HooptapPoint.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.POINT).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }
    @Test
    public void testCheckListClass() {
        
        String nameClassToTest = HooptapListResponse.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey(Constants.LIST).getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

    @Test
    public void testCheckOtherKeyClass() {
        
        String nameClassToTest = HooptapItem.class.getName();
        String nameClassMapper = MapperObjects.getClassFromKey("Item").getName();

        assertThat(nameClassMapper, is(nameClassToTest));
    }

}