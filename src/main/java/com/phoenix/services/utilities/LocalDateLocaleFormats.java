package com.phoenix.services.utilities;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateLocaleFormats {

    private static final Locale[]  dmy = new Locale[] {Locale.UK, new Locale("ru"), Locale.ENGLISH};
    private static final Locale[]  mdy = new Locale[] {Locale.US};
    private static final Locale[]  ymd = new Locale[] {Locale.CANADA_FRENCH};

    private static final DateTimeFormatter dmyFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private static final DateTimeFormatter mdyFormatter = DateTimeFormatter.ofPattern("MM-dd-uuuu");
    private static final DateTimeFormatter ymdFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    public static DateTimeFormatter getFormatterFor(Locale locale) {

        for (Locale l : mdy) {
            if (l.equals(locale)) return mdyFormatter;
        }

        for (Locale l : dmy) {
            if (l.equals(locale)) return dmyFormatter;
        }

        for (Locale l : ymd) {
            if (l.equals(locale)) return ymdFormatter;
        }

        return ymdFormatter;
    }


}
