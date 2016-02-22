package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 12/2/16.
 */
public class HooptapPoint extends HooptapItem {
    private int quantity;
    private String type;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
