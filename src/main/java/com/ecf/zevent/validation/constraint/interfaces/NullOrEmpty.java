package com.ecf.zevent.validation.constraint.interfaces;

import com.ecf.zevent.validation.NullOrEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NullOrEmptyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrEmpty {
    String message() default "La valeur doit être null ou une string vide";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
