package com.ecf.zevent.test.validation;

import com.ecf.zevent.dto.StreamerDTO;
import com.ecf.zevent.test.util.StreamerDataGenerator;
import com.ecf.zevent.validation.constraint.interfaces.Create;
import com.ecf.zevent.validation.constraint.interfaces.Update;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class StreamerDTOValidatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(StreamerDTOValidatorTest.class);

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidStreamerDTOCreate() {
        StreamerDTO streamerDTO = StreamerDataGenerator.newStreamerDTO();

        Set<ConstraintViolation<StreamerDTO>> violations = this.validator.validate(streamerDTO, Create.class);
        assertTrue(violations.isEmpty());

        streamerDTO.setUuid(UUID.randomUUID().toString());
        violations = this.validator.validate(streamerDTO, Create.class);
        assertTrue(violations.size() == 1);
        assertEquals("L'UUID doit être null pour la création d'un nouveau streamer", violations.iterator().next().getMessage());

        streamerDTO.setUuid("");
        violations = this.validator.validate(streamerDTO, Create.class);
        assertTrue(violations.isEmpty());
        }

    @Test
    public void testValidStreamerDTOUpdate() {
        StreamerDTO streamerDTO = StreamerDataGenerator.newStreamerDTO();
        streamerDTO.setUuid(UUID.randomUUID().toString());

        Set<ConstraintViolation<StreamerDTO>> violations = this.validator.validate(streamerDTO, Update.class);
        assertTrue(violations.isEmpty());

        streamerDTO.setUuid(null);
        violations = this.validator.validate(streamerDTO, Update.class);
        assertTrue(violations.size() == 1);
        assertEquals("L'UUID est obligatoire pour la mis à jour des donnée", violations.iterator().next().getMessage());

        streamerDTO.setUuid("");
        violations = this.validator.validate(streamerDTO, Update.class);
        assertTrue(violations.size() == 1);
        assertEquals("L'UUID est obligatoire pour la mis à jour des donnée", violations.iterator().next().getMessage());

        streamerDTO.setUuid("qsdfjqsf-qsf-qsfqsf--qsdfqsf-qsfqsfqsf-qsdf-qs");
        violations = this.validator.validate(streamerDTO, Update.class);
        assertTrue(violations.size() == 1);
        assertEquals("L'UUID est obligatoire pour la mis à jour des donnée", violations.iterator().next().getMessage());

    }
}
