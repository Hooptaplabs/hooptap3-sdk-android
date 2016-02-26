package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 24/2/16.
 */
public class HooptapAction extends HooptapItem {
    private String key;
    private int value;
    private String comparison;
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
