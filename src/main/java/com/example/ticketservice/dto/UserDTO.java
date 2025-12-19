package com.example.ticketservice.dto;

import com.example.ticketservice.entity.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer departmentID;
    private String username;
    private boolean isAdmin;

}
