package com.phoenix.exceptions;

import java.io.IOException;

public class FileCorruptException extends Exception {

    public FileCorruptException() {
        super("Requested file is corrupt.", new IOException());
    }

    public FileCorruptException(String file_name) {
        super("Requested file \"" +file_name +"\" is corrupt.", new IOException());
    }
}
