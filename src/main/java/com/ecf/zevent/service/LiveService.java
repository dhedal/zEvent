package com.ecf.zevent.service;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;
import com.ecf.zevent.repository.LiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LiveService extends AbstractService<LiveRepository, Live> {

    @Autowired
    private StreamerService streamerService;

    @Autowired
    public LiveService(LiveRepository repository) {
        super(repository);
    }

    public List<Live> findLivesByDate(LocalDate localDate) {
        List<Live> lives = this.repository.findAllByDate(localDate);
        return lives;

    }

    public List<Live> findLivesByStreamer(Streamer streamer) {
        List<Live> lives = this.repository.findAllByStreamer(streamer);
        return lives;
    }

    public List<Live> findLivesByTheme(ThematiqueType theme){
        List<Live> lives = this.repository.findAllByTheme(theme);
        return lives;
    }

    public List<ThematiqueType> getThematiqueList() {
        return Stream.of(ThematiqueType.values()).toList();
    }

    public List<Live> findLivesBy(LocalDate date, ThematiqueType thematiqueType, String streamerPseudo) {
        Streamer streamer = null;
        if(streamerPseudo != null && !streamerPseudo.isEmpty()){
            streamer = this.streamerService.findByPseudo(streamerPseudo);
            System.out.println(streamer);
        }
        return this.repository.findLivesBy(date, thematiqueType, streamer);
    }

    public List<Live> findByDateStartGreaterThanEqual(LocalDateTime dateTime) {
        return this.repository.findByDateStartGreaterThanEqual(dateTime);
    }

}
