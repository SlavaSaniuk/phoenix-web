package com.phoenix.unittests.formatterstest;

import com.phoenix.webmvc.formatters.LocalDateFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.format.Formatter;

class LocalDateFormatterTestCase {

    private Formatter<LocalDate> formatter;

    @BeforeEach
    void setUpBeforeEach() {
        this.formatter = new LocalDateFormatter();
    }

    @AfterEach
    void tearDownAfterEach() {
        this.formatter = null;
    }

    @Test
    void parse_stringIsNull_shouldThrowParseException() {
        Assertions.assertThrows(ParseException.class, () -> this.formatter.parse(null, Locale.ENGLISH));
    }

    @Test
    void parse_stringIsNotParseable_shouldThrowParseException() {
        Assertions.assertThrows(ParseException.class, () -> this.formatter.parse("asdas", Locale.ENGLISH));
    }

    @Test
    void parse_newString_shouldReturnLocalDate() throws ParseException {
        LocalDate expected = LocalDate.of(1488,9, 1);
        Assertions.assertEquals(expected, this.formatter.parse("1488-09-01", Locale.ENGLISH));
    }
}
