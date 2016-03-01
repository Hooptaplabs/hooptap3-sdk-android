package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 24/2/16.
 */
public class HooptapAction<T> extends HooptapItem {
    ArrayList<HooptapActionFields> actionFields = new ArrayList<>();

    public ArrayList<HooptapActionFields> getActionFields() {
        return actionFields;
    }

    public void setActionFields(ArrayList<HooptapActionFields> actionFields) {
        this.actionFields = actionFields;
    }
}
