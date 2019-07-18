package com.phoenix.webmvc.validation.annotations;

import com.phoenix.webmvc.validation.CharConstraintValidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Char} annotation used to mark character fields for validation.
 * {@link CharConstraintValidator} contains validation logic.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CharConstraintValidator.class)
public @interface Char {

    String message() default "Character fields is not set";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Specify chars that must be appears.
     * @return - Chars array.
     */
    char[] available();

    /**
     * Specify incorrect character to map incorrect characters with this value.
     * @return - '\u0000' by default.
     */
    char incorrect() default '\u0000';
}
