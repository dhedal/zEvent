package com.ecf.zevent.dto;

public class AuthDataResponse {

    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public AuthDataResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public AuthDataResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthDataResponse{");
        sb.append("token='").append(token).append('\'');
        sb.append(", expiresIn=").append(expiresIn);
        sb.append('}');
        return sb.toString();
    }
}