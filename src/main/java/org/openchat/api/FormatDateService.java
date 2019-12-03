package org.openchat.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public String execute(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
