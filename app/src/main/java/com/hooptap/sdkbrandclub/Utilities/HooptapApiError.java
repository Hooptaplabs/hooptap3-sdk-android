package com.hooptap.sdkbrandclub.Utilities;

import com.hooptap.d.annotations.SerializedName;

/**
 * Created by root on 3/11/15.
 */
public class HooptapApiError {
    @SerializedName("message")
    private final String message;

    @SerializedName("code")
    private final int code;

    public HooptapApiError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
