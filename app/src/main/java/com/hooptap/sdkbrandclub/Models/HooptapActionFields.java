package com.hooptap.sdkbrandclub.Models;

import java.io.Serializable;

/**
 * Created by carloscarrasco on 24/2/16.
 */
public class HooptapActionFields<T> implements Serializable{
    private String key;
    private T value;
    private String comparison;
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
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
