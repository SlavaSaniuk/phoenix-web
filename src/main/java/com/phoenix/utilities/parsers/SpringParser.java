package com.phoenix.utilities.parsers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;

public abstract class SpringParser<T> implements Parser<T> {

    @Getter @Setter
    private Environment environment;

    @SuppressWarnings("WeakerAccess")
    protected SpringParser(Environment env) {
        this.environment = env;
    }

    @Override
    public abstract T  getProperties();

}
