/*package com.example.ticketservice.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoutingStatsDepartmentDTOIntegrationTest {

    @Test
    void contextLoadsAndDtoCanBeCreated() {
        // Hvis Spring Boot context ikke kan starte, fejler testen inden assertions.

        RoutingStatsDepartmentDTO dto = new RoutingStatsDepartmentDTO(
                10,   // totalTickets
                6,    // successCount
                2,    // failureCount
                2,    // defaultedCount
                0.6   // accuracy
        );

        assertThat(dto.getTotalTickets()).isEqualTo(10);
        assertThat(dto.getSuccessCount()).isEqualTo(6);
        assertThat(dto.getFailureCount()).isEqualTo(2);
        assertThat(dto.getDefaultedCount()).isEqualTo(2);
        assertThat(dto.getAccuracy()).isEqualTo(0.6);
    }

    @Test
    void dtoValuesCanBeUpdatedWithSetters() {
        RoutingStatsDepartmentDTO dto =
                new RoutingStatsDepartmentDTO(0, 0, 0, 0, 0.0);

        dto.setTotalTickets(5);
        dto.setSuccessCount(3);
        dto.setFailureCount(1);
        dto.setDefaultedCount(1);
        dto.setAccuracy(0.75);

        assertThat(dto.getTotalTickets()).isEqualTo(5);
        assertThat(dto.getSuccessCount()).isEqualTo(3);
        assertThat(dto.getFailureCount()).isEqualTo(1);
        assertThat(dto.getDefaultedCount()).isEqualTo(1);
        assertThat(dto.getAccuracy()).isEqualTo(0.75);
    }
}*/
