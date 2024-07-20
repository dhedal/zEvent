package com.ecf.zevent.validation;

import com.ecf.zevent.validation.constraint.ValidUUID;
import com.ecf.zevent.validation.constraint.interfaces.NullOrEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NullOrEmptyValidator  implements ConstraintValidator<NullOrEmpty, String> {
    @Override
    public void initialize(NullOrEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isEmpty();
    }
}
