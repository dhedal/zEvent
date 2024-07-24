package com.ecf.zevent.service;

import com.ecf.zevent.model.Streamer;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final static String EMAIL_FOR_TEST = "client4test@yahoo.com";
    private final static String EMAIL_FOR_TEST_PASSWORD = "ZEVENT4ecf++";

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeMessage(Streamer streamer, String password) throws MessagingException {
        LOG.debug("## sendWelcomeMessage");
        try {
            final String link = "http://localhost:8080/signin";
            String subject = "Bienvenue sur Z-Event";
            String text = String.format("""
                    Bonjour %s
                    Votre compte a été créé avec succès.    
                            
                    Pseudo : %s
                    Mot de passe provisoire: %s
                            
                    Veuiller vous connecter à l'application web en utilisant le lien suivant :
                    [%s] 
                            
                    Cordialement,
                    L'équipe Z-Event           
                    """.formatted(
                    streamer.getPrivateData().getFirstName(),
                    streamer.getPublicData().getPseudo(),
                    password,
                    link));
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(EMAIL_FOR_TEST);
            helper.setSubject(subject);
            helper.setText(text);

            this.mailSender.send(message);
        } catch (MailException | MessagingException e) {
            LOG.error("Failed to send email: " + e.getMessage());
        }
    }

    public boolean isMailValid(String email) {
        LOG.debug("## isMailValid");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(EMAIL_FOR_TEST);
            helper.setSubject("Test Email");
            helper.setText("This is a test email to verify your email address.");

            mailSender.send(message);
            return true;
        } catch (MailException | MessagingException e) {
            LOG.error("Failed to send email: " + e.toString());
        }
        return false;
    }
}
