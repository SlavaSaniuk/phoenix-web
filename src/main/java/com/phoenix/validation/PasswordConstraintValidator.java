package com.phoenix.validation;

import com.phoenix.validation.annotations.Password;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * {@link PasswordConstraintValidator} bean is validation bean that's define
 * business logic for validating users passwords. Bean holds password properties defined
 * in security.properties file in this inner {@link PasswordConstraintValidator#setProperties(PasswordProperties)}
 * bean.
 */
@Component
public class PasswordConstraintValidator implements ConstraintValidator<Password, String>{

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordConstraintValidator.class);

    //Spring beans
    private PasswordProperties properties; //Autowired

    //Local password constraints
    private int min_password_length = 8;
    private boolean uppercase_letter = false;
    private boolean lowercase_letter = true;
    private boolean number = false;
    private boolean special_character = false;

    /**
     * Default constructor. Construct new PasswordConstraintValidator bean.
     */
    public PasswordConstraintValidator() {
        LOGGER.debug("Start to create " +getClass().getName() +" class.");
    }

    @Override
    public void initialize(Password constraintAnnotation) {

        LOGGER.warn("Password properties: " +this.properties);

        //Set minimum password length
        int i = this.properties.getPasswordMinLength();
        if (i > 0) this.min_password_length = i;
        else {
            i = constraintAnnotation.min_length();
            if (i > 0) this.min_password_length = i;
        }

        //Set uppercase letters
        String s = this.properties.getPasswordUppercase();
        if (s == null || s.isEmpty()) this.uppercase_letter = constraintAnnotation.uppercase();
        else if (s.equals("true") || s.equals("false")) this.uppercase_letter = Boolean.parseBoolean(s);
        else this.uppercase_letter = constraintAnnotation.uppercase();

        //Set lowercase letters
        s = this.properties.getPasswordLowercase();
        if (s == null || s.isEmpty()) this.lowercase_letter = constraintAnnotation.lowercase();
        else if (s.equals("true") || s.equals("false")) this.lowercase_letter = Boolean.parseBoolean(s);
        else this.lowercase_letter = constraintAnnotation.lowercase();

        //Set numbers
        s = this.properties.getPasswordNumbers();
        if (s == null || s.isEmpty()) this.number = constraintAnnotation.numbers();
        else if (s.equals("true") || s.equals("false")) this.number = Boolean.parseBoolean(s);
        else this.number = constraintAnnotation.numbers();

        //Set lowercase letters
        s = this.properties.getPasswordSpecial();
        if (s == null || s.isEmpty()) this.special_character = constraintAnnotation.special();
        else if (s.equals("true") || s.equals("false")) this.special_character = Boolean.parseBoolean(s);
        else this.special_character = constraintAnnotation.special();

        //Log properties
        LOGGER.debug(getClass().getName() +": minimum password length: " +this.min_password_length);
        LOGGER.debug(getClass().getName() +": must contain uppercase letters: " +this.uppercase_letter);
        LOGGER.debug(getClass().getName() +": must contain lowercase letters: " +this.lowercase_letter);
        LOGGER.debug(getClass().getName() +": must contain numbers: " +this.number);
        LOGGER.debug(getClass().getName() +": must contain special characters: " +this.special_character);
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

    /*
        Standard getters and setters
     */
    public int getMinPasswordLength() {        return min_password_length;    }
    public boolean isUppercaseLetter() {        return uppercase_letter;    }

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

    /**
     * Spring autowiring. Autowire {@link PasswordProperties} bean that
     * hold password constraints to this bean.
     * @param props - {@link PasswordProperties} bean.
     */
    @Autowired
    public void setProperties(PasswordProperties props) {
        LOGGER.debug("AUTOWIRING:" +props.getClass().getName() + " to " +getClass().getName());
        this.properties = props;
    }

}
