package com.phoenix.exceptions;

import javax.persistence.PersistenceException;

/**
 * {@link Exception} throws in case which JPA Provider work incorrectly.
 */
public class JpaEngineException extends PersistenceException {

    /**
     * Construct new exception with default message.
     * @param msg - default message.
     */
    public JpaEngineException(String msg) {
        super(msg);
    }
}
