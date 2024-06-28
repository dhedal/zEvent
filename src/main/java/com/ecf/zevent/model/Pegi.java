package com.ecf.zevent.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Pegi {
    PEGI_3(1, "PEGI 3", ""),
    PEGI_7(2, "PEGI 7", ""),
    PEGI_12(3, "PEGI 12", ""),
    PEGI_16(4, "PEGI 16", ""),
    PEGI_18(5, "PEGI 18", "");

    private Integer key;
    private String label;
    private String description;
    Pegi(Integer key, String label, String description) {
        this.key = key;
        this.label = label;
        this.description = description;
    }

    public Integer getKey() { return this.key;}

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
