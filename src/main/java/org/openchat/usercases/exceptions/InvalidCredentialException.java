package org.openchat.usercases.exceptions;

public class InvalidCredentialException extends RuntimeException {
    private static final String MESSAGE = "Invalid credentials.";

    public InvalidCredentialException() {
        super(MESSAGE);
    }
}
