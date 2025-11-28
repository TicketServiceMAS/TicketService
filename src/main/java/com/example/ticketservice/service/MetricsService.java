package com.example.ticketservice.service;

import com.example.ticketservice.dto.RoutingStatsDTO;
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

    public MetricsService(MetricsDepartmentRepository metricsDepartmentRepository,MetricsPriorityRepository metricsPriorityRepository, DepartmentRepository departmentRepository) {
        this.metricsDepartmentRepository = metricsDepartmentRepository;
        this.metricsPriorityRepository = metricsPriorityRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Beregner routing-statistik baseret på MetricsDepartment-tabellen.
     * Hver række repræsenterer én ticket med et status-felt af typen Status (enum).
     */
    public RoutingStatsDTO getRoutingStats() {

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

        return new RoutingStatsDTO(
                total,
                success,
                failure,
                defaulted,
                accuracy
        );
    }

    public MetricsDepartment getMetricsDepartment(int id){
        return metricsDepartmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Priority not found with ID " + id));
    }

    public MetricsPriority getMetricsPriority(int id){
        return metricsPriorityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Priority not found with ID " + id));

    }


}
