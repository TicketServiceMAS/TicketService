/*package com.example.ticketservice.controller;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();

        Department d1 = new Department(0, "SERVICE_DESK_L1", "l1@example.com", null);
        Department d2 = new Department(0, "NETWORK", "network@example.com", null);

        departmentRepository.save(d1);
        departmentRepository.save(d2);
    }

    @Test
    void getDepartmentsEndpointReturnsAllDepartments() throws Exception {
        mockMvc.perform(get("/api/ticketservice/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].departmentName", is("SERVICE_DESK_L1")))
                .andExpect(jsonPath("$[1].departmentName", is("NETWORK")));
    }
}*/
