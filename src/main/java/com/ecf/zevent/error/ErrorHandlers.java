package com.ecf.zevent.error;

import com.ecf.zevent.controller.AuthController;
import com.ecf.zevent.dto.StreamerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandlers {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandlers.class);

    public static List<String> getErrorMessages(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                errors.add(error.getDefaultMessage());
            });
        }
        return errors;
    }
}
