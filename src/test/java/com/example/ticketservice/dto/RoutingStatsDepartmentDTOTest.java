package com.example.ticketservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoutingStatsDepartmentDTOTest {

    @Test
    void allArgsConstructorSetsAllFieldsCorrectly() {
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
    void settersAndGettersWorkCorrectly() {
        // Brug all-args constructor med startværdier
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
}
