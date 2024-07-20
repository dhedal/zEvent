package com.ecf.zevent.service;

import com.ecf.zevent.dto.SignupDTO;
import com.ecf.zevent.model.AuthenticationData;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPrivateData;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.model.enumerations.Rule;
import com.ecf.zevent.model.enumerations.StreamerStatus;
import com.ecf.zevent.repository.AuthenticationDataRepository;
import com.ecf.zevent.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthService extends AbstractService<AuthenticationDataRepository, AuthenticationData> {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);


    private StreamerService streamerService;

    @Autowired
    public AuthService(AuthenticationDataRepository repository) {
        super(repository);
    }

    @Autowired
    public void setStreamerService(@Lazy StreamerService streamerService) {
        this.streamerService = streamerService;
    }

    public boolean save(SignupDTO signupDTO) {
        if(signupDTO == null) return false;

        StreamerPrivateData privateData = new StreamerPrivateData();
        privateData.setFirstName(signupDTO.getFirstName());
        privateData.setLastName(signupDTO.getLastName());

        StreamerPublicData publicData = new StreamerPublicData();
        publicData.setPseudo(signupDTO.getPseudo());
        publicData.setBirthDate(signupDTO.getBirthDate());
        publicData.setChannel(signupDTO.getChannel());

        AuthenticationData authData = new AuthenticationData();
        authData.setEmail(signupDTO.getEmail());
        authData.setPassword(PasswordUtil.passwordTemp());

        Streamer streamer = new Streamer();
        streamer.setPrivateData(privateData);
        streamer.setPublicData(publicData);
        streamer.setAuthenticationData(authData);
        streamer.setRule(Rule.STREAMER);
        streamer.setStatus(StreamerStatus.REGISTRATION_REQUEST);

        streamer = this.streamerService.save(streamer);

        return Objects.nonNull(streamer) &&
                Objects.nonNull(streamer.getId()) &&
                Objects.nonNull(streamer.getUuid()) &&
                Objects.nonNull(streamer.getCreatedAt());
    }

    public AuthenticationData authentication(String email, String password) {
        AuthenticationData authData = this.findByEmail(email);
        if(Objects.isNull(authData)) return null;
        return Objects.equals(authData.getPassword(), password) ? authData : null;
    }

    public AuthenticationData findByEmail(String email){
        if(email == null || email.equals("")) return null;
        return this.repository.findByEmail(email);
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
        authData.setPassword(newPassword);
        this.repository.save(authData);
        return true;
    }

}
