package com.hooptap.sdkbrandclub.Models;

import android.view.View;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapFriend extends HooptapItem {

    private String username;
    private int points;
    private int invitation;//0 Normal Friend // 1 Invitation // 2 Add
    private String friendship_code;
    private View.OnClickListener possitiveClick;
    private View.OnClickListener negativeClick;

    public HooptapFriend(String jsonObj) {
        super(jsonObj);
        try{
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("username"))
                this.username = json.getString("username");
            if (!json.isNull("points"))
                this.points = json.getInt("points");
            if (!json.isNull("info"))
                this.image = json.getJSONObject("info").getString("image");
            if (!json.isNull("friendship_code"))
                this.friendship_code = json.getString("friendship_code");
            if (!json.isNull("invitation"))
                this.invitation = json.getInt("invitation");

        }catch (Exception e){e.printStackTrace();}
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getInvitation() {
        return invitation;
    }

    public void setInvitation(int invitation) {
        this.invitation = invitation;
    }

    public String getCode() {
        return friendship_code;
    }

    public void setCode(String friendship_code) {
        this.friendship_code = friendship_code;
    }

    public View.OnClickListener getPossitiveClick() {
        return possitiveClick;
    }

    public void setPossitiveClick(View.OnClickListener possitiveClick) {
        this.possitiveClick = possitiveClick;
    }

    public View.OnClickListener getNegativeClick() {
        return negativeClick;
    }

    public void setNegativeClick(View.OnClickListener negativeClick) {
        this.negativeClick = negativeClick;
    }
}
