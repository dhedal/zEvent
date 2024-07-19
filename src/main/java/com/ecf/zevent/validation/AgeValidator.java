package com.ecf.zevent.validation;

import com.ecf.zevent.validation.constraint.ValidAge;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {
    private int ageMin = 0;
    private int ageMax = 0;
    @Override
    public void initialize(ValidAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.ageMin = constraintAnnotation.ageMin();
        this.ageMax = constraintAnnotation.ageMax();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if(birthDate == null) return false;
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();
        if(this.ageMin > 0 && age < this.ageMin) return false;
        if(this.ageMax > 0 && age > this.ageMax) return false;
        return true;
    }
}
