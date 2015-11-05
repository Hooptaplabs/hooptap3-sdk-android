package com.hooptap.sdkbrandclub.Utilities;

/**
 * Created by root on 3/11/15.
 */
public class HooptapException extends RuntimeException{
    public HooptapException(String detailMessage) {
        super(detailMessage);
    }

    public HooptapException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
