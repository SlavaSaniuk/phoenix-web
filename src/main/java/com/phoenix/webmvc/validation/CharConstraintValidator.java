package com.phoenix.webmvc.validation;

import com.phoenix.webmvc.validation.annotations.Char;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CharConstraintValidator implements ConstraintValidator<Char, Character> {

    private char[] available_chars = new char[]{};
    private char incorrect = '\u0000';

    @Override
    public void initialize(Char constraintAnnotation) {
        this.available_chars = constraintAnnotation.available();
        this.incorrect = constraintAnnotation.incorrect();
    }

    @Override
    public boolean isValid(Character value, ConstraintValidatorContext context) {

        //Check on null and invalid character
        if (value  == null || value == this.incorrect) return false;

        for (char c : this.available_chars) {
            if(c == value) return true;
        }

        return false;
    }


}
