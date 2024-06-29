package com.ecf.zevent.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Rule {

    USER(1, "user"), STREAMER(2, "streamer"), ADMIN(3, "admin");

    private Integer key;
    private String label;

    Rule(Integer key, String label){
        this.key = key;
        this.label = label;
    }

    public Integer getKey() {return this.key;}

    public String getLabel() {
        return label;
    }
}
