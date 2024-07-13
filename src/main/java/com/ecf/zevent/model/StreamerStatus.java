package com.ecf.zevent.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StreamerStatus {

    REGISTRATION_REQUEST(1, "en attente","Demande d'inscription"),
    STREAMER_ACTIVATE(2, "actif","Streamer actif"),
    STREAMER_SUSPENDED(3, "suspendu","Streamer suspendu");

    private final Integer key;
    private final String label;
    private final String description;

    StreamerStatus(Integer key, String label, String description){
        this.key = key;
        this.label = label;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
