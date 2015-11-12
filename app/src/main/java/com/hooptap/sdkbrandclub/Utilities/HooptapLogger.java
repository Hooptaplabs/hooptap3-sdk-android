package com.hooptap.sdkbrandclub.Utilities;

/**
 * Created by root on 9/11/15.
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import android.util.Log;

import com.hooptap.sdkbrandclub.Api.Hooptap;
import com.hooptap.sdkbrandclub.BuildConfig;

public class HooptapLogger {

    public enum Level {
        error, warn, info, debug, trace
    }

    private static final String DEFAULT_TAG = "HooptapLogger";
    boolean active_debug=Hooptap.Builder.htEnableDebug;

    //private static final Level CURRENT_LEVEL = BuildConfig.DEBUG ? Level.trace : Level.info;

    private static boolean isEnabled(Level l) {
        return true;
    }

    static {
        Log.i(DEFAULT_TAG, "Activado modo debug");
    }

    private String classname = DEFAULT_TAG;

    public void setClassName(Class<?> c) {
        classname = c.getSimpleName();
    }

    public String getClassname() {
        return classname;
    }

    public void error(Object... args) {
        if (active_debug) Log.e(buildTag(), build(args));
    }

    public void warn(Object... args) {
        if (active_debug) Log.w(buildTag(), build(args));
    }

    public void info(Object... args) {
        if (active_debug) Log.i(buildTag(), build(args));
    }

    public void debug(Object... args) {
        if (active_debug) Log.d(buildTag(), build(args));
    }

    public void trace(Object... args) {
        if (active_debug) Log.v(buildTag(), build(args));
    }

    public void error(String msg, Throwable t) {
        if (active_debug) error(buildTag(), msg, stackToString(t));
    }

    public void warn(String msg, Throwable t) {
        if (active_debug) warn(buildTag(), msg, stackToString(t));
    }

    public void info(String msg, Throwable t) {
        if (active_debug) info(buildTag(), msg, stackToString(t));
    }

    public void debug(String msg, Throwable t) {
        if (active_debug) debug(buildTag(), msg, stackToString(t));
    }

    public void trace(String msg, Throwable t) {
        if (active_debug) trace(buildTag(), msg, stackToString(t));
    }

    private String buildTag() {
        String tag;
        if (BuildConfig.DEBUG) {
            StringBuilder b = new StringBuilder(20);
            b.append(getClassname());

            StackTraceElement stackEntry = Thread.currentThread().getStackTrace()[4];
            if (stackEntry != null) {
                b.append('.');
                b.append(stackEntry.getMethodName());
                b.append(':');
                b.append(stackEntry.getLineNumber());
            }
            tag = b.toString();
        } else {
            tag = DEFAULT_TAG;
        }
        return tag;
    }

    private String build(Object... args) {
        if (args == null) {
            return "null";
        } else {
            StringBuilder b = new StringBuilder(args.length * 10);
            for (Object arg : args) {
                if (arg == null) {
                    b.append("null");
                } else {
                    b.append(arg);
                }
            }
            return b.toString();
        }
    }

    private String stackToString(Throwable t) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
        baos.toString();
        t.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }
}