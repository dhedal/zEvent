package com.ecf.zevent.controller;

import com.ecf.zevent.model.Streamer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/streamer")
public class StreamerController {

    @GetMapping(path = "/id", produces = "application/hal+json")
    public ResponseEntity<Streamer> getStreamer() {
        return ResponseEntity.ok(new Streamer());
    }

    @GetMapping(path = "/list/pseudo", produces = "application/hal+json")
    public ResponseEntity<List<String>> getPseudoList() {
        List<String> pseudos = List.of("david", "hedgar", "alexandre");
        System.out.println(pseudos);
        return ResponseEntity.ok(pseudos);
    }
}
