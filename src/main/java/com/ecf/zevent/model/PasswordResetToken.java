package com.ecf.zevent.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiryDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="auth_data_id", nullable = false)
    private AuthenticationData authData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public AuthenticationData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthenticationData authData) {
        this.authData = authData;
    }
}
