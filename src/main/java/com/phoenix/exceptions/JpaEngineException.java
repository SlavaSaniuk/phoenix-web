package com.phoenix.exceptions;

import javax.persistence.PersistenceException;

public class JpaEngineException extends PersistenceException {

    public JpaEngineException() {
        super();
    }

    public JpaEngineException(String msg) {
        super(msg);
    }
}
