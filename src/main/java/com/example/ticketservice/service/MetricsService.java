package com.example.ticketservice.service;

import com.example.ticketservice.dto.RoutingStatsDTO;
import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsPriorityDTO;
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
     * Beregner samlet routing-statistik baseret på MetricsDepartment-tabellen.
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

    /**
     * (Bruges evt. hvis du vil have stats for et bestemt metricsDepartment-ID.
     *  Lader din eksisterende metode stå uændret.)
     */
    public RoutingStatsDTO getMetricsDepartments(int id) {

        List<MetricsDepartment> all =
                metricsDepartmentRepository.findAllById(Collections.singleton(id));
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
        List<MetricsPriority> all = metricsPriorityRepository.findMetricsPriorityByPriority_PriorityID(id);

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
        List<MetricsDepartment> all = metricsDepartmentRepository.findMetricsDepartmentByDepartmentDepartmentID(id);
        for (MetricsDepartment md : all){
            System.out.println(md.getStatus());
        }

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

    /**
     * Hent et enkelt MetricsDepartment (én ticket/row) ud fra dets ID.
     */
    public MetricsDepartment getMetricsDepartment(int id) {




    public MetricsDepartment getMetricsDepartment(int id){
        return metricsDepartmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("MetricsDepartment not found with ID " + id));
    }

    /**
     * Hent et enkelt MetricsPriority ud fra ID.
     */
    public MetricsPriority getMetricsPriority(int id) {
        return metricsPriorityRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Priority not found with ID " + id));
    }

    /**
     * NY: Hent ALLE tickets/metrics for et bestemt department.
     * Bruges af controllerens endpoint:
     *   GET /api/ticketservice/departments/{id}/tickets
     *
     * Forudsætter at MetricsDepartment har en relation:
     *   private Department department;
     *   // hvor Department har feltet categoryID
     * og at MetricsDepartmentRepository har metoden:
     *   List<MetricsDepartment> findByDepartment_CategoryID(int categoryID);
     */
    public List<MetricsDepartment> getTicketsForDepartment(int departmentId) {
        return metricsDepartmentRepository.findByDepartment_CategoryID(departmentId);
    }
}
