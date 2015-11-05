package com.hooptap.sdkbrandclub.Utilities;

import android.util.Log;

import com.hooptap.a.RetrofitError;
import com.hooptap.a.mime.TypedByteArray;
import com.hooptap.d.Gson;
import com.hooptap.d.JsonObject;
import com.hooptap.d.JsonParser;
import com.hooptap.d.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by root on 3/11/15.
 */
public class HooptapApiException  extends HooptapException{public static final int DEFAULT_ERROR_CODE = 0;
    private static final String TAG_EROOR = "HooptapApiException";

    private final RetrofitError retrofitError;

    private final HooptapApiError apiError;

    HooptapApiException(HooptapApiError apiError,
                        RetrofitError retrofitError) {
        super(retrofitError.getMessage());
        this.retrofitError = retrofitError;
        this.apiError = apiError;

    }

    HooptapApiException(RetrofitError retrofitError) {
        super(createExceptionMessage(retrofitError));
        setStackTrace(retrofitError.getStackTrace());
        this.retrofitError = retrofitError;
        apiError = readApiError(retrofitError);
    }

    private static String createExceptionMessage(RetrofitError retrofitError) {
        if (retrofitError.getMessage() != null) {
            return retrofitError.getMessage();
        }
        if (retrofitError.getResponse() != null) {
            return "Status: " + retrofitError.getResponse().getStatus();
        }
        return "unknown error";
    }



    /**
     * Error code returned by API request.
     *
     * @return API error code
     */
    public int getErrorCode() {
        return apiError == null ? DEFAULT_ERROR_CODE : apiError.getCode();
    }

    /**
     * Error message returned by API request. Error message may change, the codes will stay the same.
     *
     * @return API error message
     */
    public String getErrorMessage() {
        return apiError == null ? null : apiError.getMessage();
    }

    public boolean canRetry() {
        final int status = retrofitError.getResponse().getStatus();
        return status < 400 || status > 499;
    }

    public RetrofitError getRetrofitError() {
        return retrofitError;
    }



    public static final HooptapApiException convert(RetrofitError retrofitError) {
        return new HooptapApiException(retrofitError);
    }

    public static HooptapApiError readApiError(RetrofitError retrofitError) {
        if (retrofitError == null || retrofitError.getResponse() == null ||
                retrofitError.getResponse().getBody() == null) {
            return null;
        }
        final byte[] responseBytes = ((TypedByteArray) retrofitError.getResponse().getBody())
                .getBytes();

        if (responseBytes == null) return null;
        final String response;
        try {
            response = new String(responseBytes, "UTF-8");
            return parseApiError(response);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG_EROOR, "Failed to convert to string", e);
        }
        return null;
    }

    static HooptapApiError parseApiError(String response) {
        final Gson gson = new Gson();
        try {
            // Get the "errors" object
            final JsonObject responseObj = new JsonParser().parse(response).getAsJsonObject();
            final HooptapApiError[] apiErrors = gson.fromJson(
                    responseObj.get(HooptapApiConstants.Errors.ERRORS), HooptapApiError[].class);
            if (apiErrors.length == 0) {
                return null;
            } else {
                // return the first api error.
                return apiErrors[0];
            }
        } catch (JsonSyntaxException e) {
           Log.e(TAG_EROOR, "Invalid json: " + response, e);
        } catch (Exception e) {
           Log.e(TAG_EROOR, "Unexpected response: " + response, e);
        }
        return null;
    }
}
