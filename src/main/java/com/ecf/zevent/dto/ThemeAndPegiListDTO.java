package com.ecf.zevent.dto;

import com.ecf.zevent.model.Pegi;
import com.ecf.zevent.model.ThematiqueType;

public class ThemeAndPegiListDTO {

    private ThematiqueType [] themes;
    private Pegi[] pegis;

    public ThemeAndPegiListDTO() {
        this.themes = ThematiqueType.values();
        this.pegis = Pegi.values();
    }

    public ThematiqueType[] getThemes() {
        return themes;
    }

    public Pegi[] getPegis() {
        return pegis;
    }
}
