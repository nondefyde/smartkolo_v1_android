package com.ekaruztech.smartkolo.object.responses;

/**
 * Created by Emmanuel on 8/28/2016.
 */
public class APIError {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
