package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 8/2/16.
 */
public class HooptapResponse<T> {

    public int current_page;
    public int total_pages;
    public int page_size;
    public int item_count;
    public ArrayList<T> items;

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public ArrayList<T> getItemArray() {
        return items;
    }

    public void setItemArray(ArrayList<T> mappedResponse) {
        this.items = mappedResponse;
    }
}
