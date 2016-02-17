package com.hooptap.sdkbrandclub.Engine;

import android.util.Log;

import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapGame;
import com.hooptap.sdkbrandclub.Models.HooptapGood;
import com.hooptap.sdkbrandclub.Models.HooptapItem;
import com.hooptap.sdkbrandclub.Models.HooptapLevel;
import com.hooptap.sdkbrandclub.Models.HooptapListResponse;
import com.hooptap.sdkbrandclub.Models.HooptapPoint;
import com.hooptap.sdkbrandclub.Models.HooptapRanking;
import com.hooptap.sdkbrandclub.Models.HooptapReward;
import com.hooptap.sdkbrandclub.Models.HooptapUser;
import com.hooptap.sdkbrandclub.Utilities.Constants;

import java.util.HashMap;

/**
 * Created by carloscarrasco on 9/2/16.
 */
public class MapperObjects {

    private static HashMap<String, Class> mapper = new HashMap<>();

    public MapperObjects() {
        setUpMapper();
    }

    private void setUpMapper() {
        addMap(Constants.BADGE, HooptapBadge.class);
        addMap(Constants.GOOD, HooptapGood.class);
        addMap(Constants.GAME, HooptapGame.class);
        addMap(Constants.USER, HooptapUser.class);
        addMap(Constants.RANKING, HooptapRanking.class);
        addMap(Constants.LEVEL, HooptapLevel.class);
        addMap(Constants.POINT, HooptapPoint.class);
        addMap(Constants.LIST, HooptapListResponse.class);
        addMap(Constants.REWARD, HooptapReward.class);
    }

    private void addMap(String key, Class hooptapClass) {
        mapper.put(key, hooptapClass);
    }

    public static Class getClassFromKey(String key) {
        Log.d("getClassFromKey", key);
        if (mapper.containsKey(key))
            return mapper.get(key);
        else
            return HooptapItem.class;
    }
}
