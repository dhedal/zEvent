package com.ecf.zevent.test.service;

import com.ecf.zevent.dto.SignupDTO;
import com.ecf.zevent.dto.StreamerDTO;
import com.ecf.zevent.model.AuthenticationData;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPrivateData;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.model.enumerations.Rule;
import com.ecf.zevent.model.enumerations.StreamerStatus;
import com.ecf.zevent.service.AuthService;
import com.ecf.zevent.service.StreamerService;
import com.ecf.zevent.test.util.StreamerDataGenerator;
import com.ecf.zevent.test.utils.DateUtils;
import com.ecf.zevent.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(AuthServiceTest.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private StreamerService streamerService;

    @Test
    public void testSaveSignupDTO() {
        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        Streamer streamer = this.createNewStreamerBySignupDTO(signupDTO);

        StreamerPrivateData privateData = streamer.getPrivateData();
        assertEquals(privateData.getFirstName(), signupDTO.getFirstName());
        assertEquals(privateData.getLastName(), signupDTO.getLastName());

        StreamerPublicData publicData = streamer.getPublicData();
        assertEquals(publicData.getPseudo(), signupDTO.getPseudo());
        assertEquals(publicData.getBirthDate(), signupDTO.getBirthDate());
        assertEquals(publicData.getChannel(), signupDTO.getChannel());

        assertEquals(streamer.getRule(), Rule.STREAMER);
        assertEquals(streamer.getStatus(), StreamerStatus.REGISTRATION_REQUEST);

    }

    @Test
    public void testAuthentication(){
        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        Streamer streamer = this.createNewStreamerBySignupDTO(signupDTO);

        AuthenticationData authData = this.authService.authentication(signupDTO.getEmail(), "OTHER_PASSWORD");
        assertNull(authData);

        authData = this.authService.authentication(signupDTO.getEmail(), PasswordUtil.generateRandomPassword());
        assertNotNull(authData);
        assertEquals(authData.getId(), streamer.getAuthenticationData().getId());
    }

    @Test
    public void testIsEmailExist(){
        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        Streamer streamer = this.createNewStreamerBySignupDTO(signupDTO);

        assertTrue(this.authService.isEmailExist(signupDTO.getEmail()));

        AuthenticationData authData = this.authService.findByEmail(signupDTO.getEmail());
        assertNotNull(authData);
        assertEquals(authData.getId(), streamer.getAuthenticationData().getId());
    }

    @Test
    public void testIsPseudoExist(){
        System.out.println(this.authService.encode("ZE2++admin4++"));
        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        Streamer streamer = this.createNewStreamerBySignupDTO(signupDTO);

        assertTrue(this.authService.isPseudoExist(signupDTO.getPseudo()));

        AuthenticationData authData = this.authService.findByPseudo(signupDTO.getPseudo());
        assertNotNull(authData);
        assertEquals(authData.getId(), streamer.getAuthenticationData().getId());
    }

    @Test
    public void testChangePassword() {
        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        this.createNewStreamerBySignupDTO(signupDTO);

        final String newPassword = "NEW_PASSWORD";
        assertTrue(this.authService.changePassword(signupDTO.getEmail(), newPassword, PasswordUtil.generateRandomPassword()));

        AuthenticationData authData = this.authService.authentication(signupDTO.getEmail(), PasswordUtil.generateRandomPassword());
        assertNull(authData);

        authData = this.authService.authentication(signupDTO.getEmail(), newPassword);
        assertNotNull(authData);


    }

    private Streamer createNewStreamerBySignupDTO(SignupDTO signupDTO) {
        assertTrue(this.authService.save(signupDTO));
        AuthenticationData authData = this.authService.findByEmail(signupDTO.getEmail());
        assertNotNull(authData);
        assertNotNull(authData.getId());
        assertEquals(authData.getPassword(), this.authService.encode(PasswordUtil.generateRandomPassword()));

        Streamer streamer = this.streamerService.findByAuthenticationDataId(authData.getId());
        StreamerServiceTest.assertNewStreamer(streamer);

        return streamer;
    }



}
