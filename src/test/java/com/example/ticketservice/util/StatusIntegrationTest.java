/*package com.example.ticketservice.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StatusIntegrationTest {

    @Test
    void contextLoadsAndStatusEnumIsAvailable() {
        // Hvis Spring-context ikke kan starte, fejler testen allerede her.
        // Vi laver samtidig et par asserts på enum'en.

        Status[] values = Status.values();

        assertThat(values)
                .isNotEmpty()
                .contains(Status.SUCCESS, Status.FAILURE, Status.DEFAULTED);
    }

    @Test
    void statusNamesAreStableForPersistenceAndJson() {
        // Denne test sikrer at navnene ikke ændrer sig ved et uheld
        // (vigtigt hvis enum bruges i DB eller JSON).

        assertThat(Status.SUCCESS.name()).isEqualTo("SUCCESS");
        assertThat(Status.FAILURE.name()).isEqualTo("FAILURE");
        assertThat(Status.DEFAULTED.name()).isEqualTo("DEFAULTED");
    }
}*/
