package com.hooptap.sdkgameroom.Models;

import com.hooptap.a.client.Response;
import com.hooptap.a.mime.TypedInput;

/**
 * Created by carloscarrasco on 5/5/15.
 */
public class ResponseError {

    private int status;
    private String reason;
    private TypedInput body;
    private String url;

    public ResponseError(Response response) {
        this.status = response.getStatus();
        this.reason = response.getReason();
        this.body = response.getBody();
        this.url = response.getUrl();
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

    public TypedInput getBody() {
        return body;
    }

    public void setBody(TypedInput body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
