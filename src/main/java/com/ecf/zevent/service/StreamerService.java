package com.ecf.zevent.service;


import com.ecf.zevent.dto.StreamerDTO;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.model.enumerations.Rule;
import com.ecf.zevent.model.enumerations.StreamerStatus;
import com.ecf.zevent.repository.StreamerRepository;
import com.ecf.zevent.util.PasswordUtil;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Service
public class StreamerService extends AbstractService<StreamerRepository, Streamer>{
    private static final Logger LOG = LoggerFactory.getLogger(StreamerService.class);

    private final AuthService authService;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public StreamerService(StreamerRepository repository,
                           AuthService authService,
                           MailService mailService,
                           BCryptPasswordEncoder passwordEncoder) {
        super(repository);
        this.authService = authService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }


    public Streamer create(StreamerDTO streamerDTO) {
        LOG.debug("## create");
        if(Objects.isNull(streamerDTO) || Objects.nonNull(streamerDTO.getUuid())) return null;
        if(!this.mailService.isMailValid(streamerDTO.getEmail())) return null;

        Streamer streamer = streamerDTO.toStreamer();
        final String password = PasswordUtil.generateRandomPassword();
        streamer.getAuthenticationData().setPassword(this.passwordEncoder.encode(password));
        streamer = this.save(streamer);

        try {
            if (Objects.nonNull(streamer) &&
                    Objects.nonNull(streamer.getId()) &&
                    Objects.nonNull(streamer.getUuid()) &&
                    Objects.nonNull(streamer.getCreatedAt())) {
                this.mailService.sendWelcomeMessage(streamer, password);
                return streamer;
            }
        } catch (MessagingException ex) {
            LOG.error(ex.toString());
        }

        return null;
    }

    @Transactional
    public Streamer update(StreamerDTO dto){
        LOG.debug("## update");
        if(Objects.isNull(dto) ||
                Objects.isNull(dto.getUuid())) return null;

        Streamer entity = this.findByUuid(dto.getUuid());
        if(Objects.isNull(entity)) return null;
        final StreamerStatus statusPrev = entity.getStatus();

        entity.getPrivateData().setFirstName(dto.getFirstName());
        entity.getPrivateData().setLastName(dto.getLastName());
        entity.getPublicData().setBirthDate(dto.getBirthDate());
        entity.getPublicData().setChannel(dto.getChannel());
        entity.setRule(dto.getRule());
        entity.setStatus(dto.getStatus());

        /**
            si la création du streamer à moin de deux jours,
            envoyer un email avec un password provisoire
         */
        String password = null;
        if(Objects.nonNull(entity.getUpdatedAt())) {
            LocalDateTime dateTime  = entity.getUpdatedAt().plusDays(2);
            if(dateTime.isAfter(LocalDateTime.now())) {
                password = PasswordUtil.generateRandomPassword();
                entity.getAuthenticationData().setPassword( this.passwordEncoder.encode(password));
            }
        }
        entity = this.save(entity);

        if(Objects.nonNull(password) &&
                Objects.equals(statusPrev, StreamerStatus.REGISTRATION_REQUEST) &&
                Objects.equals(entity.getStatus(), StreamerStatus.STREAMER_ACTIVATE)){
            try {
                this.mailService.sendWelcomeMessage(entity, password);
            } catch (MessagingException ex) {
                LOG.error(ex.toString());
            }
        }

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
