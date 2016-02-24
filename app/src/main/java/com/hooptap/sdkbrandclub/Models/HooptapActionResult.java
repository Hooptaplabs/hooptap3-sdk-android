package com.hooptap.sdkbrandclub.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 3/12/15.
 */
public class HooptapActionResult<T> implements Serializable {
    public ArrayList<T> rewards;
    public HooptapLevel level;


    public ArrayList<T> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<T> rewards) {
        this.rewards = rewards;
    }

    public HooptapLevel getLevel() {
        return level;
    }

    public void setLevel(HooptapLevel level) {
        this.level = level;
    }
}
