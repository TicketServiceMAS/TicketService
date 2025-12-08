package com.example.ticketservice.service;

import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsPriorityDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.entity.MetricsPriority;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.repository.MetricsPriorityRepository;
import com.example.ticketservice.util.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricsService {

    private final MetricsDepartmentRepository metricsDepartmentRepository;
    private final MetricsPriorityRepository metricsPriorityRepository;
    private final DepartmentRepository departmentRepository;

    public MetricsService(MetricsDepartmentRepository metricsDepartmentRepository,
                          MetricsPriorityRepository metricsPriorityRepository,
                          DepartmentRepository departmentRepository) {
        this.metricsDepartmentRepository = metricsDepartmentRepository;
        this.metricsPriorityRepository = metricsPriorityRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Beregner routing-statistik baseret på MetricsDepartment-tabellen.
     * Hver række repræsenterer én ticket med et status-felt af typen Status (enum).
     */
    public RoutingStatsDepartmentDTO getRoutingStatsDepartments() {

        // Hent alle metrics (én per ticket)
        List<MetricsDepartment> all = metricsDepartmentRepository.findAll();

        int total = all.size();

        int success = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.SUCCESS)
                .count();

        int failure = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.FAILURE)
                .count();

        int defaulted = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.DEFAULTED)
                .count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsDepartmentDTO(
                total,
                success,
                failure,
                defaulted,
                accuracy
        );
    }

    public RoutingStatsPriorityDTO getRoutingStatsPriorities() {

        // Hent alle metrics (én per ticket)
        List<MetricsPriority> all = metricsPriorityRepository.findAll();

        int total = all.size();

        int success = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.SUCCESS)
                .count();

        int failure = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.FAILURE)
                .count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsPriorityDTO(
                total,
                success,
                failure,
                accuracy
        );
    }

    public RoutingStatsPriorityDTO getRoutingStatsOnePriority(int id) {

        // Hent alle metrics (én per ticket)
        List<MetricsPriority> all =
                metricsPriorityRepository.findMetricsPriorityByPriority_PriorityID(id);

        int total = all.size();

        int success = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.SUCCESS)
                .count();

        int failure = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.FAILURE)
                .count();

        int defaulted = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.DEFAULTED)
                .count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsPriorityDTO(
                total,
                success,
                failure,
                accuracy
        );
    }

    public RoutingStatsDepartmentDTO getRoutingStatsOneDepartment(int id) {

        // Hent alle metrics (én per ticket)
        List<MetricsDepartment> all =
                metricsDepartmentRepository.findMetricsDepartmentByDepartmentDepartmentID(id);

        int total = all.size();

        int success = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.SUCCESS)
                .count();

        int failure = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.FAILURE)
                .count();

        int defaulted = (int) all.stream()
                .filter(entry -> entry.getStatus() == Status.DEFAULTED)
                .count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsDepartmentDTO(
                total,
                success,
                failure,
                defaulted,
                accuracy
        );
    }

    // ======================================================
    // ================== METRICS CRUD ======================
    // ======================================================

    public MetricsDepartment getMetricsDepartment(int id) {
        return metricsDepartmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("MetricsDepartment not found with ID " + id));
    }

    public List<MetricsDepartment> getMetricsDepartmentsForDepartment(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Department not found with ID " + id));
        return department.getMetricsDepartments();
    }

    public MetricsPriority getMetricsPriority(int id) {
        return metricsPriorityRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Priority not found with ID " + id));
    }

    // ======================================================
    // ========== MARK TICKET AS MISROUTED (FAILURE) ========
    // ======================================================

    /**
     * Bruges af POST /api/ticketservice/tickets/{id}/misrouted.
     * Her antager vi, at {id} er ID'et på MetricsDepartment (én række per ticket).
     * Vi sætter blot status til FAILURE og gemmer.
     */
    public void markTicketAsMisrouted(long ticketId) {
        int idAsInt = (int) ticketId; // repo bruger Integer som ID

        MetricsDepartment metricsDepartment = metricsDepartmentRepository.findById(idAsInt)
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket not found with ID " + ticketId));

        // Marker ticketen som forkert routet
        metricsDepartment.setStatus(Status.FAILURE);

        metricsDepartmentRepository.save(metricsDepartment);
    }
}
