package com.phoenix.exceptions;

import javax.persistence.PersistenceException;

public class NotPersistedEntity extends PersistenceException {

    public NotPersistedEntity(String msg) {
        super("Entity is not persisted before.");
    }

    public NotPersistedEntity(Object entity) {
        super("Entity " +entity.getClass().getName() +" is not persisted before.");
    }
}
