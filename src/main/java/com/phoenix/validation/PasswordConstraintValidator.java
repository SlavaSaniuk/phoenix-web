package com.phoenix.validation;

import com.phoenix.validation.annotations.Password;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PasswordConstraintValidator implements ConstraintValidator<Password, String>, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordConstraintValidator.class);

    private int min_password_length = 8;
    private boolean uppercase_letter = false;
    private boolean lowercase_letter = true;
    private boolean number = false;
    private boolean special_character = false;

    public PasswordConstraintValidator() {
        LOGGER.debug("Create " +getClass().getName() +" class.");
    }

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        //Check on null
        if (value == null) return false;

        //Check whether password length less than minimum password length
        if(value.length() < this.min_password_length) return false;

        //Check whether password must contain uppercase characters
        if (this.uppercase_letter) {
            if(!value.matches(".*[A-Z]+.*")) return false;
        }

        //Check whether password must contain lowercase characters
        if (this.lowercase_letter) {
            if(!value.matches(".*[a-z]+.*")) return false;
        }

        //Check whether password must contain number
        if (this.number) {
            if(!value.matches(".*[0-9]+.*")) return false;
        }

        //Check whether password must contain special characters
        if (this.special_character) {
            return value.matches(".*\\W+.*");
        }

        return true;
    }


    //Getters and Setters
    public void setMinPasswordLength(@NonNull int min) {        if (min == 0 ) this.setMinPasswordLength(8);        this.min_password_length = min;    }
    public void setUppercaseLetter(@NonNull boolean uppercase) {
        this.uppercase_letter = uppercase;
    }
    public void setLowercaseLetter(@NonNull boolean lowercase_letter) {
        this.lowercase_letter = lowercase_letter;
    }
    public void setNumber(@NonNull boolean numbers) {
        this.number = numbers;
    }
    public void setSpecialCharacter(@NonNull boolean special_characters) {        this.special_character = special_characters;    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.debug(getClass().getName() +": Passwords minimum password length: " +this.min_password_length);
        LOGGER.debug(getClass().getName() +": Passwords must contain uppercase letters " +this.uppercase_letter);
        LOGGER.debug(getClass().getName() +": Passwords must contain lowercase letters: " +this.lowercase_letter);
        LOGGER.debug(getClass().getName() +": Passwords must contain numbers: " +this.number);
        LOGGER.debug(getClass().getName() +": Minimum must contain special characters: " +this.special_character);
    }
}
