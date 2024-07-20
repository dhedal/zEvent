package com.ecf.zevent.controller;

import com.ecf.zevent.dto.RuleAndStatusListDTO;
import com.ecf.zevent.dto.StreamerDTO;
import com.ecf.zevent.error.ErrorHandlers;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.service.StreamerService;
import com.ecf.zevent.validation.constraint.interfaces.Create;
import com.ecf.zevent.validation.constraint.interfaces.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/streamer")
public class StreamerController {
    private static final Logger LOG = LoggerFactory.getLogger(StreamerController.class);

    @Autowired
    private StreamerService streamerService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamerDTO> create(@Validated(Create.class) @RequestBody StreamerDTO streamerDTO, BindingResult bindingResult) {
        LOG.debug("## create");
        try {
            List<String> errors = ErrorHandlers.getErrorMessages(bindingResult);
            if(!errors.isEmpty()) {
                LOG.debug(errors.stream().collect(Collectors.joining("\n")));
                return ResponseEntity.ok(new StreamerDTO());
            }

            Streamer streamer = this.streamerService.create(streamerDTO.toStreamer());
            if(Objects.isNull(streamer)) return ResponseEntity.ok(new StreamerDTO());
            return ResponseEntity.ok(StreamerDTO.toStreamerDTO(streamer));

        } catch (Exception e){
            LOG.error(e.toString());
            return ResponseEntity.ok(new StreamerDTO());
        }
    }

    @PatchMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamerDTO> update(@Validated(Update.class) @RequestBody StreamerDTO streamerDTO, BindingResult bindingResult) {
        LOG.debug("## update");
        try {
            List<String> errors = ErrorHandlers.getErrorMessages(bindingResult);
            if(!errors.isEmpty()) {
                LOG.debug(errors.stream().collect(Collectors.joining("\n")));
                return ResponseEntity.ok(new StreamerDTO());
            }

            Streamer streamer = this.streamerService.update(streamerDTO.toStreamer());
            if(Objects.isNull(streamer)) return ResponseEntity.ok(new StreamerDTO());
            return ResponseEntity.ok(StreamerDTO.toStreamerDTO(streamer));

        } catch (Exception e){
            LOG.error(e.toString());
            return ResponseEntity.ok(new StreamerDTO());
        }
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
        try{
            List<Streamer> streamers = this.streamerService.listAll();
            return ResponseEntity.ok(StreamerDTO.toStreamerDTOs(streamers));
        } catch(Exception e) {
            LOG.error(e.toString());
        }
        List<Streamer> streamers = this.streamerService.listAll();
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(path = "/pseudo/{pseudo}", produces = "application/hal+json")
    public ResponseEntity<StreamerDTO> getStreamerByPseudo(@PathVariable String pseudo){
        try {
            if(pseudo == null || pseudo.isEmpty()) throw new InvalidParameterException("le paramètre pseudo est invalide!!!");
            Streamer streamer = this.streamerService.findByPseudo(pseudo);
            if(streamer == null) throw new ResourceNotFoundException("la resource streamer est introuvable !!!");
            return ResponseEntity.ok(new StreamerDTO());
        } catch (Exception ex) {
            LOG.error(ex.toString());
        }

        return ResponseEntity.ok(new StreamerDTO());

    }

    @GetMapping(path = "/rule-and-status-list", produces = "application/hal+json")
    public ResponseEntity<RuleAndStatusListDTO> getRuleAndStatusList() {
        return ResponseEntity.ok(new RuleAndStatusListDTO());
    }

    //TODO: créer un validateur DTO avec javax.validation
}
