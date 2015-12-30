package com.hooptap.sdkbrandclub.Utilities;

import com.hooptap.sdkbrandclub.Api.Hooptap;

/**
 * Created by root on 15/12/15.
 */
public class Log {


    static public void d(String tag, String msgFormat, Object... args) {
        if (Hooptap.Builder.htEnableDebug) {
            android.util.Log.d(tag, String.format(msgFormat, args));
        }
    }

    static public void e(String tag,  String msgFormat, Object... args) {
        if (Hooptap.Builder.htEnableDebug) {
            android.util.Log.e(tag, String.format(msgFormat, args));
        }
    }
}
