package com.ecf.zevent.controller;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;
import com.ecf.zevent.service.LiveService;
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

    @Autowired
    private LiveService liveService;

    @GetMapping(path = "/id", produces = "application/hal+json")
    public ResponseEntity<Live> getStreamer() {
        return ResponseEntity.ok(new Live());
    }

    @GetMapping(path = "/thematique/list", produces = "application/hal+json")
    public ResponseEntity<List<ThematiqueType>> getThematiqueType() {
        return ResponseEntity.ok(this.liveService.getThematiqueList());
    }

    @GetMapping(path = "/list/param/{date}/{thematique}/{streamer}", produces = "application/hal+json")
    public ResponseEntity<List<Live>> getLivesBy(
            @PathVariable String date,
            @PathVariable String thematique,
            @PathVariable String streamer) {
        String none = "NONE";
        if("NONE".equals(date) && "NONE".equals(thematique) && "NONE".equals(streamer)) return ResponseEntity.ok(List.of());
        LocalDate localDate = LocalDate.parse(date);
        ThematiqueType thematiqueType = ThematiqueType.getByLabel(thematique);
        List<Live> lives = this.liveService.findLivesBy(localDate, thematiqueType, streamer);
        return ResponseEntity.ok(lives);
    }

    @GetMapping(path = "/list/dateStart/greaterThanEquals", produces = "application/hal+json")
    public ResponseEntity<List<Live>> getTodayAndUpcomingLives(){
        List<Live> lives = this.liveService.findByDateStartGreaterThanEqual(LocalDateTime.now());
        return ResponseEntity.ok(lives);
    }

}
