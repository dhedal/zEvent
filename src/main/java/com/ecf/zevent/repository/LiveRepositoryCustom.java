package com.ecf.zevent.repository;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;

import java.time.LocalDate;
import java.util.List;

public interface LiveRepositoryCustom {
    public List<Live> findLivesBy(LocalDate date, ThematiqueType thematiqueType, Streamer streamer);
    public List<Live> findLivesByTheme(ThematiqueType thematiqueType);
    public List<Live> findLivesByThemes(List<ThematiqueType> thematiqueTypes);
}
