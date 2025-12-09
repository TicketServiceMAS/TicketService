package com.example.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO til historisk statistik over forkert routede tickets.
 * Én række pr. dato.
 */
@Data
@AllArgsConstructor
public class MisroutingDailyStatsDTO {

    /**
     * Datoen (LocalDate) for metrics-rækken.
     */
    private LocalDate date;

    /**
     * Hvor mange tickets blev der totalt routet den dag.
     */
    private long totalTickets;

    /**
     * Hvor mange af dagens tickets blev markeret som forkert routet.
     * (Status.FAILURE i MetricsDepartment)
     */
    private long misroutedCount;

    /**
     * Procentdel af dagens tickets, der var forkert routede (0–100).
     */
    private double misroutedPercentage;
}
