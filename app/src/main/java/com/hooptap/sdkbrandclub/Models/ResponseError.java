package com.hooptap.sdkbrandclub.Models;


import org.json.JSONObject;

/**
 * Created by carloscarrasco on 5/5/15.
 */
public class ResponseError {

    private int status;
    private String reason;
    private String timestamp;

    public void setData(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if (!jsonResponse.isNull("httpErrorCode")) {
                this.status = jsonResponse.getInt("httpErrorCode");
            } else {
                this.setStatus(500);
            }

            if (!jsonResponse.isNull("message")) {
                this.reason = jsonResponse.getString("message");
            } else {
                this.reason = "Unknown error";
            }

            if (!jsonResponse.isNull("timestamp")) {
                this.timestamp = jsonResponse.getString("timestamp");
            } else {
                this.timestamp = (System.currentTimeMillis() / 1000) + "";
            }
        } catch (Exception e) {
            this.reason = "Unknown error";
            this.setStatus(500);
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


