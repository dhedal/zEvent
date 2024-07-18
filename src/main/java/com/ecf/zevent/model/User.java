package com.ecf.zevent.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    private String email;
    private Long liveId;

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

    @Override
    public void setCreatedAt(LocalDateTime updatedAt) {

    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLiveId() {
        return liveId;
    }

    public void setLiveId(Long liveId) {
        this.liveId = liveId;
    }
}
