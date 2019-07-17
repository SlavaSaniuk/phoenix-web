package com.phoenix.webmvc.formatters;

import org.springframework.format.Formatter;

import java.util.Locale;

public class CharFormatter implements Formatter<Character> {

    @Override
    public Character parse(String text, Locale locale) {
        if (text == null || text.isEmpty()) return '\u0000';
        return text.charAt(0);
    }

    @Override
    public String print(Character object, Locale locale) {
        if(object == null || object == '\u0000') return "";
        return object.toString();
    }

}
