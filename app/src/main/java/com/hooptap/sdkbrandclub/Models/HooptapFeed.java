package com.hooptap.sdkbrandclub.Models;

/**
 * Created by carloscarrasco on 17/2/16.
 */
public class HooptapFeed<T> extends HooptapItem {
    private String reason;
    private String reason_type;
    private String reason_text;
    private T feed;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason_type() {
        return reason_type;
    }

    public void setReason_type(String reason_type) {
        this.reason_type = reason_type;
    }

    public String getReason_text() {
        return reason_text;
    }

    public void setReason_text(String reason_text) {
        this.reason_text = reason_text;
    }

    public T getFeed() {
        return feed;
    }

    public void setFeed(T feed) {
        this.feed = feed;
    }
}
