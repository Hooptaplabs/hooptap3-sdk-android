package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 10/2/16.
 */
public class HooptapRanking extends HooptapItem{

    private ArrayList<HooptapUser> items;

    public ArrayList<HooptapUser> getUsers() {
        return items;
    }

    public void setUsers(ArrayList<HooptapUser> items) {
        this.items = items;
    }
}
