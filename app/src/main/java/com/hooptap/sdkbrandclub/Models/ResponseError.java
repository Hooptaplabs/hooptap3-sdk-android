package com.hooptap.sdkbrandclub.Models;



import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 5/5/15.
 */
public class ResponseError {

    private int status;
    private String reason;
    private String timestamp;

    public ResponseError() {

    }

    public ResponseError(JSONObject response) {
        try {
            this.status = response.getInt("httpErrorCode");
            this.reason = response.getString("message");
            this.timestamp = response.getString("timestamp");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}


