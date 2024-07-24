package com.ecf.zevent.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthenticationDataDTO {

    @NotBlank(message = "L'email' est obligatoire")
    @Email( message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthenticationDataDTO{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
