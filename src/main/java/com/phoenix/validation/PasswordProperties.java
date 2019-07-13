package com.phoenix.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PasswordProperties {

    //LOGGER
    @SuppressWarnings("FieldCanBeLocal")
    private Logger LOGGER = LoggerFactory.getLogger(PasswordProperties.class);

    //Password properties
    private int password_min_length;
    private String password_uppercase;
    private String password_lowercase;
    private String password_numbers;
    private String password_special;

    /**
     * Create new PasswordProperties properties bean that contain all password constraints e.g.
     * minimum password length, must contain uppercase letters, ...
     * @param env - {@link Environment} - with defined password properties.
     */
    public PasswordProperties(@NonNull Environment env) {
        LOGGER.debug("Start to create " +getClass().getName() +" properties bean.");

        LOGGER.debug(getClass().getName() +": Start to initialize password properties.");
        //Set password min length properties
        String password_min_length = env.getProperty("com.phoenix.security.password.min_length");
        if (!(password_min_length == null)) {
            try {
                this.password_min_length = Integer.parseInt(password_min_length);
            }catch (NumberFormatException exc) {
                this.password_min_length = 0;
            }
        }else this.password_min_length = 0;

        //Set another properties
        this.password_uppercase = env.getProperty("com.phoenix.security.password.uppercase_letters");
        this.password_lowercase = env.getProperty("com.phoenix.security.password.lowercase_letters");
        this.password_numbers = env.getProperty("com.phoenix.security.password.numbers");
        this.password_special = env.getProperty("com.phoenix.security.password.special_characters");

        LOGGER.debug(getClass().getName() +": password properties was initialized.");
    }

    /*
        Standard getters
     */
    public int getPasswordMinLength() {        return password_min_length;    }
    public String getPasswordUppercase() {        return password_uppercase;    }
    public String getPasswordLowercase() {        return password_lowercase;    }
    public String getPasswordNumbers() {        return password_numbers;    }
    public String getPasswordSpecial() {        return password_special;    }
}
