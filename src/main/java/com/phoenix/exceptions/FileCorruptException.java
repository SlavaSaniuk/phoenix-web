package com.phoenix.exceptions;

import java.io.IOException;

/**
 * Unchecked exception. Used for inform users about configuration file is corrupt.
 */
public class FileCorruptException extends Exception {

    /**
     * Construct new exception object with clause of {@link IOException}.
     * @param file_name - Corrupted file name.
     */
    public FileCorruptException(String file_name) {
        super("Requested file \"" +file_name +"\" is corrupt.", new IOException());
    }
}
