package com.example.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoutingStatsDTO {

    private int totalTickets;
    private int successCount;
    private int failureCount;
    private int defaultedCount;
    private double accuracy;

}
