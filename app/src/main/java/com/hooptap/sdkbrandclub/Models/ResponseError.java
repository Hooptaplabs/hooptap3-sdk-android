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
            this.status = jsonResponse.getInt("httpErrorCode");
            this.reason = jsonResponse.getString("message");
            this.timestamp = jsonResponse.getString("timestamp");
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


