package com.ecf.zevent.controller;

import com.ecf.zevent.dto.StreamerDTO;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.service.StreamerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/streamer")
public class StreamerController {
    private static final Logger LOG = LoggerFactory.getLogger(StreamerController.class);

    @Autowired
    private StreamerService streamerService;

    @GetMapping(path = "/id", produces = "application/hal+json")
    public ResponseEntity<Streamer> getStreamer() {
        return ResponseEntity.ok(new Streamer());
    }

    @GetMapping(path = "/pseudo/list", produces = "application/hal+json")
    public ResponseEntity<List<String>> getPseudoList() {
        List<String> pseudos = this.streamerService.getPseudoList();
        System.out.println(pseudos);
        return ResponseEntity.ok(pseudos);
    }

    @GetMapping(path = "/pseudo/{pseudo}", produces = "application/hal+json")
    public ResponseEntity<StreamerDTO> getStreamerByPseudo(@PathVariable String pseudo){
        try {
            if(pseudo == null || pseudo.isEmpty()) throw new InvalidParameterException("le paramètre pseudo est invalide!!!");
            Streamer streamer = this.streamerService.findByPseudo(pseudo);
            if(streamer == null) throw new ResourceNotFoundException("la resource streamer est introuvable !!!");
            return ResponseEntity.ok(StreamerDTO.parse(streamer));
        } catch (Exception ex) {
            LOG.error(ex.toString());
        }

        return ResponseEntity.ok(StreamerDTO.getEmpty());

    }
}
