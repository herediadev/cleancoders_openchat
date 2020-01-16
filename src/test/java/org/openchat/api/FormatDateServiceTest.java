package org.openchat.api;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class FormatDateServiceTest {

    @Test
    void given_the_aspect_before_it_will_log_the_information() {
        FormatDateService formatDateService = new FormatDateService();

        String dateFormatted = formatDateService.apply(LocalDateTime.now());

        System.out.println(dateFormatted);
    }
}