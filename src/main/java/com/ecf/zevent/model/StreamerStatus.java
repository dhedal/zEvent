package com.ecf.zevent.model;

public enum StreamerStatus {

    REGISTRATION_REQUEST(1, "Demande d'inscription"),
    STREAMER_ACTIVATE(2, "Streamer actif"),
    STREAMER_SUSPENDED(3, "Streamer suspendu");

    private final Integer key;
    private final String description;

    StreamerStatus(Integer key, String description){
        this.key = key;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
