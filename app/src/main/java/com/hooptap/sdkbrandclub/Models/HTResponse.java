package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 8/2/16.
 */
public class HTResponse {
    public int limit;
    public int page;
    public int count;
    public ArrayList<HooptapItem>mappedResponse;
    public String plainResponse;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<HooptapItem> getMappedResponse() {
        return mappedResponse;
    }

    public void setMappedResponse(ArrayList<HooptapItem> mappedResponse) {
        this.mappedResponse = mappedResponse;
    }

    public String getPlainResponse() {
        return plainResponse;
    }

    public void setPlainResponse(String plainResponse) {
        this.plainResponse = plainResponse;
    }
}
