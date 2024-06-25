package com.ecf.zevent.controller;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;
import com.ecf.zevent.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
