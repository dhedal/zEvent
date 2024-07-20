package com.ecf.zevent.dto;

import com.ecf.zevent.model.*;
import com.ecf.zevent.model.embeddables.StreamerPrivateData;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.model.enumerations.Rule;
import com.ecf.zevent.model.enumerations.StreamerStatus;
import com.ecf.zevent.validation.constraint.ValidAge;
import com.ecf.zevent.validation.constraint.ValidUUID;
import com.ecf.zevent.validation.constraint.interfaces.Create;
import com.ecf.zevent.validation.constraint.interfaces.NullOrEmpty;
import com.ecf.zevent.validation.constraint.interfaces.Update;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class StreamerDTO {
    @ValidUUID(groups = Update.class, message = "L'UUID est obligatoire pour la mis à jour des donnée")
    @NullOrEmpty(groups = Create.class, message = "L'UUID doit être null pour la création d'un nouveau streamer")
    private String uuid;
    @NotBlank(message = "L'email' est obligatoire")
    @Email( message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;
    @NotBlank(message = "Le pseudo est obligatoire")
    @Size(min = 3, max = 50, message = "le pseudo doit avoir entre 3 et 50 charactères")
    private String pseudo;
    @NotNull(message = "la date de naissance est obligatoire")
    @ValidAge(ageMin = 13, ageMax = 80)
    private LocalDate birthDate;
    @NotNull(message = "le nom de la chaîne est obligatoire")
    private String channel;
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 3, max = 50, message = "le prénom doit avoir entre 3 et 50 charactères")
    private String firstName;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 50, message = "le nom doit avoir entre 3 et 50 charactères")
    private String lastName;
    @NotNull(message = "La régle est obligatoire")
    private Rule rule;
    @NotNull(message = "Le status est obligatoire")
    private StreamerStatus status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public StreamerStatus getStatus() {
        return status;
    }

    public void setStatus(StreamerStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamerDTO{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", channel='").append(channel).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", rule=").append(rule);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public Streamer toStreamer() {
        Streamer streamer = new Streamer();
        streamer.setUuid(this.uuid);
        streamer.setRule(this.rule);
        streamer.setStatus(this.status);

        StreamerPrivateData privateData = new StreamerPrivateData();
        privateData.setFirstName(this.firstName);
        privateData.setLastName(this.lastName);
        streamer.setPrivateData(privateData);

        StreamerPublicData publicData = new StreamerPublicData();
        publicData.setPseudo(this.pseudo);
        publicData.setBirthDate(this.birthDate);
        publicData.setChannel(this.channel);
        streamer.setPublicData(publicData);

        AuthenticationData authData = new AuthenticationData();
        authData.setEmail(this.email);
        streamer.setAuthenticationData(authData);

        return streamer;
    }

    public static StreamerDTO toStreamerDTO(Streamer streamer) {
        if(Objects.isNull(streamer)) return null;
        StreamerDTO dto = new StreamerDTO();

        dto.setUuid(streamer.getUuid());
        dto.setRule(streamer.getRule());
        dto.setStatus(streamer.getStatus());
        dto.setFirstName(streamer.getPrivateData().getFirstName());
        dto.setLastName(streamer.getPrivateData().getLastName());
        dto.setPseudo(streamer.getPublicData().getPseudo());
        dto.setBirthDate(streamer.getPublicData().getBirthDate());
        dto.setChannel(streamer.getPublicData().getChannel());
        dto.setEmail(streamer.getAuthenticationData().getEmail());

        return dto;
    }

    public static List<StreamerDTO> toStreamerDTOs(List<Streamer> streamers) {
        if(streamers == null) return List.of();
        return streamers.stream().filter(Objects::nonNull)
                .map(StreamerDTO::toStreamerDTO)
                .toList();
    }
}
