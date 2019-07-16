package com.phoenix.webmvc.validation.annotations;

import com.phoenix.webmvc.validation.ValidLocalDateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLocalDateConstraintValidator.class)
public @interface ValidLocalDate {

    /**
     * Default validation message if local date field is not valid.
     * @return {@link String} local date error message.
     */
    String message() default "Password field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
