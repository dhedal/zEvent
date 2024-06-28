package com.ecf.zevent.controller;

import com.ecf.zevent.dto.LiveDTO;
import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.ThematiqueType;
import com.ecf.zevent.service.LiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/live")
public class LiveController {
    private static final Logger LOG = LoggerFactory.getLogger(LiveController.class);

    @Autowired
    private LiveService liveService;

    @GetMapping(path = "/id", produces = "application/hal+json")
    public ResponseEntity<Live> getStreamer() {
        return ResponseEntity.ok(new Live());
    }

    @GetMapping(path = "/thematique/list", produces = "application/hal+json")
    public ResponseEntity<List<ThematiqueType>> getThematiqueType() {
        try {
            return ResponseEntity.ok(this.liveService.getThematiqueList());
        } catch (Exception ex) {
            LOG.error(ex.toString());
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(path = "/list/param/{date}/{thematique}/{streamer}", produces = "application/hal+json")
    public ResponseEntity<List<LiveDTO>> getLivesBy(
            @PathVariable String date,
            @PathVariable String thematique,
            @PathVariable String streamer) {
        try {
            String none = "NONE";
            if ("NONE".equals(date) && "NONE".equals(thematique) && "NONE".equals(streamer)) {
                throw new RuntimeException("problèmes avec les paramètres de la rêquete");
            }

            LocalDate localDate = "NONE".equals(date) ? null : LocalDate.parse(date);
            ThematiqueType thematiqueType = "NONE".equals(thematique) ? null : ThematiqueType.getByLabel(thematique);
            List<Live> lives = this.liveService.findLivesBy(localDate, thematiqueType, streamer);
            return ResponseEntity.ok(LiveDTO.parse(lives));
        } catch (Exception ex) {
            LOG.error(ex.toString());
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(path = "/list/dateStart/greaterThanEquals", produces = "application/hal+json")
    public ResponseEntity<List<LiveDTO>> getTodayAndUpcomingLives(){
        try {
            List<Live> lives = this.liveService.findByDateStartGreaterThanEqual(LocalDateTime.now());
            return ResponseEntity.ok(LiveDTO.parse(lives));
        }catch(Exception ex){
            LOG.error(ex.toString());
        }
        return ResponseEntity.ok(List.of());
    }

}
