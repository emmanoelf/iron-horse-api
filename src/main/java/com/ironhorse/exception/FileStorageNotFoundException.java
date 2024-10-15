package com.ironhorse.exception;

public class FileStorageNotFoundException extends RuntimeException {
    public FileStorageNotFoundException(String message) {
        super(message);
    }
}
