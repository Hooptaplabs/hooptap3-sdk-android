package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 24/2/16.
 */
public class HooptapStep<T extends HooptapItem> extends HooptapItem {
    private ArrayList<String> images;
    private ArrayList<HooptapAction> actions;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<HooptapAction> getActions() {
        return actions;
    }

    public void setActions(ArrayList<HooptapAction> actions) {
        this.actions = actions;
    }
}
