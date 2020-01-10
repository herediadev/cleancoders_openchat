package org.openchat.usercases.exceptions;

public class FollowingAlreadyExistException extends RuntimeException {

    private static final String MESSAGE = "Following already exist.";

    public FollowingAlreadyExistException() {
        super(MESSAGE);
    }
}
