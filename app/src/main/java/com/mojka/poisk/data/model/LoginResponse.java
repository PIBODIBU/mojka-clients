package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseErrorResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
