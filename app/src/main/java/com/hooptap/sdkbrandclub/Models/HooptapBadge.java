package com.hooptap.sdkbrandclub.Models;

/**
 * Created by root on 5/01/16.
 */
public class HooptapBadge extends HooptapItem{
    private String progress;
    private String imageOn;
    private String imageOff;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getImageOn() {
        return imageOn;
    }

    public void setImageOn(String imageOn) {
        this.imageOn = imageOn;
    }

    public String getImageOff() {
        return imageOff;
    }

    public void setImageOff(String imageOff) {
        this.imageOff = imageOff;
    }
}
