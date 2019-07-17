package units.webmvc.formatters;

import com.phoenix.webmvc.formatters.CharFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.format.Formatter;

import java.text.ParseException;

class CharFormatterTestCase {

    private static Formatter<Character> formatter;

    @BeforeAll
    static void setUpBeforeAll() {
        formatter = new CharFormatter();
    }

    @Test
    void parse_emptyString_shouldReturnDefaultCharacter() throws ParseException {
        Assertions.assertEquals('\u0000', formatter.parse("", null));
    }

    @Test
    void parse_nullableString_shouldReturnDefaultCharacter() throws ParseException {
        Assertions.assertEquals('\u0000', formatter.parse(null,null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"female", "feministka", "fen"})
    void parse_differentStrings_shouldReturnFirstCharacter(String parseable) throws ParseException {
        Assertions.assertEquals('f', formatter.parse(parseable, null));
    }

    @Test
    void print_nullCharacter_shouldReturnEmptyString() {
        Assertions.assertEquals("", formatter.print(null, null));
    }

    @Test
    void print_defaultCharacter_shouldReturnEmptyString() {
        Assertions.assertEquals("", formatter.print('\u0000', null));
    }

    @ParameterizedTest
    @ValueSource(chars = {'d', 'P', 'f'})
    void print_differentCharacter_shouldReturnNotEmptyString(char character) {
        Assertions.assertFalse(formatter.print(character, null).isEmpty());
    }
}
