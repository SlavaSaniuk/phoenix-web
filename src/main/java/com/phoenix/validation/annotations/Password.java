package com.phoenix.validation.annotations;

import com.phoenix.validation.PasswordConstraintValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@NotEmpty(message = "{notempty.Account.password}")
public @interface Password {

    String message() default "Password field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
