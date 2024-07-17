package com.ecf.zevent.controller;

import com.ecf.zevent.dto.RuleAndStatusListDTO;
import com.ecf.zevent.dto.StreamerDTO;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.service.StreamerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/streamer")
public class StreamerController {
    private static final Logger LOG = LoggerFactory.getLogger(StreamerController.class);

    @Autowired
    private StreamerService streamerService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamerDTO> saveStreamer(@RequestBody StreamerDTO streamerDTO) {
        LOG.info(streamerDTO.toString());
        Streamer streamer = StreamerDTO.parseStreamerDTOToStreamer(streamerDTO);
        streamer = this.streamerService.save(streamer);
        return ResponseEntity.ok(StreamerDTO.parse(streamer));
    }

    @GetMapping(path = "/id", produces = "application/hal+json")
    public ResponseEntity<Streamer> getStreamer() {
        return ResponseEntity.ok(new Streamer());
    }

    @GetMapping(path = "/pseudo/list", produces = "application/hal+json")
    public ResponseEntity<List<String>> getPseudoList() {
        List<String> pseudos = this.streamerService.getPseudoList();
        return ResponseEntity.ok(pseudos);
    }

    @GetMapping(path = "/list", produces = "application/hal+json")
    public ResponseEntity<List<StreamerDTO>> getStreamerList() {
        List<Streamer> streamers = this.streamerService.listAll();
        return ResponseEntity.ok(StreamerDTO.parseStreamerListToStreamerDTOList(this.streamerService.listAll()));
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

    @GetMapping(path = "/rule-and-status-list", produces = "application/hal+json")
    public ResponseEntity<RuleAndStatusListDTO> getRuleAndStatusList() {
        return ResponseEntity.ok(new RuleAndStatusListDTO());
    }

    //TODO: créer un validateur DTO avec javax.validation
}
