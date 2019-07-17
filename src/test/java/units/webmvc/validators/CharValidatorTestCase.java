package units.webmvc.validators;

import com.phoenix.webmvc.validation.CharConstraintValidator;
import com.phoenix.webmvc.validation.annotations.Char;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

class CharValidatorTestCase {

    private ConstraintValidator<Char, Character> validator = new CharConstraintValidator();



    @Test
    void isValid_characterIsNull_shouldReturnFalse() {
        Assertions.assertFalse(this.validator.isValid(null, null));
    }

    @Test
    void isValid_characterIsInvalid_shouldReturnFalse() {
        Assertions.assertFalse(this.validator.isValid('\u0000', null));
    }

    @Test
    void isValid_characterIsNotAvailable_shouldReturnFalse() {
        Char annotation = new Char() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public char[] available() {
                return new char[] {'S', 'M'};
            }

            @Override
            public char incorrect() {
                return '\u0000';
            }
        };

        this.validator.initialize(annotation);
        Assertions.assertFalse(this.validator.isValid('B', null));
    }

    @Test
    void isValid_characterIsAvailable_shouldReturnTrue() {
        Char annotation = new Char() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public char[] available() {
                return new char[] {'S', 'M'};
            }

            @Override
            public char incorrect() {
                return '\u0000';
            }
        };

        this.validator.initialize(annotation);
        Assertions.assertTrue(this.validator.isValid('M', null));
    }


}
