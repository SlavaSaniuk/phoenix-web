package com.phoenix.webmvc.validation;

import com.phoenix.webmvc.formatters.LocalDateFormatter;
import com.phoenix.webmvc.validation.annotations.ValidLocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * This class contains validation logic for {@link ValidLocalDate} annotation.
 */
public class ValidLocalDateConstraintValidator implements ConstraintValidator<ValidLocalDate, LocalDate> {

    private final LocalDate INVALID = LocalDateFormatter.INVALID;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return false;
        else return !value.equals(INVALID);

    }

}
