package com.hooptap.sdkbrandclub.Models;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by carloscarrasco on 9/12/14.
 */
public class HooptapWildCard extends HooptapItem {

    private String subType;
    private String tags;
    private String imgs_info;
    private String txts_info;
    private String end_msgOK;
    private boolean find_all;
    private String instructions;
    private String instructions_image;

    public HooptapWildCard(String jsonObj) {
        super(jsonObj);
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("sub_type"))
                subType = json.getString("sub_type");
            Log.e("TAGS",json.getString("tags"));
            if (!json.isNull("tags"))
                tags = json.getString("tags");
            if (!json.isNull("end_msgOK"))
                end_msgOK = json.getString("end_msgOK");
            if (!json.isNull("context_info")){
                JSONObject json_context_info = json.getJSONObject("context_info");
                if (!json_context_info.isNull("imgs_info"))
                    imgs_info = json_context_info.getString("imgs_info");
                if (!json_context_info.isNull("txts_info"))
                    txts_info = json_context_info.getString("txts_info");
                if (!json_context_info.isNull("find_all"))
                    find_all = json_context_info.getBoolean("find_all");
                if (!json_context_info.isNull("instructions"))
                    instructions = json_context_info.getString("instructions");
                if (!json_context_info.isNull("instructions_image"))
                    instructions_image = json_context_info.getString("instructions_image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImgs_info() {
        return imgs_info;
    }

    public void setImgs_info(String imgs_info) {
        this.imgs_info = imgs_info;
    }

    public String getTxts_info() {
        return txts_info;
    }

    public void setTxts_info(String txts_info) {
        this.txts_info = txts_info;
    }

    public String getEnd_msgOK() {
        return end_msgOK;
    }

    public void setEnd_msgOK(String end_msgOK) {
        this.end_msgOK = end_msgOK;
    }

    public boolean isFind_all() {
        return find_all;
    }

    public void setFind_all(boolean find_all) {
        this.find_all = find_all;
    }

    public String getInstructions_image() {
        return instructions_image;
    }

    public void setInstructions_image(String instructions_image) {
        this.instructions_image = instructions_image;
    }
}
