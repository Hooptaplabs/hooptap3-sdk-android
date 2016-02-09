package com.hooptap.sdkbrandclub.Engine;

import com.hooptap.sdkbrandclub.Models.HooptapResponse;
import com.hooptap.sdkbrandclub.Models.HooptapBadge;
import com.hooptap.sdkbrandclub.Models.HooptapGood;
import com.hooptap.sdkbrandclub.Models.HooptapUser;

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
        addMap("Badge", HooptapBadge.class);
        addMap("Good", HooptapGood.class);
        addMap("User", HooptapUser.class);
        addMap("List", HooptapResponse.class);
    }

    private void addMap(String key, Class hooptapClass) {
        mapper.put(key, hooptapClass);
    }

    public static Class getClassFromKey(String key){
        return mapper.get(key);
    }
}
