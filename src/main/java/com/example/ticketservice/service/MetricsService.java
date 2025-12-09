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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // ======================================================
    // ========== ROUTING STATS – DEPARTMENTS ===============
    // ======================================================

    public RoutingStatsDepartmentDTO getRoutingStatsDepartments() {

        List<MetricsDepartment> all = metricsDepartmentRepository.findAll();
        int total = all.size();

        int success = (int) all.stream().filter(x -> x.getStatus() == Status.SUCCESS).count();
        int failure = (int) all.stream().filter(x -> x.getStatus() == Status.FAILURE).count();
        int defaulted = (int) all.stream().filter(x -> x.getStatus() == Status.DEFAULTED).count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsDepartmentDTO(total, success, failure, defaulted, accuracy);
    }

    // ======================================================
    // ========== ROUTING STATS – PRIORITIES ================
    // ======================================================

    public RoutingStatsPriorityDTO getRoutingStatsPriorities() {

        List<MetricsPriority> all = metricsPriorityRepository.findAll();
        int total = all.size();

        int success = (int) all.stream().filter(x -> x.getStatus() == Status.SUCCESS).count();
        int failure = (int) all.stream().filter(x -> x.getStatus() == Status.FAILURE).count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsPriorityDTO(total, success, failure, accuracy);
    }

    public RoutingStatsPriorityDTO getRoutingStatsOnePriority(int id) {

        List<MetricsPriority> all =
                metricsPriorityRepository.findMetricsPriorityByPriority_PriorityID(id);

        int total = all.size();
        int success = (int) all.stream().filter(x -> x.getStatus() == Status.SUCCESS).count();
        int failure = (int) all.stream().filter(x -> x.getStatus() == Status.FAILURE).count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsPriorityDTO(total, success, failure, accuracy);
    }

    public RoutingStatsDepartmentDTO getRoutingStatsOneDepartment(int id) {

        List<MetricsDepartment> all =
                metricsDepartmentRepository.findMetricsDepartmentByDepartmentDepartmentID(id);

        int total = all.size();
        int success = (int) all.stream().filter(x -> x.getStatus() == Status.SUCCESS).count();
        int failure = (int) all.stream().filter(x -> x.getStatus() == Status.FAILURE).count();
        int defaulted = (int) all.stream().filter(x -> x.getStatus() == Status.DEFAULTED).count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsDepartmentDTO(total, success, failure, defaulted, accuracy);
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
                        new IllegalArgumentException("MetricsPriority not found with ID " + id));
    }

    // ======================================================
    // ========== MARK TICKET AS MISROUTED (FAILURE) ========
    // ======================================================

    public void markTicketAsMisrouted(long ticketId) {

        MetricsDepartment metricsDepartment = metricsDepartmentRepository.findById((int) ticketId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket not found with ID " + ticketId));

        metricsDepartment.setStatus(Status.FAILURE);
        metricsDepartmentRepository.save(metricsDepartment);
    }

    // ======================================================
    // ========== DAILY MISROUTING STATS ====================
    // ======================================================

    /**
     * Returnerer daglige misrouting-statistikker (antal FAILURE per dag)
     * mellem datoerne 'from' og 'to' (inklusive).
     */
    public Map<LocalDate, Long> getDailyMisroutingStats(LocalDate from, LocalDate to) {

        return metricsDepartmentRepository.findAll().stream()
                .filter(m -> m.getStatus() == Status.FAILURE)
                .filter(m -> {
                    LocalDate d = m.getDate();
                    return (d.isEqual(from) || d.isAfter(from))
                            && (d.isEqual(to) || d.isBefore(to));
                })
                .collect(Collectors.groupingBy(
                        MetricsDepartment::getDate,
                        Collectors.counting()
                ));
    }
}
