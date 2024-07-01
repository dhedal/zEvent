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

    /**
     *
      * @param localDate
     * @return
     */
    public List<Live> findLivesByDate(LocalDate localDate) {
        List<Live> lives = this.repository.findAllByDate(localDate);
        return lives;

    }

    /**
     *
     * @param streamer
     * @return
     */
    public List<Live> findLivesByStreamer(Streamer streamer) {
        List<Live> lives = this.repository.findAllByStreamer(streamer);
        return lives;
    }

    /**
     *
     * @param theme
     * @return
     */
    public List<Live> findLivesByTheme(ThematiqueType theme){
        List<Live> lives = this.repository.findLivesByTheme(theme);
        return lives;
    }

    /**
     *
     * @param themes
     * @return
     */
    public List<Live> findLivesByThemes(List<ThematiqueType> themes){
        List<Live> lives = this.repository.findLivesByThemes(themes);
        return lives;
    }

    /**
     *
     * @return
     */
    public List<ThematiqueType> getThematiqueList() {
        return Stream.of(ThematiqueType.values()).toList();
    }

    /**
     *
     * @param date
     * @param thematiqueType
     * @param streamerPseudo
     * @return
     */
    public List<Live> findLivesBy(LocalDate date, ThematiqueType thematiqueType, String streamerPseudo) {
        Streamer streamer = null;
        if(streamerPseudo != null && !streamerPseudo.isEmpty()){
            streamer = this.streamerService.findByPseudo(streamerPseudo);
        }
        return this.repository.findLivesBy(date, thematiqueType, streamer);
    }

    /**
     *
     * @param dateTime
     * @return
     */
    public List<Live> findByDateStartGreaterThanEqual(LocalDateTime dateTime) {
        LOG.info(dateTime.toString());
        return this.repository.findByDateStartGreaterThanEqual(dateTime);
    }

}
