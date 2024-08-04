package com.ecf.zevent.service;

import com.ecf.zevent.model.AuthenticationData;
import com.ecf.zevent.model.PasswordResetToken;
import com.ecf.zevent.repository.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    private final PasswordResetTokenRepository repository;

    @Value("${password.reset.token.expiration.hours}")
    private long TOKEN_EXPIRATION_HOURS;
    @Autowired
    public PasswordResetTokenService(PasswordResetTokenRepository repository){
        this.repository = repository;
    }

    public PasswordResetToken findByToken(String token) {
        LOG.debug("## findByToken");
        if(Objects.isNull(token) || token.isEmpty()) return null;
        return this.repository.findByToken(token)
                .orElse(null);
    }

    public boolean isTokenValid(String token) {
        LOG.debug("## isTokenValid: " + TOKEN_EXPIRATION_HOURS);
        if(Objects.isNull(token) || token.isEmpty()) return false;
        PasswordResetToken passwordResetToken = this.repository.findByToken(token).orElse(null);
        if(Objects.isNull(passwordResetToken)) return false;
        if(passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) return true;
        this.repository.delete(passwordResetToken);
        return false;
    }

    public PasswordResetToken create(AuthenticationData authData) {
        LOG.debug("## create");
        if(Objects.isNull(authData)) return null;

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOURS));
        token.setAuthData(authData);
        return this.repository.save(token);
    }

    public void delete(PasswordResetToken token) {
        LOG.debug("## delete");
        this.repository.delete(token);
    }
}
