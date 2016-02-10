package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 10/2/16.
 */
public class HooptapRanking extends HooptapItem{

    private ArrayList<HooptapUser> users;
    private String type;

    public ArrayList<HooptapUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<HooptapUser> users) {
        this.users = users;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
