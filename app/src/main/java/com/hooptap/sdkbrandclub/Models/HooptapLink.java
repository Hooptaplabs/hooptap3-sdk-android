package com.hooptap.sdkbrandclub.Models;

import android.os.Parcel;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapLink extends HooptapItem {

    private  String nType;
    private  String text;
    private  boolean unread;
    private  String subject;
    private String created;

    public HooptapLink(String jsonObj) {
        super(jsonObj);
        try{
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("subject_type"))
                nType = json.getString("subject_type");
            if (!json.isNull("message"))
                text = json.getString("message");
            if (!json.isNull("unread"))
                unread = json.getBoolean("unread");
            if (!json.isNull("subject"))
                subject = json.getJSONObject("subject").toString();
            if (!json.isNull("creation_date"))
                created = json.getString("creation_date");
        }catch (Exception e){e.printStackTrace();}
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
