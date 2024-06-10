package com.ecf.zevent.model;

public enum Rule {

    USER(1), STREAMER(2), ADMIN(3);

    private Integer key;

    Rule(Integer key){
        this.key = key;
    }

    public Integer getKey() {return this.key;}


}
