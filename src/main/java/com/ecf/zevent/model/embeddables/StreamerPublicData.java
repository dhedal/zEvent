package com.ecf.zevent.model.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class StreamerPublicData {
    @Column(unique = true, nullable = false, length = 50)
    private String pseudo;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, length = 100)
    private String channel;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamerPublicData{");
        sb.append("pseudo='").append(pseudo).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", channel='").append(channel).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(34);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        StreamerPublicData that = (StreamerPublicData) obj;
        if(null == obj || !(obj instanceof StreamerPublicData)) return false;
        return Objects.equals(this.pseudo, that.pseudo) &&
                Objects.equals(this.birthDate, that.birthDate) &&
                Objects.equals(this.channel, that.channel);
    }
}
