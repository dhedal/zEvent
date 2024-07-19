package com.ecf.zevent.validation.constraint;

import com.ecf.zevent.validation.AgeValidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {
    String message() default "L'age doit être compris entre {ageMin} et {ageMax}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int ageMin() default 0;
    int ageMax() default 0;
}
