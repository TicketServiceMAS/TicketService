package com.example.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoutingStatsPriorityDTO {

    private int totalTickets;
    private int successCount;
    private int failureCount;
    private double accuracy;

}
