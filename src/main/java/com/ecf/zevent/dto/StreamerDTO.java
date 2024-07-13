package com.ecf.zevent.dto;

import com.ecf.zevent.model.Rule;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.StreamerStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StreamerDTO {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String pseudo;
    private String email;
    private LocalDate birthDate;
    private String chaine;
    private Rule rule;
    private StreamerStatus status;

    private StreamerDTO(){}
    private StreamerDTO(Streamer streamer) {
        this.uuid = streamer.getUuid();
        this.firstName = streamer.getFirstName();
        this.lastName = streamer.getLastName();
        this.pseudo = streamer.getPseudo();
        this.birthDate = streamer.getBirthDate();
        this.email = streamer.getEmail();
        this.chaine = streamer.getChaine();
        this.rule = streamer.getRule();
        this.status = streamer.getStatus();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() { return this.firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() { return this.lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getChaine() {
        return chaine;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
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
        sb.append("uuid=").append(uuid);
        sb.append(", prénom='").append(firstName).append('\'');
        sb.append(", nom='").append(lastName).append('\'');
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", chaine='").append(chaine).append('\'');
        sb.append(", rule=").append(rule);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public static StreamerDTO parse(Streamer streamer) {
        if(streamer == null) return null;
        return new StreamerDTO(streamer);
    }

    public static List<StreamerDTO> parse(List<Streamer> streamers) {
        return streamers.stream()
                .filter(Objects::nonNull)
                .map(StreamerDTO::parse)
                .toList();
    }

    public static StreamerDTO getEmpty() { return new StreamerDTO();}
}
