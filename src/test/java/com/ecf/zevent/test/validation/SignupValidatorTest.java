package com.ecf.zevent.test.validation;

import com.ecf.zevent.dto.SignupDTO;

import com.ecf.zevent.test.util.StreamerDataGenerator;
import com.ecf.zevent.test.utils.DateUtils;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class SignupValidatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(SignupValidatorTest.class);

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
    public void testValidSignupDTO() {
        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        signupDTO.setChannel("John's Channel");

        Set<ConstraintViolation<SignupDTO>> violations = this.validator.validate(signupDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testDateBirthValid() {

        LocalDate dateAgeMin = DateUtils.dateAgeMin();
        LocalDate dateAgeMax = DateUtils.dateAgeMax();

        SignupDTO signupDTO = StreamerDataGenerator.newSignupDTO();
        signupDTO.setBirthDate(dateAgeMax);
        Set<ConstraintViolation<SignupDTO>> violations = this.validator.validate(signupDTO);
        assertTrue(violations.isEmpty());


        signupDTO.setBirthDate(dateAgeMin);
        violations = this.validator.validate(signupDTO);
        assertTrue(violations.isEmpty());

        signupDTO.setBirthDate(dateAgeMin.plusDays(1));
        violations = this.validator.validate(signupDTO);
        assertTrue(violations.size() == 1);
        assertEquals("L'age doit être compris entre 13 et 80", violations.iterator().next().getMessage());

        signupDTO.setBirthDate(dateAgeMax.minusYears(1));
        violations = this.validator.validate(signupDTO);
        assertTrue(violations.size() == 1);
        assertEquals("L'age doit être compris entre 13 et 80", violations.iterator().next().getMessage());

    }
}
