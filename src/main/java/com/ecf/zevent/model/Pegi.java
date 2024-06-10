package com.ecf.zevent.model;

public enum Pegi {
    PEGI_3(1),
    PEGI_7(2),
    PEGI_12(3),
    PEGI_16(4),
    PEGI_18(5);

    private Integer key;
    Pegi(Integer key) {
        this.key = key;
    }

    public Integer getKey() { return this.key;}

}
