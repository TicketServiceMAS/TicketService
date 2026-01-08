package com.example.ticketservice.controller;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.messaging.EmailReceiver;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.security.service.JwtService;
import com.example.ticketservice.service.DepartmentService;
import com.example.ticketservice.service.MetricsService;
import com.example.ticketservice.service.PriorityService;
import com.example.ticketservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = Controller.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ControllerEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService; // REQUIRED

    @MockBean
    private MetricsService metricsService;

    @MockBean
    private PriorityService priorityService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;


    @Test
    void  getDepartmentsEndpointReturnsAllDepartments() throws Exception {

        Department d1 = new Department();
        d1.setDepartmentName("SERVICE_DESK_L1");
        d1.setMailAddress("l1@example.com");

        Department d2 = new Department();
        d2.setDepartmentName("NETWORK");
        d2.setMailAddress("network@example.com");

        when(departmentService.getAllDepartments())
                .thenReturn(List.of(d1, d2));

        mockMvc.perform(get("/api/ticketservice/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].departmentName").value("SERVICE_DESK_L1"))
                .andExpect(jsonPath("$[1].departmentName").value("NETWORK"));
    }
}

