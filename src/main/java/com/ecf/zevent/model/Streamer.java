package com.ecf.zevent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Streamer implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private UUID uuid;
    @Column(unique = true, nullable = false, length = 50)
    private String pseudo;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, length = 100)
    private String chaine;
    @Column(nullable = false)
    private Rule rule;
    @Column(nullable = false)
    private StreamerStatus status;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public StreamerStatus getStatus() { return this.status;}

    public void setStatus(StreamerStatus status) { this.status = status;}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Streamer{");
        sb.append("id=").append(id);
        sb.append(", uuid=").append(uuid);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", chaine='").append(chaine).append('\'');
        sb.append(", rule=").append(rule);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(32);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(null == obj || !(obj instanceof Streamer)) return false;
        Streamer that = (Streamer) obj;
        if(this.id == null && that.id == null) {
            return Objects.equals(this.uuid, that.uuid) &&
                    Objects.equals(this.email, that.email) &&
                    Objects.equals(this.pseudo, that.pseudo) &&
                    Objects.equals(this.email, that.email) &&
                    Objects.equals(this.firstName, this.lastName) &&
                    Objects.equals(this.lastName, that.lastName) &&
                    Objects.equals(this.birthDate, that.birthDate) &&
                    Objects.equals(this.rule, that.rule) &&
                    Objects.equals(this.status, that.status) &&
                    Objects.equals(this.createdAt, that.createdAt);
        }

        return Objects.equals(this.id, that.id);
    }
}
