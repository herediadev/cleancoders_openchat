package org.openchat.usercases;

import org.openchat.usercases.exceptions.InappropriateLanguageException;

import java.util.Arrays;
import java.util.List;

public class ValidateInappropriateWordService {

    private static final List<String> INAPPROPRIATE_WORDS;

    static {
        INAPPROPRIATE_WORDS = Arrays.asList("orange", "ice cream", "elephant");
    }

    public ValidateInappropriateWordService() {
    }

    void validate(String postText) {
        INAPPROPRIATE_WORDS
                .stream()
                .filter(word -> postText.toLowerCase().contains(word))
                .findFirst()
                .ifPresent(this::throwInappropriateLanguageException);
    }

    private void throwInappropriateLanguageException(String word) {
        throw new InappropriateLanguageException();
    }

}
