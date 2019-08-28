package com.phoenix.utilities.parsers;


import lombok.AccessLevel;
import lombok.Getter;

import lombok.Setter;
import org.springframework.core.env.Environment;

public abstract class SpringEnvironmentPropertiesParser<T> implements PropertiesParser<T> {

    @Getter(AccessLevel.PROTECTED) @Setter
    private Environment environment;

    protected abstract T parseEnvironment();

    @Override
    public T getProperties() {
        return parseEnvironment();
    }
}
