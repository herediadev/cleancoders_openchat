package org.openchat.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class FormatDateService implements Function<LocalDateTime, String> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public String apply(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
