package com.longbridge.exception;

/**
 * Created by Longbridge on 16/01/2018.
 */
public class PasswordException extends RuntimeException {

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
