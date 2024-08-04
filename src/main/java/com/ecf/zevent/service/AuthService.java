package com.ecf.zevent.service;

import com.ecf.zevent.dto.ForgotPasswordResponse;
import com.ecf.zevent.dto.ResetPasswordDTO;
import com.ecf.zevent.dto.SignupDTO;
import com.ecf.zevent.model.AuthenticationData;
import com.ecf.zevent.model.PasswordResetToken;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.enumerations.Rule;
import com.ecf.zevent.model.enumerations.StreamerStatus;
import com.ecf.zevent.repository.AuthenticationDataRepository;
import com.ecf.zevent.util.PasswordUtil;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthService extends AbstractService<AuthenticationDataRepository, AuthenticationData> {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);


    private StreamerService streamerService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final PasswordResetTokenService passwordResetTokenService;


    @Autowired
    public AuthService(
            AuthenticationDataRepository repository,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder  passwordEncoder,
            MailService mailService,
            PasswordResetTokenService passwordResetTokenService) {
        super(repository);
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Autowired
    public void setStreamerService(@Lazy StreamerService streamerService) {
        this.streamerService = streamerService;
    }

    public boolean save(SignupDTO signupDTO){
        if(signupDTO == null) return false;

        if(!this.mailService.isMailValid(signupDTO.getEmail())) return false;
        final String password = PasswordUtil.generateRandomPassword();
        Streamer streamer = signupDTO.toStreamer();
        streamer.getAuthenticationData().setPassword(this.passwordEncoder.encode(password));
        streamer.setRule(Rule.STREAMER);
        streamer.setStatus(StreamerStatus.REGISTRATION_REQUEST);

        streamer = this.streamerService.save(streamer);

        try {
            if (Objects.nonNull(streamer) &&
                    Objects.nonNull(streamer.getId()) &&
                    Objects.nonNull(streamer.getUuid()) &&
                    Objects.nonNull(streamer.getCreatedAt())) {
                this.mailService.sendConfirmationRegistrationMessage(streamer);
                return true;
            }
        } catch (MessagingException ex) {
            LOG.error(ex.toString());
        }
        return false;
    }

    public AuthenticationData authentication(String email, String password) {
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password));

        return this.findByEmail(email);
    }

    public Streamer findStreamerByAuthDataId(Long authDataId){
        return this.streamerService.findByAuthenticationDataId(authDataId);
    }

    public AuthenticationData findByEmail(String email){
        if(email == null || email.isEmpty()) return null;
        return this.repository.findByEmail(email).orElse(null);
    }

    public AuthenticationData findByPseudo(String pseudo){
        if(pseudo == null || pseudo.isEmpty()) return null;
        Streamer streamer = this.streamerService.findByPseudo(pseudo);
        return streamer == null ? null : streamer.getAuthenticationData();
    }

    public boolean isEmailExist(String email) {
        return this.findByEmail(email) != null;
    }

    public boolean isPseudoExist(String pseudo) {
        return this.streamerService.isPseudoExist(pseudo);
    }

    public boolean changePassword(String email, String newPassword, String oldPassword) {
        AuthenticationData authData = this.authentication(email, oldPassword);
        if(Objects.isNull(authData)) return false;
        authData.setPassword(this.passwordEncoder.encode(newPassword));
        this.repository.save(authData);
        return true;
    }

    public String encode(String p) {
        return this.passwordEncoder.encode(p);
    }

    public ForgotPasswordResponse forgotPassword(final String email) {
        LOG.debug("## forgotPassword");
        AuthenticationData authData = null;
        if (Objects.nonNull(email) && email.contains("@")) {
            authData = this.findByEmail(email);
        }

        if (Objects.isNull(authData)) return new ForgotPasswordResponse()
                .setOk(false)
                .setMessage("Il n'existe pas de compte avec cette email!");

        final PasswordResetToken token = this.passwordResetTokenService.create(authData);

        Streamer streamer = this.streamerService.findByAuthenticationDataId(authData.getId());
        if (!this.mailService.forgotPassword(streamer.getPrivateData().getFirstName(), token.getToken())) {
            LOG.error("Pb lors de l'envoi de l'email de réinitialisation de mot de passe : %s".formatted(email));
            return new ForgotPasswordResponse().setOk(false).setMessage("Problème interne, en cours de traitement");
        }

        return new ForgotPasswordResponse().setOk(true).setMessage("Vérifiez vos emails");
    }

    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO) {
        LOG.debug("## resetPassword");
        if(Objects.isNull(resetPasswordDTO)) return false;

        PasswordResetToken token = this.passwordResetTokenService.findByToken(resetPasswordDTO.getToken());
        if(Objects.isNull(token)) return false;

        AuthenticationData authData = token.getAuthData();
        if(Objects.isNull(authData)) return false;

        authData.setPassword(this.passwordEncoder.encode(resetPasswordDTO.getPassword()));
        this.repository.save(authData);
        this.passwordResetTokenService.delete(token);
        return true;
    }

}
