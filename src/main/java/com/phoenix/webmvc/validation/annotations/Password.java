package com.phoenix.webmvc.validation.annotations;

import com.phoenix.webmvc.validation.PasswordConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;

/**
 * {@link Password} annotation used to mark password fields for validation. Password constraint may be defined
 * in "security.properties" file with "com.phoenix.security.password.*" keys or with this annotations methods.
 * {@link PasswordConstraintValidator} class holds validation logic for password fields.
 * Note: Properties defined in "security.properties" prefer than annotations properties.
 */
@SuppressWarnings("unused")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@NotEmpty(message = "{not_empty.Account.password}")
public @interface Password {

    /**
     * Default validation message if password field is not valid.
     * @return {@link String} password error message.
     */
    String message() default "Password field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * Minimum password length in characters. Default is 8 characters.
     * @return - {@link Integer} minimum password length.
     */
    int min_length() default 8;

    /**
     * Value define if user password must contain uppercase letters. Default value - false.
     * @return {@link Boolean} constraints.
     */
    boolean uppercase() default false;

    /**
     * Value define if user password must contain lowercase letters. Default value - true.
     * @return {@link Boolean} constraints.
     */
    boolean lowercase() default true;

    /**
     * Value define if user password must contain numbers. Default value - false.
     * @return {@link Boolean} constraints.
     */
    boolean numbers() default false;

    /**
     * Value define if user password must contain special characters e.g. "!@#$%^&". Default value - false.
     * @return {@link Boolean} constraints.
     */
    boolean special() default false;
}
