package com.example.ticketservice.controller;

import com.example.ticketservice.dto.*;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.MetricsPriority;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.entity.User;
import com.example.ticketservice.service.DepartmentService;
import com.example.ticketservice.service.MetricsService;
import com.example.ticketservice.service.PriorityService;
import com.example.ticketservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ticketservice")
@CrossOrigin
public class Controller {

    private final MetricsService metricsService;
    private final DepartmentService departmentService;
    private final PriorityService priorityService;

    private final UserService userService;

    public Controller(
            MetricsService metricsService,
            DepartmentService departmentService,
            PriorityService priorityService,
            UserService userService
    ) {
        this.metricsService = metricsService;
        this.departmentService = departmentService;
        this.priorityService = priorityService;
        this.userService = userService;
    }

    // ======================================================
    // ================ ROUTING STATS =======================
    // ======================================================

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
    @GetMapping("/stats")
    public RoutingStatsDepartmentDTO getRoutingStatsDepartments() {
        return metricsService.getRoutingStatsDepartments();
    }

    @GetMapping("/stats/{id}")
    public RoutingStatsDepartmentDTO getRoutingStatsOneDepartment(@PathVariable int id) {
        return metricsService.getRoutingStatsOneDepartment(id);
    }

    @GetMapping("/stats/priorities")
    public RoutingStatsPriorityDTO getRoutingStatsPriorities() {
        return metricsService.getRoutingStatsPriorities();
    }

    @GetMapping("/stats/priorities/{id}")
    public RoutingStatsPriorityDTO getRoutingStatsOnePriority(@PathVariable int id) {
        return metricsService.getRoutingStatsOnePriority(id);
    }

    // ======================================================
    // ===================== METRICS ========================
    // ======================================================

    @GetMapping("/metrics/departments")
    public List<TicketDTO> getAllMetricsDepartments() {
        return metricsService.getAllTickets();
    }

    @GetMapping("/metrics/departments/{id}/history")
    public ResponseEntity<?> getMetricsHistoryForDepartment(@PathVariable int id) {
        try {
            return ResponseEntity.ok(metricsService.getMetricsHistoryForDepartment(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/metrics/departments/{id}")
    public ResponseEntity<?> getMetricsDepartment(@PathVariable int id) {
        try {
            return ResponseEntity.ok(metricsService.getMetricsDepartment(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/departments/tickets/{id}")
    public ResponseEntity<?> getMetricsDepartmentForDepartment(@PathVariable int id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            if (!user.isAdmin()) {
                if (user.getDepartment().getDepartmentID() != id) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                            "You cannot access metrics for another department.");
                }
            }
            return ResponseEntity.ok(metricsService.getMetricsDepartmentsForDepartment(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/metrics/priorities")
    public List<MetricsPriority> getAllMetricsPriorities() {
        return metricsService.getAllMetricsPriorities();
    }

    @GetMapping("/metrics/priorities/{id}/history")
    public ResponseEntity<?> getMetricsHistoryForPriority(@PathVariable int id) {
        try {
            return ResponseEntity.ok(metricsService.getMetricsHistoryForPriority(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/metrics/priorities/{id}")
    public ResponseEntity<?> getMetricsPriorities(@PathVariable int id) {
        try {
            return ResponseEntity.ok(metricsService.getMetricsPriority(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ======================================================
    // ======= HISTORISK MISROUTING STATISTIK ===============
    // ======================================================

    @GetMapping("/metrics/misrouting/daily")
    public ResponseEntity<?> getDailyMisroutingStats(
            @RequestParam(value = "from", required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam(value = "to", required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            LocalDate to
    ) {
        try {
            return ResponseEntity.ok(metricsService.getDailyMisroutingStatsDepartment(from, to));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kunne ikke hente historiske misrouting-data.");
        }
    }

    // ======================================================
    // =================== DEPARTMENTS ======================
    // ======================================================

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable int id) {
        try {
            return ResponseEntity.ok(departmentService.getDepartment(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/departments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDTO dto) {
        Department department = new Department();
        department.setDepartmentName(dto.getDepartmentName());
        department.setMailAddress(dto.getMailAddress());
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @PutMapping("/departments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDepartment(
            @PathVariable int id,
            @RequestBody DepartmentDTO dto
    ) {
        Department department = new Department();
        department.setDepartmentName(dto.getDepartmentName());
        department.setMailAddress(dto.getMailAddress());
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(id, department));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/departments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ======================================================
    // ==================== PRIORITIES ======================
    // ======================================================

    @GetMapping("/priorities")
    public List<Priority> getPriorities() {
        return priorityService.getPriorities();
    }

    @GetMapping("/priorities/{id}")
    public ResponseEntity<?> getPriority(@PathVariable int id) {
        try {
            return ResponseEntity.ok(priorityService.getPriority(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/priorities")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Priority> createPriority(@RequestBody Priority priority) {
        return ResponseEntity.ok(priorityService.createPriority(priority));
    }

    @PutMapping("/priorities/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePriority(
            @PathVariable int id,
            @RequestBody Priority priority
    ) {
        try {
            return ResponseEntity.ok(priorityService.updatePriority(id, priority));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/priorities/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePriority(@PathVariable int id) {
        try {
            priorityService.deletePriority(id);
            return ResponseEntity.ok("Priority deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ======================================================
    // ===================== TICKETS ========================
    // ======================================================

    @PostMapping("/tickets/{id}/misrouted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> markTicketAsMisrouted(@PathVariable int id) {
        try {
            metricsService.markTicketDepartmentAsMisrouted(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kunne ikke markere ticket som forkert routing.");
        }
    }

    @PostMapping("/tickets/{id}/correct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> markTicketAsCorrect(@PathVariable int id) {
        try {
            metricsService.markTicketDepartmentAsCorrect(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kunne ikke markere ticket som korrekt routing: " + e.getMessage());
        }
    }

    @PostMapping("/tickets/{id}/priority/{priorityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTicketPriority(
            @PathVariable int id,
            @PathVariable int priorityId
    ) {
        try {
            metricsService.updateTicketPriority(id, priorityId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kunne ikke opdatere prioritet: " + e.getMessage());
        }
    }
}
