package com.ecf.zevent.controller;

import com.ecf.zevent.service.PasswordResetTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {
    private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordController.class);
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public ResetPasswordController(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping(value = "/api/reset-password")
    public String resetPassword(@RequestParam("token") String token) {
        LOG.debug("## resetPassword");
        LOG.debug(token);
        String redirect = "redirect:/pages/resetPassword.html";
        if(this.passwordResetTokenService.isTokenValid(token)) {
            redirect += ("?token=" + token);
        }
        return redirect;
    }

}
