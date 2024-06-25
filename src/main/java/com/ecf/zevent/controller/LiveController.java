package com.ecf.zevent.controller;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/live")
public class LiveController {
    @GetMapping(path = "/id", produces = "application/hal+json")
    public ResponseEntity<Live> getStreamer() {
        return ResponseEntity.ok(new Live());
    }

}
