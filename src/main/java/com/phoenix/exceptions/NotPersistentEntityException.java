package com.phoenix.exceptions;

import javax.persistence.Entity;
import javax.persistence.PersistenceException;

/**
 * This exception occurs when developer want to save, update, delete or
 * other methods, that's change entity internal state with entity which is not in
 * persistence context (Entity ID has {@code null} or default value.).
 */
public class NotPersistentEntityException extends PersistenceException {

    private String msg = " entity is not in persistent context (ID has a default value or null).";

    public NotPersistentEntityException(Class entity) {
        super(entity.getName() +" entity is not in persistent context (ID has a default value or null).");
        this.msg = entity.getName() +msg;
    }

    public NotPersistentEntityException(Entity entity) {
        super(entity.getClass().getName() +" entity is not in persistent context (ID has a default value or null).");
        this.msg = entity.getClass().getName() +msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
