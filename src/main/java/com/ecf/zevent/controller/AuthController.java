package com.ecf.zevent.controller;

import com.ecf.zevent.dto.AuthDataResponse;
import com.ecf.zevent.dto.AuthenticationDataDTO;
import com.ecf.zevent.dto.CheckIsEmailAndPseudoUniqueResponse;
import com.ecf.zevent.dto.SignupDTO;
import com.ecf.zevent.model.AuthenticationData;
import com.ecf.zevent.service.AuthService;
import com.ecf.zevent.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveSignupData(@Valid @RequestBody SignupDTO signupDTO, BindingResult bindingResult) {
        LOG.debug("## saveSignupData");
        try {
            if(bindingResult.hasErrors()) {
                List<String> errors = new ArrayList<>();
                bindingResult.getAllErrors().forEach(error -> {
                    errors.add(error.getDefaultMessage());
                });
                LOG.debug(errors.stream().collect(Collectors.joining("\n")));
            }
            else {
                LOG.debug(signupDTO.toString());
                boolean response = this.authService.save(signupDTO);
                return ResponseEntity.ok(response);
            }

        } catch (Exception e){
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthDataResponse> authenticate(@RequestBody AuthenticationDataDTO authDataDTO) {
        LOG.debug("## authenticate");
        try {

            AuthenticationData authData = this.authService.authentication(authDataDTO.getEmail(), authDataDTO.getPassword());
            String jwtToken = this.jwtService.generateToken(authData);
            LOG.debug("jwtToken : " + jwtToken);
            AuthDataResponse authDataResponse = new AuthDataResponse()
                    .setToken(jwtToken)
                    .setExpiresIn(this.jwtService.getJwtExpiration());
            return ResponseEntity.ok(authDataResponse);

        } catch (Exception ex) {
            LOG.error(ex.toString());
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(path = "/unique/{email}/{pseudo}", produces = "application/hal+json")
    public ResponseEntity<CheckIsEmailAndPseudoUniqueResponse> isEmailAndPseudoUniques(
            @PathVariable(required = true) String email,
            @PathVariable(required = true) String pseudo) {
        LOG.debug("## CheckIsEmailAndPseudoUniqueResponse");
        try {
            CheckIsEmailAndPseudoUniqueResponse response = new CheckIsEmailAndPseudoUniqueResponse(
                    !this.authService.isEmailExist(email),
                    !this.authService.isPseudoExist(pseudo)
            );
            LOG.debug(response.toString());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            LOG.error(ex.toString());
            return ResponseEntity.ok(new CheckIsEmailAndPseudoUniqueResponse());
        }
    }


}
