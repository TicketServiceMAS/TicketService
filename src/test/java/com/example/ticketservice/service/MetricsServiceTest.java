/*package com.example.ticketservice.service;

import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.repository.MetricsPriorityRepository;
import com.example.ticketservice.repository.PriorityRepository;
import com.example.ticketservice.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricsServiceTest {

    @Mock
    private MetricsDepartmentRepository metricsDepartmentRepository;

    @Mock
    private MetricsPriorityRepository metricsPriorityRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PriorityRepository priorityRepository;

    @InjectMocks
    private MetricsService metricsService;

    private Department serviceDeskL1;
    private Department network;

    @BeforeEach
    void setUp() {
        serviceDeskL1 = new Department(1, "SERVICE_DESK_L1", "l1@example.com", null);
        network = new Department(2, "NETWORK", "network@example.com", null);
    }

    @Test
    void getRoutingStatsDepartmentsCalculatesCountsAndAccuracy() {
        List<MetricsDepartment> metrics = List.of(
                new MetricsDepartment(1, "Subject 1", Status.SUCCESS, LocalDate.now(), serviceDeskL1),
                new MetricsDepartment(2, "Subject 2", Status.FAILURE, LocalDate.now(), network),
                new MetricsDepartment(3, "Subject 3", Status.DEFAULTED, LocalDate.now(), serviceDeskL1)
        );

        when(metricsDepartmentRepository.findAll()).thenReturn(metrics);

        RoutingStatsDepartmentDTO result = metricsService.getRoutingStatsDepartments();

        assertThat(result.getTotalTickets()).isEqualTo(3);
        assertThat(result.getSuccessCount()).isEqualTo(1);
        assertThat(result.getFailureCount()).isEqualTo(1);
        assertThat(result.getDefaultedCount()).isEqualTo(1);
        assertThat(result.getAccuracy()).isEqualTo(1.0 / 3.0);
    }

    @Test
    void getDailyMisroutingStatsCountsFailuresWithinDateRange() {
        LocalDate may1 = LocalDate.of(2024, 5, 1);
        LocalDate may2 = LocalDate.of(2024, 5, 2);
        LocalDate may3 = LocalDate.of(2024, 5, 3);

        List<MetricsDepartment> metrics = List.of(
                new MetricsDepartment(1, "Subject 1", Status.FAILURE, may1, serviceDeskL1),
                new MetricsDepartment(2, "Subject 2", Status.FAILURE, may1, serviceDeskL1),
                new MetricsDepartment(3, "Subject 3", Status.SUCCESS, may2, network),
                new MetricsDepartment(4, "Subject 4", Status.FAILURE, may2, network),
                new MetricsDepartment(5, "Subject 5", Status.FAILURE, may3, network)
        );

        when(metricsDepartmentRepository.findAll()).thenReturn(metrics);

        Map<LocalDate, Long> result = metricsService.getDailyMisroutingStats(may1, may2);

        assertThat(result)
                .hasSize(2)
                .containsEntry(may1, 2L)
                .containsEntry(may2, 1L);
    }
}*/
