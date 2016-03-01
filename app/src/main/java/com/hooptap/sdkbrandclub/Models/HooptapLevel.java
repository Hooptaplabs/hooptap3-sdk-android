package com.hooptap.sdkbrandclub.Models;

/**
 * Created by root on 5/01/16.
 */
public class HooptapLevel extends HooptapItem{
    int min_points;
    boolean passed;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getMin_points() {
        return min_points;
    }

    public void setMin_points(int min_points) {
        this.min_points = min_points;
    }
}
