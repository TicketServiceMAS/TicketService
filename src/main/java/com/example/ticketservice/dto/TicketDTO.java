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
    private int id;
    private Status status;
    private String priority;
    private String subject;
    private String content;
    private LocalDate date;
}
