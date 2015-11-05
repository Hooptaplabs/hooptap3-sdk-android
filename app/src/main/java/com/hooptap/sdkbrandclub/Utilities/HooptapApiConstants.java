package com.hooptap.sdkbrandclub.Utilities;

/**
 * Created by root on 3/11/15.
 */
public class HooptapApiConstants {
    public static final int MAX_TWEET_CHARS = 140;

    public static class Base {
        public static final String PARAM_ID = "id";
        public static final String FIELD_ID = "id";
    }

    public static class Errors extends Base {
        public static final String ERRORS = "errors";

        // error when app auth token not recognized (such as when expired)
        public static final int APP_AUTH_ERROR_CODE = 89;
        // error when a tweet has already been favorited
        public static final int ALREADY_FAVORITED = 139;
        // error when a tweet has already been unfavorited
        public static final int ALREADY_UNFAVORITED = 144;
        // error when guest auth token not recognized (such as when expired)
        public static final int GUEST_AUTH_ERROR_CODE = 239;

        // legacy errors are errors that are returned by the api in a different format, where there
        // is no array of errors.
        public static final int LEGACY_ERROR = 0;
    }
}


