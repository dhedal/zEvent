package com.ecf.zevent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Streamer implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @Embedded
    private StreamerPrivateData privateData;
    @Embedded
    private StreamerPublicData publicData;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_data_id", referencedColumnName = "id")
    private AuthenticationData authenticationData;

    @Column(nullable = false)
    private Rule rule;
    @Column(nullable = false)
    private StreamerStatus status;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public StreamerPrivateData getPrivateData() {
        return privateData;
    }

    public void setPrivateData(StreamerPrivateData privateData) {
        this.privateData = privateData;
    }

    public StreamerPublicData getPublicData() {
        return publicData;
    }

    public void setPublicData(StreamerPublicData publicData) {
        this.publicData = publicData;
    }

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
                    this.privateData.equals(that.privateData) &&
                    this.publicData.equals(that.publicData) &&
                    this.authenticationData.equals(that.authenticationData) &&
                    Objects.equals(this.rule, that.rule) &&
                    Objects.equals(this.status, that.status) &&
                    Objects.equals(this.createdAt, that.createdAt) &&
                    Objects.equals(this.updatedAt, this.updatedAt);

        }
        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Streamer{");
        sb.append("id=").append(id);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", privateData=").append(privateData);
        sb.append(", publicData=").append(publicData);
        sb.append(", rule=").append(rule);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
