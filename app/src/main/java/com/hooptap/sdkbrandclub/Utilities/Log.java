package com.hooptap.sdkbrandclub.Utilities;

import com.hooptap.sdkbrandclub.Api.Hooptap;

import java.util.ArrayList;

/**
 * Created by root on 15/12/15.
 */
public class Log {


    static public void d(String tag, String msgFormat, Object... args) {
        if (Hooptap.getEnableDebug()) {
            int maxLogSize = 2000;
            /*for(int i = 0; i <= msgFormat.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > msgFormat.length() ? msgFormat.length() : end;
                android.util.Log.d(tag, msgFormat.substring(start, end));
            }*/
            android.util.Log.d(tag, String.format(msgFormat, args));
        }
    }

    static public void e(String tag,  String msgFormat, Object... args) {
        if (Hooptap.getEnableDebug()) {
            android.util.Log.e(tag, String.format(msgFormat, args));
        }

    }
    /**
     * Divides a string into chunks of a given character size.
     *
     * @param text                  String text to be sliced
     * @param sliceSize             int Number of characters
     * @return  ArrayList<String>   Chunks of strings
     */
    public static ArrayList<String> splitString(String text, int sliceSize) {
        ArrayList<String> textList = new ArrayList<String>();
        String aux;
        int left = -1, right = 0;
        int charsLeft = text.length();
        while (charsLeft != 0) {
            left = right;
            if (charsLeft >= sliceSize) {
                right += sliceSize;
                charsLeft -= sliceSize;
            }
            else {
                right = text.length();
                aux = text.substring(left, right);
                charsLeft = 0;
            }
            aux = text.substring(left, right);
            textList.add(aux);
        }
        return textList;
    }

    /**
     * Divides a string into chunks.
     *
     * @param text                  String text to be sliced
     * @return  ArrayList<String>
     */
    public static ArrayList<String> splitString(String text) {
        return splitString(text, 80);
    }

    /**
     * Divides the string into chunks for displaying them
     * into the Eclipse's LogCat.
     *
     * @param text      The text to be split and shown in LogCat
     * @param tag       The tag in which it will be shown.
     */
    public static void splitAndLog(String tag, String text) {
        ArrayList<String> messageList = splitString(text);
        for (String message : messageList) {
            android.util.Log.e(tag, String.format(message));
            Log.e(tag, message);
        }
    }

}
