/*package com.example.ticketservice.util;

import com.example.ticketservice.util.Status;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @Test
    void statusEnumContainsExpectedValues() {
        Status[] values = Status.values();

        assertThat(values)
                .containsExactlyInAnyOrder(
                        Status.SUCCESS,
                        Status.FAILURE,
                        Status.DEFAULTED
                );
    }

    @Test
    void statusEnumToStringMatchesName() {
        assertThat(Status.SUCCESS.toString()).isEqualTo("SUCCESS");
        assertThat(Status.FAILURE.toString()).isEqualTo("FAILURE");
        assertThat(Status.DEFAULTED.toString()).isEqualTo("DEFAULTED");
    }

    @Test
    void canParseEnumFromString() {
        assertThat(Status.valueOf("SUCCESS")).isEqualTo(Status.SUCCESS);
        assertThat(Status.valueOf("FAILURE")).isEqualTo(Status.FAILURE);
        assertThat(Status.valueOf("DEFAULTED")).isEqualTo(Status.DEFAULTED);
    }
}*/
