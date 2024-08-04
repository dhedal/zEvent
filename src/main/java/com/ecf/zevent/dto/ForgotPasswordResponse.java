package com.ecf.zevent.dto;

import java.util.List;

public class ForgotPasswordResponse {
    private boolean ok;
    private String message;

    public boolean isOk() {
        return ok;
    }

    public ForgotPasswordResponse setOk(boolean ok) {
        this.ok = ok;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ForgotPasswordResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForgotPasswordResponse{");
        sb.append("ok=").append(ok);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
