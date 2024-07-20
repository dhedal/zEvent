package com.ecf.zevent.service;


import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.repository.StreamerRepository;
import com.ecf.zevent.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class StreamerService extends AbstractService<StreamerRepository, Streamer>{
    private static final Logger LOG = LoggerFactory.getLogger(StreamerService.class);

    private AuthService authService;
    @Autowired
    public StreamerService(StreamerRepository repository, AuthService authService ) {
        super(repository);
        this.authService = authService;
    }

    public Streamer create(Streamer streamer) {
        if(Objects.isNull(streamer) || Objects.nonNull(streamer.getId())) return null;
        streamer.getAuthenticationData().setPassword(PasswordUtil.passwordTemp());
        return this.save(streamer);
    }

    @Transactional
    public Streamer update(Streamer streamer){
        if(Objects.isNull(streamer) ||
                Objects.isNull(streamer.getUuid())) return null;

        Streamer entity = this.findByUuid(streamer.getUuid());
        if(Objects.isNull(entity)) return null;

        entity.getPrivateData().setFirstName(streamer.getPrivateData().getFirstName());
        entity.getPrivateData().setLastName(streamer.getPrivateData().getLastName());
        entity.getPublicData().setPseudo(streamer.getPublicData().getPseudo());
        entity.getPublicData().setBirthDate(streamer.getPublicData().getBirthDate());
        entity.getPublicData().setChannel(streamer.getPublicData().getChannel());
        entity.getAuthenticationData().setEmail(entity.getAuthenticationData().getEmail());
        entity.setRule(streamer.getRule());
        entity.setStatus(streamer.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        entity = this.save(entity);

        return entity;
    }

    public List<String> getPseudoList(){
        List<Streamer> streamers = this.listAll();
        return streamers.stream()
                .map(Streamer::getPublicData)
                .map(StreamerPublicData::getPseudo)
                .toList();
    }

    public Streamer findByPseudo( String pseudo) {
        if(Objects.isNull(pseudo) || Objects.equals(pseudo, "")) return null;
        return this.repository.findByPseudo(pseudo);
    }

    public Streamer findByUuid(String uuid) {
        if(uuid == null || uuid.equals("")) return null;
        return this.repository.findByUuid(uuid);
    }

    public Streamer findByAuthenticationDataId(Long authenticationDataId) {
        return this.repository.findByAuthenticationDataId(authenticationDataId);
    }

    public boolean isPseudoExist(String pseudo) {
        return Objects.nonNull(this.findByPseudo(pseudo));
    }

    public boolean isEmailExist( String email) {
        return this.authService.isEmailExist(email);
    }
}
