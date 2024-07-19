package com.ecf.zevent.model.enumerations;

import com.ecf.zevent.util.RuleDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = RuleDeserializer.class)
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

    public static Rule getByKey(int key) {
        if(key == STREAMER.key) return STREAMER;
        if(key == ADMIN.key) return ADMIN;
        return USER;
    }
}
