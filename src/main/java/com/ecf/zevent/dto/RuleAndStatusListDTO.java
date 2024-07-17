package com.ecf.zevent.dto;

import com.ecf.zevent.model.Rule;
import com.ecf.zevent.model.StreamerStatus;

public class RuleAndStatusListDTO {
    private Rule[] rules;
    private StreamerStatus [] status;

    public RuleAndStatusListDTO() {
        this.rules = Rule.values();
        this.status = StreamerStatus.values();
    }

    public Rule[] getRules() {
        return rules;
    }

    public StreamerStatus[] getStatus() {
        return status;
    }
}
