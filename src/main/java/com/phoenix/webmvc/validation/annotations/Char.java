package com.phoenix.webmvc.validation.annotations;

import com.phoenix.webmvc.validation.CharConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CharConstraintValidator.class)
public @interface Char {

    String message() default "Character fields is not set";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    char[] available() default {};
    char incorrect() default '\u0000';
}
