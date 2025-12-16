package com.example.ticketservice.service;

import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsPriorityDTO;
import com.example.ticketservice.dto.TicketDTO;
import com.example.ticketservice.entity.Metrics;
import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.entity.MetricsPriority;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.repository.MetricsRepository;
import com.example.ticketservice.util.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricsService {

    private final MetricsRepository metricsRepository;
    private final PriorityService priorityService;

    public MetricsService(MetricsRepository metricsRepository,
                          PriorityService priorityService) {
        this.metricsRepository = metricsRepository;
        this.priorityService = priorityService;
    }

    public List<TicketDTO> getAllTickets() {
        return metricsRepository.findAll().stream()
                .map(metrics -> {
                    TicketDTO dto = new TicketDTO();
                    dto.setId(metrics.getMetricsDepartment().getMetricsDepartmentID());
                    dto.setStatus(metrics.getMetricsDepartment().getStatus());
                    dto.setPriority(metrics.getMetricsPriority().getPriority().getPriorityName());
                    dto.setSubject(metrics.getSubject());
                    dto.setDate(metrics.getDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ======================================================
    // ========== ROUTING STATS – DEPARTMENTS ===============
    // ======================================================

    public RoutingStatsDepartmentDTO getRoutingStatsDepartments() {

        List<MetricsDepartment> all = metricsRepository.findAll().stream()
                .map(Metrics::getMetricsDepartment)
                .collect(Collectors.toList());

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

        List<MetricsPriority> all = metricsRepository.findAll().stream()
                .map(Metrics::getMetricsPriority)
                .collect(Collectors.toList());

        int total = all.size();
        int success = (int) all.stream().filter(x -> x.getStatus() == Status.SUCCESS).count();
        int failure = (int) all.stream().filter(x -> x.getStatus() == Status.FAILURE).count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsPriorityDTO(total, success, failure, accuracy);
    }

    public RoutingStatsPriorityDTO getRoutingStatsOnePriority(int priorityId) {

        List<MetricsPriority> all = metricsRepository.findAll().stream()
                .map(Metrics::getMetricsPriority)
                .filter(mp -> mp.getPriority().getPriorityID() == priorityId)
                .collect(Collectors.toList());

        int total = all.size();
        int success = (int) all.stream().filter(x -> x.getStatus() == Status.SUCCESS).count();
        int failure = (int) all.stream().filter(x -> x.getStatus() == Status.FAILURE).count();

        double accuracy = total > 0 ? (double) success / total : 0.0;

        return new RoutingStatsPriorityDTO(total, success, failure, accuracy);
    }

    public RoutingStatsDepartmentDTO getRoutingStatsOneDepartment(int departmentId) {

        List<MetricsDepartment> all = metricsRepository.findAll().stream()
                .map(Metrics::getMetricsDepartment)
                .filter(md -> md.getDepartment().getDepartmentID() == departmentId)
                .collect(Collectors.toList());

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
        Metrics metrics = metricsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + id));
        return metrics.getMetricsDepartment();
    }

    public MetricsPriority getMetricsPriority(int id) {
        Metrics metrics = metricsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + id));
        return metrics.getMetricsPriority();
    }

    public List<TicketDTO> getMetricsDepartmentsForDepartment(int departmentId) {
        return metricsRepository.findAll().stream()
                // filter by department
                .filter(metrics -> metrics.getMetricsDepartment().getDepartment().getDepartmentID() == departmentId)
                // sort by parent Metrics.date
                .sorted(Comparator.comparing(Metrics::getDate))
                // map to DTO
                .map(metrics -> new TicketDTO(
                        metrics.getMetricsDepartment().getMetricsDepartmentID(),           // id (brugt som ticketId i frontend)
                        metrics.getMetricsDepartment().getStatus(),                         // status
                        metrics.getMetricsPriority().getPriority().getPriorityName(),      // priority
                        metrics.getSubject(),                                              // subject
                        metrics.getDate()                                                  // date
                ))
                .collect(Collectors.toList());
    }

    public List<MetricsDepartment> getMetricsHistoryForDepartment(int departmentId) {
        return metricsRepository.findAll().stream()
                .map(Metrics::getMetricsDepartment)                // get the department child
                .filter(md -> md.getDepartment().getDepartmentID() == departmentId) // filter by department
                .sorted(Comparator.comparing(md -> md.getMetrics().getDate()))     // sort by parent Metrics date
                .collect(Collectors.toList());
    }

    public List<MetricsPriority> getMetricsHistoryForPriority(int priorityId) {
        return metricsRepository.findAll().stream()
                .map(Metrics::getMetricsPriority)
                .filter(mp -> mp.getPriority().getPriorityID() == priorityId)
                .sorted(Comparator.comparing(a -> a.getMetrics().getDate()))
                .collect(Collectors.toList());
    }

    // ======================================================
    // ============ HISTORICAL DATA FOR INDEX ===============
    // ======================================================

    public List<MetricsDepartment> getAllMetricsDepartments() {
        return metricsRepository.findAll().stream()
                .map(Metrics::getMetricsDepartment)
                .sorted(Comparator.comparing(a -> a.getMetrics().getDate()))
                .collect(Collectors.toList());
    }

    public List<MetricsPriority> getAllMetricsPriorities() {
        return metricsRepository.findAll().stream()
                .map(Metrics::getMetricsPriority)
                .sorted(Comparator.comparing(a -> a.getMetrics().getDate()))
                .collect(Collectors.toList());
    }

    // ======================================================
    // ========== MARK TICKET AS MISROUTED (FAILURE) ========
    // ======================================================

    // ==================== DEPARTMENT =====================

    public void markTicketDepartmentAsMisrouted(int metricsId) {
        Metrics metrics = metricsRepository.findById(metricsId)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + metricsId));
        metrics.getMetricsDepartment().setStatus(Status.FAILURE);
        metricsRepository.save(metrics);
    }

    public void markTicketDepartmentAsCorrect(int metricsId) {
        Metrics metrics = metricsRepository.findById(metricsId)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + metricsId));
        metrics.getMetricsDepartment().setStatus(Status.SUCCESS);
        metricsRepository.save(metrics);
    }

    public Map<LocalDate, Long> getDailyMisroutingStatsDepartment(LocalDate from, LocalDate to) {
        return metricsRepository.findAll().stream()
                .filter(metrics -> metrics.getMetricsDepartment().getStatus() == Status.FAILURE)
                .filter(metrics -> {
                    LocalDate d = metrics.getDate();
                    return (d.isEqual(from) || d.isAfter(from)) && (d.isEqual(to) || d.isBefore(to));
                })
                .collect(Collectors.groupingBy(
                        Metrics::getDate,
                        Collectors.counting()
                ));
    }

    // ==================== PRIORITY =======================

    public void markTicketPriorityAsMisrouted(int metricsId) {
        Metrics metrics = metricsRepository.findById(metricsId)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + metricsId));
        metrics.getMetricsPriority().setStatus(Status.FAILURE);
        metricsRepository.save(metrics);
    }

    public void markTicketPriorityAsCorrect(int metricsId) {
        Metrics metrics = metricsRepository.findById(metricsId)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + metricsId));
        metrics.getMetricsPriority().setStatus(Status.SUCCESS);
        metricsRepository.save(metrics);
    }

    public Map<LocalDate, Long> getDailyMisroutingStatsPriority(LocalDate from, LocalDate to) {
        return metricsRepository.findAll().stream()
                .filter(metrics -> metrics.getMetricsPriority().getStatus() == Status.FAILURE)
                .filter(metrics -> {
                    LocalDate d = metrics.getDate();
                    return (d.isEqual(from) || d.isAfter(from)) && (d.isEqual(to) || d.isBefore(to));
                })
                .collect(Collectors.groupingBy(
                        Metrics::getDate,
                        Collectors.counting()
                ));
    }

    // ======================================================
    // ================ UPDATE TICKET PRIORITY ==============
    // ======================================================

    /**
     * Opdaterer prioriteten for et metrics/ticket.
     * metricsId = ID på Metrics (som du bruger som ticketId i frontend)
     * priorityId = ID på Priority (1=P1, 2=P2, 3=P3, 4=SIMA)
     */
    public void updateTicketPriority(int metricsId, int priorityId) {
        Metrics metrics = metricsRepository.findById(metricsId)
                .orElseThrow(() -> new IllegalArgumentException("Metrics not found with ID " + metricsId));

        Priority priority = priorityService.getPriority(priorityId); // antager at denne metode findes i PriorityService

        MetricsPriority metricsPriority = metrics.getMetricsPriority();
        metricsPriority.setPriority(priority);

        metricsRepository.save(metrics);
    }
}
