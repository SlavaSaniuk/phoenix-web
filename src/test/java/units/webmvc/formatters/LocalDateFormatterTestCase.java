package units.webmvc.formatters;

import com.phoenix.webmvc.formatters.LocalDateFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

class LocalDateFormatterTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateFormatterTestCase.class);

    private Formatter<LocalDate> formatter;

    @BeforeEach
    void setUpBeforeEach() {
        this.formatter = new LocalDateFormatter();
    }

    @AfterEach
    void tierDownAfterEach() {
        this.formatter = null;
    }

    @Test
    void parse_stringIsNull_shouldReturnInvalidLocalDate() throws ParseException {

        LocalDate expected = LocalDate.of(1,1,1);
        LOGGER.debug("Expected: " +expected.toString());

        Assertions.assertEquals(expected, this.formatter.parse(null, null));
    }

    @Test
    void parse_stringIsNotParseable_shouldReturnInvalidLocalDate() throws ParseException {

        LocalDate expected = LocalDate.of(1,1,1);
        LOGGER.debug("Expected: " +expected.toString());

        Assertions.assertEquals(expected, this.formatter.parse("asdasdrfcs", null));
    }

    @Test
    void parse_stringIsValid_shouldReturnLocalDate() throws ParseException {

        LocalDate expected = LocalDate.of(1997,1,1);
        LOGGER.debug("Expected: " +expected.toString());

        Assertions.assertEquals(expected, this.formatter.parse("1997-01-01", null));
    }

    @Test
    void parse_forRussianLocale_shouldReturnLocalDate() throws ParseException {

        LocalDate expected = LocalDate.of(1997,1,1);
        LOGGER.debug("Expected: " +expected.toString());

        Assertions.assertEquals(expected, this.formatter.parse("01-01-1997", new Locale("ru")));
    }

    @Test
    void parse_forAmericanLocale_shouldReturnLocalDate() throws ParseException {

        LocalDate expected = LocalDate.of(1997,2,1);
        LOGGER.debug("Expected: " +expected.toString());

        Assertions.assertEquals(expected, this.formatter.parse("02-01-1997", Locale.US));
    }

    @Test
    void parse_forEnglishLocale_shouldReturnLocalDate() throws ParseException {

        LocalDate expected = LocalDate.of(1997,1,1);
        LOGGER.debug("Expected: " +expected.toString());

        Assertions.assertEquals(expected, this.formatter.parse("01-01-1997", Locale.ENGLISH));
    }

    @Test
    void print_localDateIsNull_shouldReturnEmptyString() {
        Assertions.assertTrue(this.formatter.print(null, null).isEmpty());
    }

    @Test
    void print_invalidLocalDate_shouldReturnEmptyString() {
        Assertions.assertTrue(this.formatter.print(LocalDateFormatter.INVALID, null).isEmpty());
    }

    @Test
    void print_forRussianLocale_shouldReturnString() {
        Assertions.assertEquals("10-01-1997", this.formatter.print(LocalDate.of(1997, 1, 10), new Locale("ru")));
    }

    @Test
    void print_forAmericanLocale_shouldReturnString() {
        Assertions.assertEquals("01-10-1997", this.formatter.print(LocalDate.of(1997, 1, 10), Locale.US));
    }

    @Test
    void print_forEnglishLocale_shouldReturnString() {
        Assertions.assertEquals("10-01-1997", this.formatter.print(LocalDate.of(1997, 1, 10), Locale.ENGLISH));
    }
}
