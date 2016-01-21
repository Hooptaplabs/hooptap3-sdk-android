package com.hooptap.sdkbrandclub.Models;

/**
 * Created by root on 18/01/16.
 */
public class Options {
    int page_size=100;
    int page_number=1;

    public int getPageSize() {
        return page_size;
    }

    public void setPageSize(int page_size) {
        this.page_size = page_size;
    }

    public int getPageNumber() {
        return page_number;
    }

    public void setPageNumber(int page_number) {
        this.page_number = page_number;
    }
}
