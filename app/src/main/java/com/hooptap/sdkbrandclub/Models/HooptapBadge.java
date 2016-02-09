package com.hooptap.sdkbrandclub.Models;

/**
 * Created by root on 5/01/16.
 */
public class HooptapBadge extends HooptapItem{
    private String progress;
    private String image_on;
    private String image_off;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getImageOn() {
        return image_on;
    }

    public void setImageOn(String imageOn) {
        this.image_on = imageOn;
    }

    public String getImageOff() {
        return image_off;
    }

    public void setImageOff(String imageOff) {
        this.image_off = imageOff;
    }
}
