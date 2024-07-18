package com.ecf.zevent.dto;

import com.ecf.zevent.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class StreamerDTO {

    private String uuid;
    private StreamerPrivateData privateData;
    private StreamerPublicData publicData;
    private AuthenticationData authenticationData;
    private Rule rule;
    private StreamerStatus status;

    public String getUuid() {
        return uuid;
    }

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
}
