package com.phoenix.webmvc;

import com.phoenix.webmvc.formatters.LocalDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        Formatter<LocalDate> localDateFormatter = new LocalDateFormatter();
        registry.addFormatter(localDateFormatter);

    }
}
