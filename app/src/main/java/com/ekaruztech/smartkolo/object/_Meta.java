package com.ekaruztech.smartkolo.object;

import com.ekaruztech.smartkolo.object.responses.APIError;

import java.io.Serializable;

/**
 * Created by Emmanuel on 5/30/2016.
 */
public class _Meta implements Serializable {
    private int status_code;
    private boolean success;
    private String token;
    private String message;

    private APIError error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public APIError getError() {
        return error;
    }

    public void setError(APIError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "_Meta{" +
                "status_code=" + status_code +
                ", success=" + success +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
