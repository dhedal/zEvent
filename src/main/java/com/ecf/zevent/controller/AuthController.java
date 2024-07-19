package com.ecf.zevent.controller;

import com.ecf.zevent.dto.SignupDTO;
import com.ecf.zevent.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveSignupData(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                List<String> errors = new ArrayList<>();
                bindingResult.getAllErrors().forEach(error -> {
                    errors.add(error.getDefaultMessage());
                });
                LOG.debug(errors.stream().collect(Collectors.joining("\n")));
                return ResponseEntity.ok(false);
            }
            LOG.debug(signupDTO.toString());
            boolean response = this.authService.save(signupDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(false);
    }
}
