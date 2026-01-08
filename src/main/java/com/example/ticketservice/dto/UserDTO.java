package com.example.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer departmentID;
    private String username;
    private boolean isAdmin;

}
