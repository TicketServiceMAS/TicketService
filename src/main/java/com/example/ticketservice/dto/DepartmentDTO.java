package com.example.ticketservice.dto;

import com.example.ticketservice.util.DepartmentName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDTO {
    private String departmentName;
    private String mailAddress;


}
