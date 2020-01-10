package org.openchat.usercases.exceptions;

public class InappropriateLanguageException extends RuntimeException {

    private static final String MESSAGE = "Post contains inappropriate language.";

    public InappropriateLanguageException() {
        super(MESSAGE);
    }
}
