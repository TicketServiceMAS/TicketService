package com.example.ticketservice.dto;

import com.example.ticketservice.util.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private int id;             // MetricsDepartment ID
    private Status status;      // MetricsDepartment status
    private String priority;    // MetricsPriority priority name
    private String subject;
    private String content;// Metrics subject
    private LocalDate date;     // Metrics date
}
