package com.example.puray.rest;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("message") private String message;
    @SerializedName("user_id")private int userId;
    @SerializedName("auth_token")private String authToken;

    public RegistrationResponse(String message, int userId, String authToken) {
        this.message = message;
        this.userId = userId;
        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
