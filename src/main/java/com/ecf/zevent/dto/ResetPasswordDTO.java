package com.ecf.zevent.dto;

import com.ecf.zevent.validation.constraint.ValidUUID;
import com.ecf.zevent.validation.constraint.interfaces.Update;

import javax.validation.constraints.NotBlank;

public class ResetPasswordDTO {

    @ValidUUID(groups = Update.class, message = "Le token est obligatoire et doit être un UUID")
    private String token;
    @NotBlank(message = "L'email' est obligatoire")
    private String password;

    public ResetPasswordDTO() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResetPasswordDTO{");
        sb.append("token='").append(token).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
