package com.example.ticketservice.service;

import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsPriorityDTO;
import com.example.ticketservice.dto.TicketDTO;
import com.example.ticketservice.entity.*;
import com.example.ticketservice.repository.MetricsRepository;
import com.example.ticketservice.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricsServiceTest {

    @Mock
    private MetricsRepository metricsRepository;

    @Mock
    private PriorityService priorityService;

    @InjectMocks
    private MetricsService metricsService;

    private Metrics metrics1;
    private Metrics metrics2;

    @BeforeEach
    void setUp() {
        // Departments
        Department dept1 = new Department();
        dept1.setDepartmentID(1);
        dept1.setDepartmentName("IT");
        dept1.setMailAddress("it@example.com");

        Department dept2 = new Department();
        dept2.setDepartmentID(2);
        dept2.setDepartmentName("HR");
        dept2.setMailAddress("hr@example.com");

        // MetricsDepartments
        MetricsDepartment md1 = new MetricsDepartment();
        md1.setMetricsDepartmentID(101);
        md1.setDepartment(dept1);
        md1.setStatus(Status.SUCCESS);

        MetricsDepartment md2 = new MetricsDepartment();
        md2.setMetricsDepartmentID(102);
        md2.setDepartment(dept2);
        md2.setStatus(Status.FAILURE);

        // Priorities
        Priority prio1 = new Priority();
        prio1.setPriorityID(1);
        prio1.setPriorityName("High");

        Priority prio2 = new Priority();
        prio2.setPriorityID(2);
        prio2.setPriorityName("Low");

        // MetricsPriorities
        MetricsPriority mp1 = new MetricsPriority();
        mp1.setMetricsPriorityID(201);
        mp1.setPriority(prio1);
        mp1.setStatus(Status.SUCCESS);

        MetricsPriority mp2 = new MetricsPriority();
        mp2.setMetricsPriorityID(202);
        mp2.setPriority(prio2);
        mp2.setStatus(Status.FAILURE);

        // Metrics
        metrics1 = new Metrics();
        metrics1.setMetricsID(1001);
        metrics1.setMetricsDepartment(md1);
        metrics1.setMetricsPriority(mp1);
        metrics1.setSubject("Ticket 1");
        metrics1.setContent("Content 1");
        metrics1.setDate(LocalDate.now().minusDays(1));
        md1.setMetrics(metrics1);
        mp1.setMetrics(metrics1);

        metrics2 = new Metrics();
        metrics2.setMetricsID(1002);
        metrics2.setMetricsDepartment(md2);
        metrics2.setMetricsPriority(mp2);
        metrics2.setSubject("Ticket 2");
        metrics2.setContent("Content 2");
        metrics2.setDate(LocalDate.now());
        md2.setMetrics(metrics2);
        mp2.setMetrics(metrics2);

        // Mock repository
        lenient().when(metricsRepository.findAll()).thenReturn(Arrays.asList(metrics1, metrics2));
        lenient().when(metricsRepository.findById(1001)).thenReturn(Optional.of(metrics1));
        lenient().when(metricsRepository.findById(1002)).thenReturn(Optional.of(metrics2));
    }

    @Test
    void getAllTickets_returnsCorrectDTOs() {
        List<TicketDTO> tickets = metricsService.getAllTickets();
        assertEquals(2, tickets.size());
        assertEquals(101, tickets.get(0).getId());
        assertEquals("Low", tickets.get(1).getPriority());
    }

    @Test
    void getRoutingStatsDepartments_returnsCorrectCounts() {
        RoutingStatsDepartmentDTO stats = metricsService.getRoutingStatsDepartments();
        assertEquals(2, stats.getTotalTickets());
        assertEquals(1, stats.getSuccessCount());
        assertEquals(1, stats.getFailureCount());
        assertEquals(0, stats.getDefaultedCount());
        assertEquals(0.5, stats.getAccuracy());
    }

    @Test
    void getRoutingStatsPriorities_returnsCorrectCounts() {
        RoutingStatsPriorityDTO stats = metricsService.getRoutingStatsPriorities();
        assertEquals(2, stats.getTotalTickets());
        assertEquals(1, stats.getSuccessCount());
        assertEquals(1, stats.getFailureCount());
        assertEquals(0.5, stats.getAccuracy());
    }

    @Test
    void getMetricsDepartment_returnsCorrectMetricsDepartment() {
        MetricsDepartment md = metricsService.getMetricsDepartment(1001);
        assertEquals(101, md.getMetricsDepartmentID());
        assertEquals(Status.SUCCESS, md.getStatus());
    }

    @Test
    void getMetricsPriority_returnsCorrectMetricsPriority() {
        MetricsPriority mp = metricsService.getMetricsPriority(1002);
        assertEquals(202, mp.getMetricsPriorityID());
        assertEquals(Status.FAILURE, mp.getStatus());
    }

    @Test
    void getMetricsDepartment_invalidId_throwsException() {
        when(metricsRepository.findById(9999)).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> metricsService.getMetricsDepartment(9999));
        assertTrue(ex.getMessage().contains("Metrics not found with ID"));
    }

    @Test
    void getMetricsDepartmentsForDepartment_returnsFilteredTickets() {
        List<TicketDTO> tickets = metricsService.getMetricsDepartmentsForDepartment(1);
        assertEquals(1, tickets.size());
        assertEquals(101, tickets.get(0).getId());
    }

    @Test
    void getRoutingStatsOnePriority_returnsCorrectCounts() {
        RoutingStatsPriorityDTO stats = metricsService.getRoutingStatsOnePriority(1);
        assertEquals(1, stats.getTotalTickets());
        assertEquals(1, stats.getSuccessCount());
        assertEquals(0, stats.getFailureCount());
    }

    @Test
    void getRoutingStatsOneDepartment_returnsCorrectCounts() {
        RoutingStatsDepartmentDTO stats = metricsService.getRoutingStatsOneDepartment(2);
        assertEquals(1, stats.getTotalTickets());
        assertEquals(0, stats.getSuccessCount());
        assertEquals(1, stats.getFailureCount());
        assertEquals(0, stats.getDefaultedCount());
    }

    @Test
    void markTicketDepartmentAsMisrouted_setsStatusToFailure_andSaves() {
        // Arrange
        when(metricsRepository.findById(metrics1.getMetricsID()))
                .thenReturn(Optional.of(metrics1));

        // Act
        metricsService.markTicketDepartmentAsMisrouted(metrics1.getMetricsID());

        // Assert
        assertEquals(Status.FAILURE, metrics1.getMetricsDepartment().getStatus());
        verify(metricsRepository).save(metrics1);
    }

    @Test
    void markTicketDepartmentAsCorrect_setsStatusToSuccess_andSaves() {
        // Arrange
        when(metricsRepository.findById(metrics1.getMetricsID()))
                .thenReturn(Optional.of(metrics1));

        // Act
        metricsService.markTicketDepartmentAsCorrect(metrics1.getMetricsID());

        // Assert
        assertEquals(Status.SUCCESS, metrics1.getMetricsDepartment().getStatus());
        verify(metricsRepository).save(metrics1);
    }

    @Test
    void markTicketDepartmentAsMisrouted_metricsNotFound_throwsException() {
        // Arrange
        when(metricsRepository.findById(999)).thenReturn(Optional.empty());

        // Act + Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> metricsService.markTicketDepartmentAsMisrouted(999)
        );

        assertTrue(ex.getMessage().contains("Metrics not found"));
    }

    @Test
    void getDailyMisroutingStatsDepartment_returnsCorrectCounts() {
        // Arrange
        LocalDate day1 = LocalDate.of(2024, 1, 1);
        LocalDate day2 = LocalDate.of(2024, 1, 2);

        metrics1.setDate(day1);
        metrics1.getMetricsDepartment().setStatus(Status.FAILURE);

        metrics2.setDate(day1);
        metrics2.getMetricsDepartment().setStatus(Status.FAILURE);

        Metrics metrics3 = createMetricsWithStatusAndDate(Status.SUCCESS, day2);
        Metrics metrics4 = createMetricsWithStatusAndDate(Status.FAILURE, day2);

        when(metricsRepository.findAll())
                .thenReturn(List.of(metrics1, metrics2, metrics3, metrics4));

        // Act
        Map<LocalDate, Long> result =
                metricsService.getDailyMisroutingStatsDepartment(day1, day2);

        // Assert
        assertEquals(2, result.get(day1));
        assertEquals(1, result.get(day2));
    }

    private Metrics createMetricsWithStatusAndDate(Status status, LocalDate date) {
        Department dept = new Department();
        dept.setDepartmentID(1);

        MetricsDepartment md = new MetricsDepartment();
        md.setStatus(status);
        md.setDepartment(dept);

        Metrics metrics = new Metrics();
        metrics.setDate(date);
        metrics.setMetricsDepartment(md);

        md.setMetrics(metrics);

        return metrics;
    }



}
