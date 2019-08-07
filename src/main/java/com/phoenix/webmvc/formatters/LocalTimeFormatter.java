package com.phoenix.webmvc.formatters;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;
import org.springframework.format.Formatter;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public String print(LocalTime object, Locale locale) {

       return object.getHour() +":" +object.getMinute();
    }

}
