package org.openchat.usercases.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public static final String MESSAGE = "user %s already exists";

    public UserAlreadyExistException(String username) {
        super(String.format(MESSAGE, username));
    }
}
