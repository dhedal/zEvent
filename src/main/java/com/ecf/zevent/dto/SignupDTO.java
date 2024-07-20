package com.ecf.zevent.dto;

import com.ecf.zevent.model.AuthenticationData;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPrivateData;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.model.enumerations.Rule;
import com.ecf.zevent.model.enumerations.StreamerStatus;
import com.ecf.zevent.util.PasswordUtil;
import com.ecf.zevent.validation.constraint.ValidAge;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class SignupDTO {

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 3, max = 50, message = "le prénom doit avoir entre 3 et 50 charactères")
    private String firstName;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 50, message = "le nom doit avoir entre 3 et 50 charactères")
    private String lastName;
    @NotBlank(message = "Le pseudo est obligatoire")
    @Size(min = 3, max = 50, message = "le pseudo doit avoir entre 3 et 50 charactères")
    private String pseudo;
    @NotBlank(message = "L'email' est obligatoire")
    @Email( message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;
    @NotNull(message = "la date de naissance est obligatoire")
    @ValidAge(ageMin = 13, ageMax = 80)
    private LocalDate birthDate;
    @NotNull(message = "le nom de la chaîne est obligatoire")
    private String channel;

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignupDTO{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", channel='").append(channel).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Streamer toStreamer() {
        StreamerPrivateData privateData = new StreamerPrivateData();
        privateData.setFirstName(this.getFirstName());
        privateData.setLastName(this.getLastName());

        StreamerPublicData publicData = new StreamerPublicData();
        publicData.setPseudo(this.getPseudo());
        publicData.setBirthDate(this.getBirthDate());
        publicData.setChannel(this.getChannel());

        AuthenticationData authData = new AuthenticationData();
        authData.setEmail(this.getEmail());

        Streamer streamer = new Streamer();
        streamer.setPrivateData(privateData);
        streamer.setPublicData(publicData);
        streamer.setAuthenticationData(authData);
        return streamer;
    }
}
