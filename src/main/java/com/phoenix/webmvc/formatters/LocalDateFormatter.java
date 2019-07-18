package com.phoenix.webmvc.formatters;

import com.phoenix.services.utilities.LocalDateLocaleFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Formatter used to convert String to LocalDate object and vise versa. Used to map html date input with
 * this birthday registration form field.
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateFormatter.class);

    /**
     * Map invalid LocalDate fields with this values.
     */
    //Invalid LocalDate format
    public static final LocalDate INVALID = LocalDate.of(1,1,1);

    @Override
    public LocalDate parse(String text, Locale locale) {

        //Check for initialization
        if (text == null) {
            LOGGER.debug("Nullable parse string " + null);
            return INVALID;
        }

        //Try to parse
        LocalDate result;
        try {
            result = LocalDate.parse(text, LocalDateLocaleFormats.getFormatterFor(locale));
        }catch (DateTimeParseException exc) {
            LOGGER.debug("Non-parseable string: " +text);
            LOGGER.debug(exc.toString());
            return INVALID;
        }

        return result;
    }

    @Override
    public String print(LocalDate object, Locale locale){

        if (object == null || object.equals(LocalDateFormatter.INVALID) ) return "";

        return object.format(LocalDateLocaleFormats.getFormatterFor(locale));
    }

}
